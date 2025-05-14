package com.chatsocket.controller;


import com.chatsocket.socket.Cliente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ClienteController {
    @FXML private VBox messageContainer;
    @FXML private TextField messageField;
    @FXML private ScrollPane messageScrollPane;
    @FXML private Label isConected;
    @FXML private Label usuario;

    private Cliente cliente;
    private String nombre;

    @FXML
    public void inicializarCliente(String nombre, String host, int puerto) {
        this.nombre = nombre;
        usuario.setText(nombre);
        cliente=new Cliente();
        try {
            cliente.run(nombre,host, puerto,this, mensaje -> {
                Platform.runLater(() -> agregarMensaje(mensaje, false));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void enviar() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            agregarMensaje(message, true);
            messageField.clear();
            cliente.enviarMensaje(message);
        }
    }

    public void agregarMensaje(String text, boolean isSent) {
        Text messageText = new Text(text);
        messageText.setWrappingWidth(300);

        TextFlow textFlow = new TextFlow(messageText);
        textFlow.setMaxWidth(300);
        textFlow.setStyle("-fx-background-color: " + (isSent ? "#dcf8c6" : "white") +
                "; -fx-padding: 10px; -fx-background-radius: 10px;");

        HBox messageBox = new HBox(textFlow);
        messageBox.setAlignment(isSent ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        messageBox.setPadding(new Insets(5));

        messageContainer.getChildren().add(messageBox);

        // Auto-scroll al final
        messageScrollPane.setVvalue(1.0);
    }

    public void isConect(boolean conectado) {
        if (conectado) {
            isConected.setText("En Linea");
        } else {
            isConected.setText("Desconectado");
        }
    }
    @FXML
    private void cerrar() {
        cliente.enviarMensaje("se ha desconectado: "+nombre);
        cliente.desconectar();

        Stage stage = (Stage) messageField.getScene().getWindow();
        stage.close();

    }
}