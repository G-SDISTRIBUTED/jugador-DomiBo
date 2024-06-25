/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import com.mycompany.utilities.Paquete;
import org.json.JSONObject;

/**
 *
 * @author Pc
 */
public class Jugador implements IObservadorDeClienteSocket {
    ClienteSocket clienteSocket;
    Controlador controlador;
    
    public Jugador(){
        clienteSocket=new ClienteSocket(12345,"127.0.0.1");
        clienteSocket.asignarObservador(this);
    }
    
    public void asignarControlador(Controlador controlador){
        this.controlador=controlador;
    }
    
    public void enviarPaquete(String paqueteSerializado){
        clienteSocket.enviarMensajeAlServidor(paqueteSerializado);
    }
    
    @Override
    public void paqueteRecibido(String mensaje) {
        Paquete paquete=Paquete.deserializar(mensaje);
        String protocolo = paquete.obtenerProtocolo();
        String parametros=paquete.obtenerParametros();
        switch (protocolo) {
            case "LOGIN":
                //
                break;
            case "LOGOUT":
                //
                break;
            case "REGISTRO":
                JSONObject parametrosJson = new JSONObject(parametros);
                String estado = parametrosJson.getString("estado");
                if(estado.equals("true")){
                    controlador.cerrarFormularioDeRegistro();
                    controlador.mostrarFormularioPrincipal();
                }
                break;
            default:
                //
                break;
        }
    }
    
}
