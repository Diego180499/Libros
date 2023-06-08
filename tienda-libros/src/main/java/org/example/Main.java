package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<ArrayList<Integer>> carrito;
    public static void main(String[] args) {
        System.out.println("!Bienvenido!");
        //carrito de compras
        carrito = new ArrayList<>();

        Tienda tiendaOmegaEs = new Tienda(1, "Omega.es");
        Tienda tiendaOmegaUk = new Tienda(2, "Omega.uk");

        llenarInventarioTiendas(tiendaOmegaEs,tiendaOmegaUk);
        menu(tiendaOmegaEs,tiendaOmegaUk);
    }

    public static void llenarInventarioTiendas(Tienda tiendaOmegaEs,Tienda tiendaOmegaUk) {
        //llenamos el inventario de la tienda omega.es
        tiendaOmegaEs.agregarCantidadLibro(1, 5);
        tiendaOmegaEs.agregarCantidadLibro(2, 10);
        tiendaOmegaEs.agregarCantidadLibro(3, 15);
        tiendaOmegaEs.agregarCantidadLibro(4, 20);
        tiendaOmegaEs.agregarCantidadLibro(5, 25);

        //llenamos el inventario de la tienda omega.uk
        tiendaOmegaUk.agregarCantidadLibro(1, 18);
        tiendaOmegaUk.agregarCantidadLibro(2, 15);
        tiendaOmegaUk.agregarCantidadLibro(3, 12);
        tiendaOmegaUk.agregarCantidadLibro(4, 9);
        tiendaOmegaUk.agregarCantidadLibro(5, 6);
    }

    public static void menu(Tienda tiendaOmegaEs,Tienda tiendaOmegaUk) {
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("Bienvenido, selecciona la tienda donde quieres realizar la compra:");
            System.out.println("1. Omega.es\n2. Omega.uk\n0. Salir");
            System.out.print("Opcion Tienda: ");
            opcion = entrada.nextInt();
            //enviamos al usuario a la tienda seleccionada
            if(opcion == 1){
                elegirTienda(tiendaOmegaEs);
            }else if(opcion == 2){
                elegirTienda(tiendaOmegaUk);
            }else{
                System.out.println("El id "+opcion+", no es un id de una tienda...");
            }

        } while (opcion != 0);
    }

    public static void elegirTienda(Tienda tienda){
        Scanner entrada = new Scanner(System.in);
        int opcionComprar = 0;

        do{
            ArrayList<Integer> detalleVenta = new ArrayList<>();
            int idLibro=0;
            int tipoPasta=0;
            int precio=0;
            System.out.println("Elige el libro que deseas comprar:");
            System.out.println(tienda.mostrarLibros());
            System.out.print("Opcion Libro: ");
            idLibro = entrada.nextInt();
            System.out.println("Elige tipo de pasta");
            System.out.println("1. Blanda\n2. Dura");
            System.out.print("Tipo de Pasta: ");
            tipoPasta = entrada.nextInt();
            precio = precioSegunPasta(tipoPasta);

            //cantidad copias
            int copias = 0;
            System.out.println("¿Cuantas copias desea añadir?");
            System.out.print("Copias: ");
            copias = entrada.nextInt();
            //llenar carrito
            for(int i=0; i<copias; i++){
                detalleVenta.add(idLibro);
                detalleVenta.add(precio);
                carrito.add(detalleVenta);
                detalleVenta = new ArrayList<>();
            }

            //seguir comprando o no
            System.out.println("¿Desea añadir otro libro?");
            System.out.println("1. Si \n2. No");
            System.out.print("Elegir: ");
            opcionComprar = entrada.nextInt();
        }while(opcionComprar != 2);

        //calcular total carrito
        if(carrito.size()>1){
          double total = totalCarrito(tienda.getId());
            System.out.println("El total a pagar es de: "+total);
        }else{
            System.out.println("El total a pagar es: "+carrito.get(0).get(1));
        }

    }


    //logica de negocio del total de la compra
    public static double totalCarrito(int idTienda){
        double total;
        ArrayList<ArrayList<Integer>> ventasDiferentes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> ventasRepetidas = new ArrayList<>();

        //si solo hay 2 productos
        if(carrito.size() == 2){
            if(carrito.get(0).get(0) == carrito.get(1).get(0)){
                total = carrito.get(0).get(1)+carrito.get(1).get(1);
                return total;
            }else{
                //aplicar descuento segun tienda
                ventasDiferentes.add(carrito.get(0));
                ventasDiferentes.add(carrito.get(1));
                total = aplicarDescuento(idTienda,ventasDiferentes);
                return total;
            }
        }


        //si hay mas de dos productos
        // {1,10},{1,10},{3,10} , {1,13}, {2,13}
        for(int i=0; i<carrito.size(); i++){
            if(i==0){
                if(carrito.get(i).get(0) != carrito.get(i+1).get(0)){
                    ventasDiferentes.add(carrito.get(i));
                }else{
                    ventasRepetidas.add(carrito.get(i));
                    //ventasRepetidas.add(carrito.get(i+1));
                }
            }else if(i < carrito.size()-1){
                if(carrito.get(i).get(0) != carrito.get(i+1).get(0)){
                    if(existeEnDiferentes(ventasDiferentes,carrito.get(i).get(0))){
                        ventasRepetidas.add(carrito.get(i));
                    }else{
                        ventasDiferentes.add(carrito.get(i));
                    }
                }else{
                    ventasRepetidas.add(carrito.get(i));
                    //ventasRepetidas.add(carrito.get(i+1));
                }
            }else if(i == carrito.size()-1){
                if(existeEnDiferentes(ventasDiferentes,carrito.get(i).get(0))){
                    ventasRepetidas.add(carrito.get(i));
                }else{
                    ventasDiferentes.add(carrito.get(i));
                }
            }
        }

        //obtener total carrito
        System.out.println("Fin de compra...");
        return aplicarDescuento(idTienda,ventasDiferentes) + totalRepetidos(ventasRepetidas);
    }

    public static int precioSegunPasta(int tipoPasta){
        if(tipoPasta == 1){
            return 10;
        }
        if(tipoPasta == 2){
            return 13;
        }
        return 0;
    }

    public static double aplicarDescuento(int idTienda, ArrayList<ArrayList<Integer>> ventasDiferentes){
            double totalVentasDiferentes=0;

            for(ArrayList<Integer> detalleVenta : ventasDiferentes){
                totalVentasDiferentes += detalleVenta.get(1);
            }

            if(idTienda == 1){
                return obtenerDescuentoTienda1(ventasDiferentes,totalVentasDiferentes);
            }else if(idTienda == 2){
                return obtenerDescuentoTienda2(ventasDiferentes,totalVentasDiferentes);
            }
            return 0;
    }

    public static double obtenerDescuentoTienda1(ArrayList<ArrayList<Integer>> ventasDiferentes, double totalVentasDiferentes){

        if(ventasDiferentes.size() == 2){
            return totalVentasDiferentes*0.95;
        }
        if(ventasDiferentes.size() == 3){
            return totalVentasDiferentes*0.90;
        }
        if(ventasDiferentes.size() == 4){
            return totalVentasDiferentes*0.80;
        }
        if(ventasDiferentes.size() == 2){
            return totalVentasDiferentes*0.75;
        }
        return 0;
    }

    public static double obtenerDescuentoTienda2(ArrayList<ArrayList<Integer>> ventasDiferentes, double totalVentasDiferentes){

        if(ventasDiferentes.size() == 2){
            return totalVentasDiferentes*0.93;
        }
        if(ventasDiferentes.size() == 3){
            return totalVentasDiferentes*0.88;
        }
        if(ventasDiferentes.size() == 4){
            return totalVentasDiferentes*0.75;
        }
        if(ventasDiferentes.size() == 2){
            return totalVentasDiferentes*0.70;
        }
        return 0;
    }


    public static double totalRepetidos(ArrayList<ArrayList<Integer>> ventasRepetidas){
        double total = 0;

        for(ArrayList<Integer> detalleVenta : ventasRepetidas){
            total += detalleVenta.get(1);
        }
        return total;
    }

    public static boolean existeEnDiferentes(ArrayList<ArrayList<Integer>> ventasDiferentes, int idLibro){

        for(ArrayList<Integer> detalleVenta : ventasDiferentes){
            if(detalleVenta.get(0) == idLibro){
                return true;
            }
        }
        return false;
    }
}
