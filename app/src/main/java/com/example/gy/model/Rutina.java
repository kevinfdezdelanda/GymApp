package com.example.gy.model;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class Rutina implements Serializable {
    private String nombre, descripcion, imagen;
    private int id;
    private ArrayList<Ejercicio> ejercicios;

    public Rutina() {}

    public Rutina(String imagen, String nombre, String descripcion, int id) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
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
