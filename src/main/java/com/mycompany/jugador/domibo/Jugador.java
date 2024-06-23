/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import com.mycompany.utilities.Paquete;

/**
 *
 * @author Pc
 */
public class Jugador implements IObservadorDeClienteSocket {
    ClienteSocket clienteSocket;
    
    public Jugador(){
        clienteSocket=new ClienteSocket(12345,"127.0.0.1");
        clienteSocket.asignarObservador(this);
    }
    
    @Override
    public void paqueteRecibido(String mensaje) {
        Paquete paquete=Paquete.deserializar(mensaje);
        String protocolo = paquete.obtenerProtocolo();
        switch (protocolo) {
            case "LOGIN":
                //
                break;
            case "LOGOUT":
                //
                break;
            case "REGISTER":
                //
                break;
            default:
                //
                break;
        }
    }
    
}
