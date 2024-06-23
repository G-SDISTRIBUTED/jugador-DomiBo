/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import java.util.EventObject;

/**
 *
 * @author Pc
 */
public class EventoDeMensajeRecibido extends EventObject {
    private String mensaje;
    
    public EventoDeMensajeRecibido(Object fuente,String mensaje) {
        super(fuente);
        this.mensaje = mensaje;
    }
    
    public String obtenerMensaje(){
        return mensaje;
    }
}
