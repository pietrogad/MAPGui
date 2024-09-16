package com.example.clientmapgui;

/**
 * Classe che rappresenta il tipo astratto Dato.
 */
public class Dati {
    private Double dato;

    /**
     * Costruttore della classe Dato.
     * @param dato valore reale da assegnare al dato in Dati
     */
    public Dati(double dato) {
        this.dato = dato;
    }

    /**
     * Metodo getter di Dati.
     * @return variabile dato.
     */
    public Double getDato() {
        return dato;
    }

    /**
     * Metodo setter di Dati.
     * @param dato valore da assegnare al dato in Dati
     */
    public void setDato(Double dato) {
        this.dato = dato;
    }

    /**
     * Ovveride del metodo toString
     * @return dato sottoforma di stringa.
     */
    @Override
    public String toString() {
        return dato.toString();
    }
}
