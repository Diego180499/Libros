package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tienda {

    private int id;
    private String nombre;
    private ArrayList<Libro> libros;
    private Map<Integer, Integer> inventarioLibros;

    public Tienda(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.libros = new ArrayList<>();
        librosIniciales();
        inventarioLibros = new HashMap<Integer, Integer>();
    }

    private void librosIniciales() {
        this.libros.add(new Libro("La Comunidad del Anillo", 1));
        this.libros.add(new Libro("Las Dos Torres", 2));
        this.libros.add(new Libro("El retorno del Rey", 3));
        this.libros.add(new Libro("Hobbit I", 4));
        this.libros.add(new Libro("Hobbit II", 5));
    }

    public void agregarCantidadLibro(int idLibro, int cantidad) {
        this.inventarioLibros.put(idLibro, cantidad);
    }

    public String mostrarLibros() {
        String texto = "";

        for (Libro libro : libros) {
            texto += libro.getId()+". "+libro.getNombre()+"\n";
        }
        return texto;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public Map<Integer, Integer> getInventarioLibros() {
        return inventarioLibros;
    }
}
