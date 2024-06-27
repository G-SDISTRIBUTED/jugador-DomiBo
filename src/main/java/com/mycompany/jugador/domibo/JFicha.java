/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
/**
 *
 * @author Pc
 */
public class JFicha extends JComponent {
    private int puntosIzquierda;
    private int puntosDerecha;

    public JFicha(int puntosIzquierda, int puntosDerecha) {
        this.puntosIzquierda = puntosIzquierda;
        this.puntosDerecha = puntosDerecha;
        setPreferredSize(new Dimension(120, 60));
    }

    public int getPuntosIzquierda() {
        return puntosIzquierda;
    }

    public int getPuntosDerecha() {
        return puntosDerecha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, width, height);

        g2.drawLine(width / 2, 0, width / 2, height);

        drawPuntos(g2, puntosIzquierda, width / 4, height / 2);
        drawPuntos(g2, puntosDerecha, 3 * width / 4, height / 2);
    }

    private void drawPuntos(Graphics2D g2, int puntos, int cx, int cy) {
        int[][] posiciones;
        if (puntos == 1) {
            posiciones = new int[][] {{cx, cy}};
        } else if (puntos == 2) {
            posiciones = new int[][] {{cx - 10, cy - 10}, {cx + 10, cy + 10}};
        } else if (puntos == 3) {
            posiciones = new int[][] {{cx - 10, cy - 10}, {cx, cy}, {cx + 10, cy + 10}};
        } else if (puntos == 4) {
            posiciones = new int[][] {{cx - 15, cy - 15}, {cx + 15, cy - 15}, {cx - 15, cy + 15}, {cx + 15, cy + 15}};
        } else if (puntos == 5) {
            posiciones = new int[][] {{cx - 15, cy - 15}, {cx + 15, cy - 15}, {cx, cy}, {cx - 15, cy + 15}, {cx + 15, cy + 15}};
        } else if (puntos == 6) {
            posiciones = new int[][] {{cx - 15, cy - 15}, {cx + 15, cy - 15}, {cx - 15, cy}, {cx + 15, cy}, {cx - 15, cy + 15}, {cx + 15, cy + 15}};
        } else {
            posiciones = new int[0][0];
        }

        g2.setColor(Color.BLACK);
        for (int[] posicion : posiciones) {
            g2.fillOval(posicion[0] - 5, posicion[1] - 5, 10, 10);
        }
    }

    @Override
    public String toString() {
        return "Ficha(" + puntosIzquierda + ", " + puntosDerecha + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JFicha ficha = (JFicha) o;
        return puntosIzquierda == ficha.puntosIzquierda && puntosDerecha == ficha.puntosDerecha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(puntosIzquierda, puntosDerecha);
    }
}
