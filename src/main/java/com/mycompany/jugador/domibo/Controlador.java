/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import com.google.gson.Gson;
import com.mycompany.utilities.Paquete;
import com.mycompany.utilities.Sala;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
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
    private FormularioDeSala formularioDeSala;
    FormularioDelTablero formularioDelTablero;
    private Thread getRoomsThread;
    public List<String> tokenSalas=new ArrayList<>();

    public Controlador(Jugador jugador, FormularioDeInicio formularioDeInicio, FormularioDeRegistro formularioDeRegistro, FormularioDeInicioDeSesion formularioDeInicioDeSesion, FormularioPrincipal formularioPrincipal, FormularioDeSala formularioDeSala,FormularioDelTablero formularioDelTablero){
        this.jugador=jugador;
        this.formularioDeInicio=formularioDeInicio;
        this.formularioDeRegistro=formularioDeRegistro;
        this.formularioDeInicioDeSesion=formularioDeInicioDeSesion;
        this.formularioPrincipal=formularioPrincipal;

        this.formularioDeSala=formularioDeSala;

        this.formularioDelTablero=formularioDelTablero;

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
        getRoomsThread = new Thread(this::getRooms);
        getRoomsThread.start();
    }
    
    public void mostrarFormularioDeSala(){
        formularioDeSala.setNombreSala(jugador.sala.getName());
        formularioDeSala.setBotonesCreador(jugador.sala.getCreador().getId().equals(jugador.usuario.getId()));
       // formularioDeSala.setJugadores();
        formularioDeSala.setVisible(true);
    }
    
    public void mostrarFormularioDelTablero(){
        formularioDelTablero.setVisible(true);
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
        getRoomsThread.interrupt();
        formularioPrincipal.dispose();
    }
    
    public void cerrarFormularioDeSala(){
        formularioDeSala.dispose();
    }
    
    public void registrarJugador(){
        try {
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
        } catch (JSONException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarSesionDelJugador(){
        try {
            JSONObject parametrosJson = new JSONObject();
            parametrosJson.put("nombreDeUsuario", formularioDeInicioDeSesion.obtenerDatoDelTextField1());
            parametrosJson.put("contrasena", formularioDeInicioDeSesion.obtenerDatoDelTextField2());
            String parametrosString=parametrosJson.toString();
            String protocolo="LOGIN";
            Paquete paquete=new Paquete(protocolo,parametrosString);
            String paqueteSerializado=Paquete.serializar(paquete);
            jugador.enviarPaquete(paqueteSerializado);
        } catch (JSONException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearSala(String nombre){
        try {
            JSONObject parametrosJson = new JSONObject();
            parametrosJson.put("idUsuario", jugador.usuario.getId());
            parametrosJson.put("nombre", nombre);
            String parametrosString=parametrosJson.toString();
            String protocolo="CREAR";
            Paquete paquete=new Paquete(protocolo,parametrosString);
            String paqueteSerializado=Paquete.serializar(paquete);
            jugador.enviarPaquete(paqueteSerializado);
        } catch (JSONException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getRooms(){
        try{
            while(true){
                String protocolo="SALAS";
                Paquete paquete=new Paquete(protocolo,null);
                String paqueteSerializado=Paquete.serializar(paquete);
                jugador.enviarPaquete(paqueteSerializado);
                Thread.sleep(10000);
            }
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void actualizarSalas(List<Sala> salas){
        List<String> salasString=new ArrayList<>();
        for(Sala sala: salas){
            salasString.add(sala.getName()+" by "+sala.getCreador().getUsername());
            tokenSalas.add(sala.getToken());
        }
        formularioPrincipal.actualizarSalas(salasString);
    }
    
    public void entrarASala(String tokenSala){
        try {
            JSONObject parametrosJson = new JSONObject();
            parametrosJson.put("tokenSala", tokenSala);
            String parametrosString=parametrosJson.toString();
            String protocolo="ENTRAR";
            Paquete paquete=new Paquete(protocolo,parametrosString);
            String paqueteSerializado=Paquete.serializar(paquete);
            jugador.enviarPaquete(paqueteSerializado);
        } catch (JSONException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]) {
        Jugador jugador=new Jugador();
        FormularioDeInicio formularioDeInicio=new FormularioDeInicio();
        FormularioDeRegistro formularioDeRegistro=new FormularioDeRegistro();
        FormularioDeInicioDeSesion formularioDeInicioDeSesion=new FormularioDeInicioDeSesion();
        FormularioPrincipal formularioPrincipal=new FormularioPrincipal();

        FormularioDeSala formularioDeSala = new FormularioDeSala();

        FormularioDelTablero formularioDelTablero=new FormularioDelTablero();
        Controlador controlador=new Controlador(jugador, formularioDeInicio, formularioDeRegistro, formularioDeInicioDeSesion,formularioPrincipal,formularioDeSala,formularioDelTablero);
        jugador.asignarControlador(controlador);
        formularioDeInicio.asignarControlador(controlador);
        formularioDeRegistro.asignarControlador(controlador);
        formularioDeInicioDeSesion.asignarControlador(controlador);
        formularioPrincipal.asignarControlador(controlador);
        formularioDeSala.asignarControlador(controlador);
        formularioDeInicio.setVisible(true);

        formularioDelTablero.asignarControlador(controlador);
        //formularioDelTablero.setVisible(true);

        
    }

    public Boolean verificarJugada(JFicha ficha, String sentido) {
        
        JFicha fichaObservada=formularioDelTablero.obetenerFicha(sentido);
        System.out.println("Ficha en tablero: "+fichaObservada.toString());
        if (sentido=="izquierda"){
            int derecha=ficha.getPuntosDerecha();
            System.out.println("Ficha a poner a la izq: "+derecha);
            int izquierda=fichaObservada.getPuntosIzquierda();
            System.out.println("Ficha a la izq: "+izquierda);
            return derecha==izquierda;
        }else{
            int izquierda=ficha.getPuntosIzquierda();
            System.out.println("Ficha a poner a la derecha: "+izquierda);
            int derecha=fichaObservada.getPuntosDerecha();
            System.out.println("Ficha a la derecha: "+derecha);
            return derecha==izquierda;
        }
    }

    public void enviarJugada(JFicha ficha, String sentido) {
        Ficha fichaAEnviar=new Ficha(ficha.getPuntosIzquierda(), ficha.getPuntosDerecha());
        Gson gson = new Gson();
        String fichaSerializada=gson.toJson(fichaAEnviar);
        JSONObject movimientoJson = new JSONObject();
        movimientoJson.put("ficha", fichaSerializada);
        movimientoJson.put("posicion", sentido);
        String parametrosString=movimientoJson.toString();
        String protocolo = "MOVER_FICHA";
        Paquete paquete=new Paquete(protocolo,parametrosString);
        String paqueteSerializado = Paquete.serializar(paquete);
        jugador.enviarPaquete(paqueteSerializado);
    }
    
    public void iniciarPartida(){
        try {
            JSONObject parametrosJson = new JSONObject();
            parametrosJson.put("tokenSala",jugador.sala.getToken());
            String parametrosString=parametrosJson.toString();
            String protocolo="INICIAR";
            Paquete paquete=new Paquete(protocolo,parametrosString);
            String paqueteSerializado=Paquete.serializar(paquete);
            jugador.enviarPaquete(paqueteSerializado);
        } catch (JSONException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarFichaADroPanel(Ficha fichaAAgregar) {
        JFicha ficha=new JFicha(fichaAAgregar.getPuntosIzquierda(), fichaAAgregar.getPuntosDerecha());
        formularioDelTablero.addFicha(ficha);
    }
}
