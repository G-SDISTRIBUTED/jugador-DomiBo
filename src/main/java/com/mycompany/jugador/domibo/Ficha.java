/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

/**
 *
 * @author Pc
 */
public class Ficha {
    private int puntosIzquierda;
    private int puntosDerecha;
    
    public Ficha(int puntosIzquierda, int puntosDerecha) {
        this.puntosIzquierda = puntosIzquierda;
        this.puntosDerecha = puntosDerecha;
    }
    
    public int getPuntosIzquierda() {
        return puntosIzquierda;
    }

    public int getPuntosDerecha() {
        return puntosDerecha;
    }
    
    @Override
    public String toString() {
        return "Ficha(" + puntosIzquierda + ", " + puntosDerecha + ")";
    }
}
