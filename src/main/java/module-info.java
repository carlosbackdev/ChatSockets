module com.chatsocket {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.chatsocket.controller to javafx.fxml;
    exports com.chatsocket;
}