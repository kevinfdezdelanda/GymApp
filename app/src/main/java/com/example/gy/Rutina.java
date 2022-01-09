package com.example.gy;

import android.widget.ImageView;

public class Rutina {
    private ImageView imagen;
    private String nombre, descripcion;
    private int id;

    public Rutina() {}

    public Rutina(ImageView imagen, String nombre, String descripcion, int id) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
