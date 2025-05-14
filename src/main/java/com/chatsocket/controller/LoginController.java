package com.chatsocket.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private TextField nombreField;
    @FXML private TextField puertoField;
    @FXML private TextField hostField;

    @FXML
    private void conectar() {
        String nombre = nombreField.getText().trim();
        String host = hostField.getText().trim();
        String puertoText = puertoField.getText().trim();

        if (nombre.isEmpty() || puertoText.isEmpty() || host.isEmpty()) {
            System.out.println("Por favor complete todos los campos");
            return;
        }

        try {
            int puerto = Integer.parseInt(puertoText);

            // Cerrar la ventana actual
//            Stage stage = (Stage) nombreField.getScene().getWindow();
//            stage.close();

            // Abrir la ventana del cliente
            abrirVentanaCliente(nombre, host, puerto);

        } catch (NumberFormatException e) {
            System.out.println("El puerto debe ser un número");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaCliente(String nombre, String host, int puerto) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/chatsocket/cliente.fxml"));
            Parent root = loader.load();

            ClienteController clienteController = loader.getController();
            if (clienteController == null) {
                throw new RuntimeException("No se pudo obtener el controlador del cliente");
            }
            clienteController.inicializarCliente(nombre, host, puerto);

            Stage stage = new Stage();
            stage.setTitle("Chat - " + nombre);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.out.println( "No se pudo cargar la interfaz del cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}