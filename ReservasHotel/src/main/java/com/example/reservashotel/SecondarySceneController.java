package com.example.reservashotel;

import com.example.reservashotel.gestor.GestorReservas;
import com.example.reservashotel.modelos.Alojamiento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class SecondarySceneController implements Initializable {

    @FXML
    private VBox panelEliminar;
    @FXML
    private VBox panelModificar;
    @FXML
    private VBox panelInsertar;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido1;
    @FXML
    private TextField apellido2;
    @FXML
    private TextField tipoAlojamiento;
    GestorReservas gestorReservas = new GestorReservas();
    @FXML
    private TextField numNoches;
    @FXML
    private TextField fieldIdDelete;
    @FXML
    private TextField fieldModify;
    @FXML
    private VBox insertVbox;
    private static Integer numAlojamientos;

    public PreparedStatement insertAlojamiento;
    public PreparedStatement insertReserva;
    Properties  properties = new Properties();
    FileOutputStream fo = new FileOutputStream("id.txt");
    private PrimarySceneController primarySceneController;

    public SecondarySceneController() throws FileNotFoundException {



    }

    @FXML
    public void insertButton() throws IOException, SQLException {

        // CODIGO INSERTAR RESERVA

        if(nombre.getText().equals("") || apellido1.getText().equals("") || apellido2.getText().equals("") ||
                tipoAlojamiento.getText().equals("") || numNoches.getText().equals("")){

            iniciaPrimeraEscena();

            primarySceneController.actualizaMensajes("La inserción no se realizó con éxito porque algunos campos estaban vacíos.", "alert-danger");

        }else{

            Alojamiento alojamientoTemp = new Alojamiento(tipoAlojamiento.getText(), Integer.parseInt(numNoches.getText()));

            try {

                // Consulta para obtener el último id_alojamiento introducido en la tabla alojamiento
                String selectMaxId = "SELECT MAX(id) FROM alojamiento;";
                Statement maxIdStmt = HotelApp.conn.createStatement();
                ResultSet rs = maxIdStmt.executeQuery(selectMaxId);

                // Obtener el valor máximo y actualizar la variable numAlojamientos
                int maxId = rs.next() ? rs.getInt(1) : 0;
                numAlojamientos = maxId;

                // Cerrar la consulta
                maxIdStmt.close();
            } catch (SQLException e) {

                iniciaPrimeraEscena();

                primarySceneController.actualizaMensajes("Error al obtener credenciales en ID alojamiento. Revise la base de datos.", "alert-warning");

            }

            numAlojamientos++;

// Actualizar el valor en el archivo properties
            properties.setProperty("Id", String.valueOf(numAlojamientos));

// Guardar el archivo properties
            properties.store(fo, "alojamientos id");

// Preparar la consulta de inserción de alojamiento
            insertAlojamiento = HotelApp.conn.prepareStatement("""
    INSERT INTO alojamiento (tipo, precio, num_noches, precio_total) VALUES (?, ?, ?, ?)
""");

            insertAlojamiento.setString(1, alojamientoTemp.getTipo());
            insertAlojamiento.setInt(2, alojamientoTemp.getPrecio());
            insertAlojamiento.setInt(3, alojamientoTemp.getNumNoches());
            insertAlojamiento.setInt(4, alojamientoTemp.getPrecioTotal());

// Preparar la consulta de inserción de reserva
            insertReserva = HotelApp.conn.prepareStatement("""
    INSERT INTO reservas (nombre, apellido1, apellido2, id_alojamiento) VALUES (?, ?, ?, ?)
""");

            insertReserva.setString(1, nombre.getText());
            insertReserva.setString(2, apellido1.getText());
            insertReserva.setString(3, apellido2.getText());
            insertReserva.setInt(4, numAlojamientos);

            insertAlojamiento.executeUpdate();
            insertReserva.executeUpdate();

            nombre.clear();
            apellido1.clear();
            apellido2.clear();
            numNoches.clear();

            iniciaPrimeraEscena();

            primarySceneController.actualizaMensajes("Inserción realizada con éxito", "alert-success");

        }

    }

    @FXML
    protected void deleteButton() throws SQLException, IOException {

        Statement getIdStm = HotelApp.conn.createStatement();

        ResultSet IdRs = getIdStm.executeQuery("SELECT id FROM reservas WHERE id = " + Integer.parseInt(fieldIdDelete.getText()));

        if((IdRs.next() ? IdRs.getInt(1) : 0 ) == Integer.parseInt(fieldIdDelete.getText())) {

            String sql = "DELETE FROM reservas WHERE id = " + Integer.parseInt(fieldIdDelete.getText());

            Statement pst = HotelApp.conn.createStatement();

            pst.executeUpdate(sql);

            iniciaPrimeraEscena();

        }else{

            iniciaPrimeraEscena();

            primarySceneController.actualizaMensajes("Eliminación fallida. Id de reserva no encontrado.", "alert-danger");

        }

    }
    @FXML
    protected void modifyButton() throws SQLException, IOException{

        Statement getIdStm = HotelApp.conn.createStatement();

        ResultSet IdRs = getIdStm.executeQuery("SELECT id FROM reservas WHERE id = " + Integer.parseInt(fieldModify.getText()));

        if((IdRs.next() ? IdRs.getInt(1) : 0 ) == Integer.parseInt(fieldModify.getText())) {

            panelModificar.getChildren().remove(1);




        }else{

            iniciaPrimeraEscena();

            primarySceneController.actualizaMensajes("Modificación fallida. Id de reserva no encontrado.", "alert-danger");

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FXMLLoader fxmlLoader1 = new FXMLLoader(HotelApp.class.getResource("primaryScene.fxml"));

        try {
            fxmlLoader1.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        primarySceneController = fxmlLoader1.getController();

        if(PrimarySceneController.insertAcces){

            panelInsertar.getStyleClass().remove(0);
            panelInsertar.getStyleClass().add("panel-success");
            panelInsertar.setDisable(false);
            panelModificar.setDisable(true);
            panelEliminar.setDisable(true);
            PrimarySceneController.insertAcces = false;

        } else if (PrimarySceneController.modifyAcces) {

            panelModificar.getStyleClass().remove(0);
            panelModificar.getStyleClass().add("panel-success");
            panelInsertar.setDisable(true);
            panelModificar.setDisable(false);
            panelEliminar.setDisable(true);
            PrimarySceneController.modifyAcces = false;

        } else {

            panelEliminar.getStyleClass().remove(0);
            panelEliminar.getStyleClass().add("panel-success");
            panelInsertar.setDisable(true);
            panelModificar.setDisable(true);
            panelEliminar.setDisable(false);
            PrimarySceneController.deleteAcces = false;

        }

    }




    public void iniciaPrimeraEscena() throws IOException {

        FXMLLoader fxmlLoader1 = new FXMLLoader(HotelApp.class.getResource("primaryScene.fxml"));

        Scene primaryScene = new Scene(fxmlLoader1.load(), 1280, 720);

        primaryScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        Stage primaryStage = new Stage();

        primaryStage.setScene(primaryScene);

        primaryStage.setTitle("Hotel");

        primarySceneController = fxmlLoader1.getController();

        primaryStage.show();

        HotelApp.getLastStage().close();

        HotelApp.setLastStage(primaryStage);

    }



}
