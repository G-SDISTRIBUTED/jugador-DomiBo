/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import com.mycompany.utilities.Paquete;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Pc
 */
public class ClienteSocket implements IEscuchadorDeEventosDelCliente {
    private Socket clienteSocket;
    private BufferedReader canalDeEntrada;
    private PrintWriter canalDesalida;
    private int puertoDelServidor;
    private String ipDelServidor;
    private EscuchadorDeMensajes escuchadorDeMensajes;
    private VerificadorDeConexion verificadorDeConexion;
    private IObservadorDeClienteSocket observador;
    
    public ClienteSocket(int puerto, String host){
        conectar();
    }
    
    public void conectar(){
        try {
            clienteSocket = new Socket(ipDelServidor, puertoDelServidor);
            canalDeEntrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            canalDesalida = new PrintWriter(clienteSocket.getOutputStream(), true);
            escuchadorDeMensajes=new EscuchadorDeMensajes(this, canalDeEntrada);
            escuchadorDeMensajes.start();
            verificadorDeConexion=new VerificadorDeConexion(this);
            verificadorDeConexion.start();
        } catch (IOException e) {
            e.printStackTrace();
            escuchadorDeMensajes.interrupt();
            verificadorDeConexion.interrupt();
            reconectar();
        }
    }
    
    public void reconectar(){
        try {
            conectar();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void enviarMensajeAlServidor(String mensaje){
        canalDesalida.println(mensaje);
    }
    
    public void asignarObservador(IObservadorDeClienteSocket observador) {
        this.observador=observador;
    }
    
    private void notificarObservador(String mensaje) {
        observador.paqueteRecibido(mensaje);
    }
    
    public String obtenerIpDelServidor(){
        return clienteSocket.getInetAddress().getHostAddress();
    }
    
    public BufferedReader obtenerCanalEntrada(){
        return canalDeEntrada;
    }

    @Override
    public void mensajeRecibido(EventoDeMensajeRecibido evento) {
        String mensaje = evento.obtenerMensaje();
        notificarObservador(mensaje);
    }
}
