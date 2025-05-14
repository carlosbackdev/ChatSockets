package com.chatsocket.hilos;

import java.io.DataInputStream;
import java.io.IOException;

public class MensajesEscucha extends Thread {
    private final DataInputStream input;
    private final MensajeListener listener;

    public interface MensajeListener {
        void onMensajeRecibido(String mensaje);
    }

    public MensajesEscucha(DataInputStream input, MensajeListener listener) {
        this.input = input;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            String respuesta;
            while ((respuesta = input.readUTF()) != null) {
                if (listener != null) {
                    listener.onMensajeRecibido(respuesta); // Notificar al listener
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo del servidor: " + e.getMessage());
        }
    }
}