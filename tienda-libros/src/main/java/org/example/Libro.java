package org.example;

public class Libro {

    private int id;
    private String nombre;
    private double precio;

    public Libro(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public void setPrecio(int tipoPasta){
        if(tipoPasta == 1){ //pasta blanda
            this.precio = 10;
        }else if(tipoPasta == 2){ //pasta dura
            this.precio = 13;
        }
    }

    public double getPrecio(){
        return this.precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }
}
