package com.chatsocket.hilos;

import com.chatsocket.controller.ServidorController;
import com.chatsocket.socket.Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ManejoClientes extends Thread {
    private DataInputStream input;
    private DataOutputStream output;
    private List<DataOutputStream> clientes;
    private Servidor servidor;

    public ManejoClientes(DataInputStream input, DataOutputStream output, List<DataOutputStream> clientes, Servidor servidor) {
        this.input = input;
        this.output = output;
        this.clientes = clientes;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            String mensaje;
            while (true) {
                mensaje = input.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);
                servidor.enviarACliente(mensaje, output);
                servidor.mensajesRecibidos();
            }
        } catch (IOException e) {
            System.out.println("Error manejando cliente: " + e.getMessage());
        } finally {
            try {
                clientes.remove(output);
                output.close();
                input.close();
            } catch (IOException e) {
                System.out.println("Error cerrando conexión del cliente: " + e.getMessage());
            }
        }
    }
}