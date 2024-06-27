/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import com.mycompany.utilities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

/**
 *
 * @author Pc
 */
public class Jugador implements IObservadorDeClienteSocket {
    ClienteSocket clienteSocket;
    Controlador controlador;
    Usuario usuario;
    Sala sala;
    
    public Jugador(){
        clienteSocket=new ClienteSocket(12345,"192.168.231.17");
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
        try {
            Paquete paquete=Paquete.deserializar(mensaje);
            String protocolo = paquete.obtenerProtocolo();
            String parametros=paquete.obtenerParametros();
            System.out.println("Protocolo: "+protocolo+", parametros: "+parametros);
            switch (protocolo) {
                case "LOGIN":{
                    JSONObject parametrosLogin = new JSONObject(parametros);
                    String estadoDeRespuestaLogin = parametrosLogin.getString("estado");
                    if(estadoDeRespuestaLogin.equals("true")){
                        Integer id = parametrosLogin.getInt("id");
                        String nombre = parametrosLogin.getString("nombre");
                        String nombreDeUsuario = parametrosLogin.getString("nombreDeUsuario");
                        String correoElectronico = parametrosLogin.getString("correoElectronico");
                        String telefono = parametrosLogin.getString("telefono");
                        String contrasena = parametrosLogin.getString("contrasena");
                        
                        this.usuario = new Usuario(id,nombre,nombreDeUsuario,correoElectronico, telefono,contrasena);
                        
                        controlador.cerrarFormularioDeInicioDeSesion();
                        controlador.mostrarFormularioPrincipal();
                    }
                    break;
                }    
                case "MOVER_FICHA":{
                  System.out.println("Llega hasta mover ficha cliente");
                  JSONObject parametrosJson = new JSONObject(parametros);
                  String ficha = parametrosJson.getString("ficha");
                  String posicion = parametrosJson.getString("posicion");
                  Gson gson = new Gson();
                  Ficha fichaAAgregar=gson.fromJson(ficha, Ficha.class);
                  controlador.agregarFichaADroPanel(fichaAAgregar);
                  break;
                }
                case "REGISTRO":{
                    JSONObject parametrosRegistro = new JSONObject(parametros);
                    String estadoDeRespuestaDERegistro = parametrosRegistro.getString("estado");
                    if(estadoDeRespuestaDERegistro.equals("true")){
                        Integer id = parametrosRegistro.getInt("id");
                        String nombre = parametrosRegistro.getString("nombre");
                        String nombreDeUsuario = parametrosRegistro.getString("nombreDeUsuario");
                        String correoElectronico = parametrosRegistro.getString("correoElectronico");
                        String telefono = parametrosRegistro.getString("telefono");
                        String contrasena = parametrosRegistro.getString("contrasena");
                        
                        this.usuario = new Usuario(id,nombre,nombreDeUsuario,correoElectronico, telefono,contrasena);
                        
                        controlador.cerrarFormularioDeRegistro();
                        controlador.mostrarFormularioPrincipal();
                    }
                    break;
                }    
                case "CREAR":{
                    JSONObject parametrosSala = new JSONObject(parametros);
                    String estadoDeRespuesta= parametrosSala.getString("estado");
                    if(estadoDeRespuesta.equals("true")){
                        String nombreDeSala = parametrosSala.getString("nombre");
                        String token = parametrosSala.getString("token");
                        
                        this.sala = new Sala(token, nombreDeSala, usuario);
                        
                        controlador.cerrarFormularioPrincipal();
                        controlador.mostrarFormularioDeSala();
                    }
                    break;
                }  
                case "SALAS":{
                    JSONObject parametrosSalas = new JSONObject(parametros);
                    String estadoDeRespuestaDERegistro = parametrosSalas.getString("estado");
                    if(estadoDeRespuestaDERegistro.equals("true")){
                        List<Sala> salas=new ArrayList<>();
                        for(int i=1; i < parametrosSalas.length(); i++){
                            String parametrosSalaString = parametrosSalas.getString("sala"+i);
                            JSONObject parametrosSala = new JSONObject(parametrosSalaString);
                            String tokenSala = parametrosSala.getString("token");
                            String nombreSala = parametrosSala.getString("nombreSala");
                            String nombreCreador = parametrosSala.getString("nombreCreador");
                            
                            Sala sala = new Sala(tokenSala,nombreSala, new Usuario(nombreCreador));
                            salas.add(sala);
                        }
                        controlador.actualizarSalas(salas);
                    }
                    break;
                } 
                case "ENTRAR":{
                    JSONObject parametrosRegistro = new JSONObject(parametros);
                    String estadoDeRespuesta = parametrosRegistro.getString("estado");
                    if(estadoDeRespuesta.equals("true")){
                        String token = parametrosRegistro.getString("token");
                        String nombreDeSala = parametrosRegistro.getString("nombre");
                        Integer idCreador = parametrosRegistro.getInt("idCreador");
                        String nombreCreador = parametrosRegistro.getString("nombreCreador");
                        
                        this.sala = new Sala(token, nombreDeSala, new Usuario(idCreador, nombreCreador));
                        
                        controlador.cerrarFormularioPrincipal();
                        controlador.mostrarFormularioDeSala();
                    }
                    break;
                }   
                case "INICIAR":{
                    JSONObject parametrosRegistro = new JSONObject(parametros);
                    String estadoDeRespuesta = parametrosRegistro.getString("estado");
                    if(estadoDeRespuesta.equals("true")){
                        
                        controlador.cerrarFormularioDeSala();
                        controlador.mostrarFormularioDelTablero();
                    }
                    break;
                } 
                default:
                    //
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
}
