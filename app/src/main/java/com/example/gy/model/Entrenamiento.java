package com.example.gy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Entrenamiento implements Serializable {
    private int id;
    private Ejercicio ejercicio;
    private String nota;
    private Date fecha;
    private ArrayList<Serie> series;

    public Entrenamiento(int id, Ejercicio ejercicio, String nota, Date fecha) {
        this.id = id;
        this.ejercicio = ejercicio;
        this.nota = nota;
        this.fecha = fecha;
    }

    public Entrenamiento(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Serie> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<Serie> series) {
        this.series = series;
    }
}
