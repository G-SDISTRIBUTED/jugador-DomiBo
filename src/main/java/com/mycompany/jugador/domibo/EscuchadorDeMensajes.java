/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Pc
 */
public class EscuchadorDeMensajes extends Thread {
    private IEscuchadorDeEventosDelCliente escuchador;
    private BufferedReader entrada;
    
    public EscuchadorDeMensajes(IEscuchadorDeEventosDelCliente escuchador, BufferedReader entrada) {
        this.escuchador=escuchador;
        this.entrada=entrada;
    }
    
    @Override
    public void run() {
        try {
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                EventoDeMensajeRecibido evento = new EventoDeMensajeRecibido(this, mensaje);
                escuchador.mensajeRecibido(evento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
