package com.example.gy.model;

import java.io.Serializable;

public class Serie implements Serializable {
    private int reps, numSerie, id;
    private double peso, rm;


    public Serie(int reps, int numSerie, int id, double peso) {
        this.reps = reps;
        this.numSerie = numSerie;
        this.id = id;
        this.peso = peso;
        calcular1rm();
    }

    public Serie(){}

    public void calcular1rm(){
        Double value = (0.0333 *  peso) * reps + peso;
        double scale = Math.pow(10, 2);
        rm = Math.round(value * scale) / scale;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getRm() {
        return rm;
    }

    public void setRm(double rm) {
        this.rm = rm;
    }
}
