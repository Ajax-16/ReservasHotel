package com.example.reservashotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;

public class HotelApp extends Application {

    public static Connection conn;

    public Scene primaryScene;

    private static Stage lastStage;

    public static void setLastStage(Stage lastStage){
        HotelApp.lastStage = lastStage;
    }

    public static Stage getLastStage(){return lastStage;}

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(HotelApp.class.getResource("inicioSesion.fxml"));
        primaryScene = new Scene(fxmlLoader1.load(), 600, 400);
        primaryScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setResizable(false);
        stage.setTitle("Hotel");
        stage.setScene(primaryScene);
        stage.show();
        lastStage = stage;
    }

    public static void main(String[] args) throws SQLException {

        // empieza el programa

        launch();

    }

}