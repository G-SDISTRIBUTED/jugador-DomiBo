/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import com.mycompany.utilities.Paquete;
import org.json.JSONObject;
import com.google.gson.Gson;

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
                JSONObject parametrosDeVerificacionLogin = new JSONObject(parametros);
                String estadoDeRespuestaLogin = parametrosDeVerificacionLogin.getString("estado");
                if(estadoDeRespuestaLogin.equals("true")){
                    controlador.cerrarFormularioDeInicioDeSesion();
                    controlador.mostrarFormularioPrincipal();
                }
                break;
            case "MOVER_FICHA":
                System.out.println("Llega hasta mover ficha cliente");
                JSONObject parametrosJson = new JSONObject(parametros);
                String ficha = parametrosJson.getString("ficha");
                String posicion = parametrosJson.getString("posicion");
                Gson gson = new Gson();
                Ficha fichaAAgregar=gson.fromJson(ficha, Ficha.class);
                controlador.agregarFichaADroPanel(fichaAAgregar);
                break;
            case "REGISTRO":
                JSONObject parametrosDeVerificacionRegistro = new JSONObject(parametros);
                String estadoDeRespuestaDERegistro = parametrosDeVerificacionRegistro.getString("estado");
                if(estadoDeRespuestaDERegistro.equals("true")){
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
