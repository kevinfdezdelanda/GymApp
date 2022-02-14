package com.example.gy.model;

import android.widget.ImageView;

import java.io.Serializable;

public class Rutina implements Serializable {
    private int imagen;
    private String nombre, descripcion;
    private int id;

    public Rutina() {}

    public Rutina(int imagen, String nombre, String descripcion, int id) {
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

    @Override
    public String toString() {
        return "Rutina{" +
                //"imagen=" + imagen +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}
