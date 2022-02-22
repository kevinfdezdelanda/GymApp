package com.example.gy.model;

import java.util.Date;

public class Entrenamiento {
    private int id;
    private Ejercicio ejercicio;
    private String nota;
    private Date fecha;

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
}
