package com.example.gy.model;

import android.widget.ImageView;

import java.io.Serializable;

public class Ejercicio implements Serializable {
    private int imagen;
    private String nombre;
    private String descripcion;
    private int id;

    public Ejercicio() {
    }

    public Ejercicio(int imagen, String nombre, String descripcion, int id) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
