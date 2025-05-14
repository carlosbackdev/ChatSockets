package com.chatsocket.socket;

import com.chatsocket.controller.ClienteController;
import com.chatsocket.hilos.MensajesEscucha;

import java.io.*;
import java.net.Socket;

public class Cliente extends Thread {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ClienteController clienteController;

    public void run(String nombre, String host, int puerto, ClienteController clienteController,MensajesEscucha.MensajeListener listener) {
        this.clienteController=clienteController;
        try {
            socket = new Socket(host, puerto);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            output.writeUTF("se ha conectado: "+nombre);

            new MensajesEscucha(input, listener).start();

            clienteController.isConect(true);

        } catch (IOException e) {
            System.out.println("Error en cliente: " + e.getMessage());
            clienteController.agregarMensaje("Error en la conexion",true);
            clienteController.isConect(false);

        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            output.writeUTF(mensaje);
        } catch (IOException e) {
            System.out.println("Error enviando mensaje: " + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el cliente: " + e.getMessage());
        }
    }
}