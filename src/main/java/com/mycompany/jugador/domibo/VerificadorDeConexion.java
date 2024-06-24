/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author Pc
 */
public class VerificadorDeConexion extends Thread {
    private ClienteSocket clienteSocket;

    public VerificadorDeConexion(ClienteSocket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String ipDelServidor=clienteSocket.obtenerIpDelServidor();
                BufferedReader canalDeEntrada=clienteSocket.obtenerCanalEntrada();
                String respuesta = canalDeEntrada.readLine();
                InetAddress direccionDeRed = InetAddress.getByName(ipDelServidor);
                if (!direccionDeRed.isReachable(2000) || respuesta == null) {
                    clienteSocket.reconectar();
                }
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                clienteSocket.reconectar();
            }
        }
    }
}
