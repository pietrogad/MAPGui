package com.example.clientmapgui;

public class Dati {
    private Double dato;
    public Dati(double dato) {
        this.dato = dato;
    }
    public Double getDato() {
        return dato;
    }
    public void setDato(Double dato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
