/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Pc
 */
public class FichaDropTargetListener extends DropTargetAdapter {
    private JPanel fichasPanel;
    private JPanel dropPanel;
    private List<JFicha> listaFichas;
    private Controlador controlador;

    public FichaDropTargetListener(JPanel fichasPanel, JPanel dropPanel, List<JFicha> listaFichas, Controlador controlador) {
        this.fichasPanel = fichasPanel;
        this.dropPanel = dropPanel;
        this.listaFichas = listaFichas;
        this.controlador=controlador;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
         try {
        Transferable transferable = dtde.getTransferable();
        if (transferable.isDataFlavorSupported(FichaTransferable.FICHA_FLAVOR)) {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            JFicha ficha = (JFicha) transferable.getTransferData(FichaTransferable.FICHA_FLAVOR);
            Component[] componentes = dropPanel.getComponents();
            Point dropPoint = dtde.getLocation();

            boolean addAtBeginning = (componentes.length == 0) || (dropPoint.x < componentes[0].getBounds().x);
            boolean addAtEnd = (componentes.length == 0) || (dropPoint.x > componentes[componentes.length - 1].getBounds().x + componentes[componentes.length - 1].getWidth());

            String positionMessage;

            if (addAtBeginning) {
                System.out.println("izq");
                dropPanel.add(ficha, 0);
                positionMessage = "a la izquierda de la primera ficha";

                // Imprimir la primera ficha de dropPanel si se soltó a la izquierda
                if (componentes.length > 0) {
                    JFicha fichaIzquierda = (JFicha) componentes[0];
                    System.out.println("Ficha al lado: " + fichaIzquierda.toString());
                }
            } else if (addAtEnd) {
                System.out.println("dere");
                dropPanel.add(ficha);
                positionMessage = "a la derecha de la última ficha";

                // Imprimir la última ficha de dropPanel si se soltó a la derecha
                if (componentes.length > 0) {
                    JFicha fichaDerecha = (JFicha) componentes[componentes.length - 1];
                    System.out.println("Ficha al lado: " + fichaDerecha.toString());
                }
            } else {
                System.out.println("otro");
                dtde.dropComplete(false);
                return;
            }
            
            if(componentes.length == 0){
                controlador.enviarJugada(ficha, "izquierda");
                boolean removedFromPanel = removeComponentFromPanel(fichasPanel, ficha);
                boolean removedFromList = listaFichas.remove(ficha);

                System.out.println("Ficha " + ficha.toString() + " removida de fichasPanel: " + removedFromPanel);
                System.out.println("Ficha " + ficha.toString() + " removida de listaFichas: " + removedFromList);

                dropPanel.revalidate();
                dropPanel.repaint();
                fichasPanel.revalidate();
                fichasPanel.repaint();

                JOptionPane.showMessageDialog(dropPanel, "Ficha soltada " + positionMessage);
                dtde.dropComplete(true);
            }else{
                printCurrentState();
                String sentido = (addAtBeginning) ? "izquierda" : "derecha";
                Boolean respuesta = controlador.verificarJugada(ficha, sentido);
                System.out.println("RESULTADO: "+respuesta);
                
                if(respuesta){
                    System.out.println("LLEGA");
                    controlador.enviarJugada(ficha, sentido);
                    System.out.println("LLEGA2");
                    boolean removedFromPanel = removeComponentFromPanel(fichasPanel, ficha);
                    System.out.println("LLEGA3");
                    boolean removedFromList = listaFichas.remove(ficha);
                    System.out.println("LLEGA4");

                    System.out.println("Ficha " + ficha.toString() + " removida de fichasPanel: " + removedFromPanel);
                    System.out.println("Ficha " + ficha.toString() + " removida de listaFichas: " + removedFromList);

                    dropPanel.revalidate();
                    dropPanel.repaint();
                    fichasPanel.revalidate();
                    fichasPanel.repaint();

                    JOptionPane.showMessageDialog(dropPanel, "Ficha soltada " + positionMessage);
                    dtde.dropComplete(true);
                }else{
                    dtde.rejectDrop();    
                }
            
            }
        } else {
            dtde.rejectDrop();
        }
    } catch (Exception e) {
        dtde.rejectDrop();
        e.printStackTrace();
    }
    }

    private boolean removeComponentFromPanel(JPanel panel, JFicha ficha) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JFicha) {
                JFicha panelFicha = (JFicha) c;
                if (panelFicha.equals(ficha)) {
                    panel.remove(c);
                    return true;
                }
            }
        }
        return false;
    }

    private void printCurrentState() {
        System.out.println("Estado actual de las fichas en dropPanel:");
        for (Component c : dropPanel.getComponents()) {
            if (c instanceof JFicha) {
                JFicha f = (JFicha) c;
                System.out.println(f.toString());
            }
        }
        System.out.println("Estado actual de las fichas en fichasPanel:");
        for (Component c : fichasPanel.getComponents()) {
            if (c instanceof JFicha) {
                JFicha f = (JFicha) c;
                System.out.println(f.toString());
            }
        }
        System.out.println("Estado actual de las fichas en listaFichas:");
        for (JFicha f : listaFichas) {
            System.out.println(f.toString());
        }
    }
}
