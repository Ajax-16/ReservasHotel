package com.example.reservashotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InicioSesionController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label informacionxd;
    @FXML
    protected void iniciarSesionButton() throws IOException {

        try {

            if (usernameField.getText().equals("") || usernameField.getText().equals("")) {

                informacionxd.setStyle("-fx-text-fill:red;");

                informacionxd.setText("Alguno de los campos está vacío. Introduzca usuario y contraseña.");

            } else {

                String url = """
                        jdbc:mysql://localhost:3306/hotel?allowPublicKeyRetrieval=true&useSSL=false
                        """;

                HotelApp.conn = null;

                HotelApp.conn = DriverManager.getConnection(url, usernameField.getText(), passwordField.getText());

                System.out.println("Conectado con la base de datos con éxito.");

                FXMLLoader fxmlLoader1 = new FXMLLoader(HotelApp.class.getResource("primaryScene.fxml"));

                Scene primaryScene = new Scene(fxmlLoader1.load(), 1280, 720);

                primaryScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

                Stage primaryStage = new Stage();

                primaryStage.setScene(primaryScene);

                primaryStage.setTitle("Hotel");

                primaryStage.show();

                HotelApp.getLastStage().close();

                HotelApp.setLastStage(primaryStage);

            }

        }catch (SQLException e){

            informacionxd.setStyle("-fx-text-fill:red;");

            informacionxd.setText("Usuario y/o contraseña incorrecta.");

        }

    }

}
