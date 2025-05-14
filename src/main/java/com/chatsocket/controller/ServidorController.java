package com.chatsocket.controller;

import com.chatsocket.socket.Servidor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServidorController {
    @FXML private TextArea logArea;
    @FXML private TextField puertoField;
    @FXML private TextField broadcastField;
    @FXML private Button btnIniciar;
    @FXML private Button btnDetener;
    @FXML private Label statusLabel;
    @FXML private Label clientesLabel;
    @FXML private Label mensajesLabel;

    private ExecutorService executorService;

    private Servidor servidor;
    private Thread servidorThread;


    @FXML
    private void initialize() {
        btnDetener.setDisable(true);
        puertoField.setText("1234");
        executorService = Executors.newCachedThreadPool();
    }

    @FXML
    private void iniciarServidor() {

        try {
            int puerto = Integer.parseInt(puertoField.getText());
            servidor = new Servidor(this);

            // Ejecutamos el servidor en un hilo separado
            servidorThread = new Thread(() -> {
                try {
                    servidor.iniciarServidor(puerto);
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        agregarLog("Error en el servidor: " + e.getMessage());
                        statusLabel.setText("Error");
                    });
                }
            });

            servidorThread.start();

            btnIniciar.setDisable(true);
            btnDetener.setDisable(false);
            statusLabel.setText("Servidor iniciado");
            agregarLog("Servidor iniciado en puerto " + puerto);


        } catch (NumberFormatException e) {
            agregarLog("Puerto inválido");
        }
    }

    public void actualizarClientes(int cantidad) {
        Platform.runLater(() -> {
            clientesLabel.setText(""+cantidad);
        });
    }
    public void actualizarMensajes(int cantidad) {
        Platform.runLater(() -> {
            mensajesLabel.setText("" + cantidad);
        });
    }

    @FXML
    private void detenerServidor() {
        try {
            if (servidor != null) {
                servidor.cerrar();
                if (servidorThread != null && servidorThread.isAlive()) {
                    servidorThread.interrupt();
                }
                agregarLog("Servidor detenido");
                btnIniciar.setDisable(false);
                btnDetener.setDisable(true);
                statusLabel.setText("Desconectado");
            }
        } catch (Exception e) {
            agregarLog("Error al detener: " + e.getMessage());
        }
    }

    public void agregarLog(String mensaje) {
        Platform.runLater(() -> logArea.appendText(mensaje + "\n"));
    }

    @FXML
    private void enviarBroadcast(){
        if (servidor != null) {
            String mensaje = broadcastField.getText().trim();
            if (!mensaje.isEmpty()) {
                servidor.enviarBroadcast(mensaje);
                agregarLog("Broadcast enviado: " + mensaje);
                broadcastField.clear();
            }
        }
    }

    @FXML
    private void limpiarLog(){
        logArea.clear();
    }
}