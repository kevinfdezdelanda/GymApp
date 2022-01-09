package com.example.gy;

import android.widget.ImageView;

public class Ejercicio {
    private ImageView imagen;
    private String nombre;
    private int id;

    public Ejercicio() {
    }

    public Ejercicio(ImageView imagen, String nombre, int id) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.id = id;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
