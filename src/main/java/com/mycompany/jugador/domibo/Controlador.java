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
public class Controlador {
    private Jugador jugador;
    private FormularioDeInicio formularioDeInicio;
    private FormularioDeRegistro formularioDeRegistro;
    private FormularioDeInicioDeSesion formularioDeInicioDeSesion;
    private FormularioPrincipal formularioPrincipal;
    
    public Controlador(Jugador jugador, FormularioDeInicio formularioDeInicio, FormularioDeRegistro formularioDeRegistro, FormularioDeInicioDeSesion formularioDeInicioDeSesion, FormularioPrincipal formularioPrincipal){
        this.jugador=jugador;
        this.formularioDeInicio=formularioDeInicio;
        this.formularioDeRegistro=formularioDeRegistro;
        this.formularioDeInicioDeSesion=formularioDeInicioDeSesion;
        this.formularioPrincipal=formularioPrincipal;
    }
    
    public void mostrarFormularioDeInicio(){
        formularioDeInicio.setVisible(true);
    }
    
    public void mostrarFormularioDeRegistro(){
        formularioDeRegistro.setVisible(true);
    }
    
    public void mostrarFormularioDeInicioDeSesion(){
        formularioDeInicioDeSesion.setVisible(true);
    }
    
    public void mostrarFormularioPrincipal(){
        formularioPrincipal.setVisible(true);
    }
    
    public void cerrarFormularioDeInicio(){
        formularioDeInicio.dispose();
    }
    
    public void cerrarFormularioDeRegistro(){
        formularioDeRegistro.dispose();
    }
    
    public void cerrarFormularioDeInicioDeSesion(){
        formularioDeInicioDeSesion.dispose();
    }
    
    public void cerrarFormularioPrincipal(){
        formularioPrincipal.dispose();
    }
    
    public void registrarJugador(){
        JSONObject parametrosJson = new JSONObject();
        parametrosJson.put("nombre", formularioDeRegistro.obtenerDatoDelTextField1());
        parametrosJson.put("nombreDeUsuario", formularioDeRegistro.obtenerDatoDelTextField2());
        parametrosJson.put("correoElectronico", formularioDeRegistro.obtenerDatoDelTextField4());
        parametrosJson.put("telefono", formularioDeRegistro.obtenerDatoDelTextField5());
        parametrosJson.put("contrasena", formularioDeRegistro.obtenerDatoDelTextField6());
        String parametrosString=parametrosJson.toString();
        String protocolo="REGISTRO";
        Paquete paquete=new Paquete(protocolo,parametrosString);
        String paqueteSerializado=Paquete.serializar(paquete);
        jugador.enviarPaquete(paqueteSerializado);
    }
    
    public static void main(String args[]) {
        Jugador jugador=new Jugador();
        FormularioDeInicio formularioDeInicio=new FormularioDeInicio();
        FormularioDeRegistro formularioDeRegistro=new FormularioDeRegistro();
        FormularioDeInicioDeSesion formularioDeInicioDeSesion=new FormularioDeInicioDeSesion();
        FormularioPrincipal formularioPrincipal=new FormularioPrincipal();
        Controlador controlador=new Controlador(jugador, formularioDeInicio, formularioDeRegistro, formularioDeInicioDeSesion,formularioPrincipal);
        jugador.asignarControlador(controlador);
        formularioDeInicio.asignarControlador(controlador);
        formularioDeRegistro.asignarControlador(controlador);
        formularioDeInicioDeSesion.asignarControlador(controlador);
        formularioPrincipal.asignarControlador(controlador);
        formularioDeInicio.setVisible(true);
        
    }
}
