package com.example.gy.model;

public class Serie {
    private int reps, numSerie, id;
    private double peso, rm;
    private Entrenamiento entrenamiento;

    public Serie(int reps, int numSerie, int id, double peso, double rm, Entrenamiento entrenamiento) {
        this.reps = reps;
        this.numSerie = numSerie;
        this.id = id;
        this.peso = peso;
        this.entrenamiento = entrenamiento;
        calcular1rm();
    }

    public Serie(){}

    public void calcular1rm(){
        rm = (0.0333 *  peso) * reps + peso;
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

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }
}
