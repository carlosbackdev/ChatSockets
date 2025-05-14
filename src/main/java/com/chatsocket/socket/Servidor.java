package com.chatsocket.socket;

import com.chatsocket.controller.ServidorController;
import com.chatsocket.hilos.ManejoClientes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Servidor extends Thread {
    private ServerSocket serverSocket;
    private List<DataOutputStream> clientes = Collections.synchronizedList(new ArrayList<>());
    private ServidorController servidorController;
    private int mensajesRecibidos=0;

    public Servidor(ServidorController servidorController) {
        this.servidorController = servidorController;

    }

    public void iniciarServidor(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en puerto " + puerto);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket.toString());
                servidorController.agregarLog(String.format("Cliente conectado: %s", clienteSocket.getInetAddress()));

                DataInputStream input = new DataInputStream(clienteSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clienteSocket.getOutputStream());
                clientes.add(output);
                output.writeUTF("Clientes conectados: " + (clientes.size()-1));
                servidorController.actualizarClientes(clientes.size());


                ManejoClientes manejoClientes = new ManejoClientes(input, output, clientes, this);
                try {
                    manejoClientes.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }



    public void enviarACliente(String mensaje, DataOutputStream remitente) {
        synchronized (clientes) {
            for (DataOutputStream cliente : clientes) {
                if (cliente != remitente) {
                    try {
                        cliente.writeUTF(mensaje);
                    } catch (IOException e) {
                        System.out.println("Error enviando mensaje a un cliente: " + e.getMessage());
                    }
                }
            }
        }
    }



    public void enviarBroadcast(String mensaje) {
        synchronized (clientes) {
            for (DataOutputStream cliente : clientes) {
                try {
                    cliente.writeUTF(mensaje);
                } catch (IOException e) {
                    System.out.println("Error enviando mensaje a un cliente: " + e.getMessage());
                    clientes.remove(cliente);
                }
            }
        }
    }

    public void mensajesRecibidos(){
        mensajesRecibidos++;
        servidorController.actualizarMensajes(mensajesRecibidos);
    }

    public void cerrar() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            synchronized (clientes) {
                for (DataOutputStream cliente : clientes) {
                    try {
                        cliente.close();
                    } catch (IOException e) {
                        System.out.println("Error cerrando cliente: " + e.getMessage());
                    }
                }
                clientes.clear();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el servidor: " + e.getMessage());
        }
    }
}