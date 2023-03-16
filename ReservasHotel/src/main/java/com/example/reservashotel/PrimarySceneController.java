package com.example.reservashotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class PrimarySceneController implements Initializable{

    HotelApp app = new HotelApp();

    public static boolean insertAcces = false;

    public static boolean modifyAcces = false;

    public static boolean deleteAcces = false;
    private SecondarySceneController secondarySceneController;
    @FXML
    private VBox areaInserciones;
    @FXML
    private TextField areaBusqueda;
    @FXML
    public TextFlow textFlow;
    @FXML
    public Text mensaje;

    public PrimarySceneController() throws FileNotFoundException {



    }

    @FXML
    protected void onInsertButton() throws IOException{

        insertAcces = true;

        iniciaSegundaEscena();

    }
    @FXML
    protected void onModifyButton() throws IOException{

        modifyAcces = true;

        iniciaSegundaEscena();

    }
    @FXML
    protected void onDeleteButton() throws IOException{

        deleteAcces = true;

        iniciaSegundaEscena();

    }

    @FXML
    protected void onRecargarButton() throws SQLException {

        clean();

        Statement stm = HotelApp.conn.createStatement();
        String select = """
        SELECT reservas.id, reservas.nombre, CONCAT_WS(' ', reservas.apellido1, reservas.apellido2) AS apellidos, alojamiento.tipo, alojamiento.num_noches, alojamiento.precio_total
         FROM reservas INNER JOIN alojamiento
         ON reservas.id_alojamiento = alojamiento.id;
         """;

        ResultSet rs = stm.executeQuery(select);

        while(rs.next()) {

            insertaFila(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), String.valueOf(rs.getInt(5)), String.valueOf(rs.getInt(6)));

        }

    }

    @FXML
    protected void onSearchButton() throws SQLException {

        String palabra = areaBusqueda.getText();

        if(!palabra.equals("")) {

            String sqlSearch = "SELECT reservas.id, reservas.nombre, CONCAT_WS(' ', reservas.apellido1, reservas.apellido2) AS apellidos, alojamiento.tipo, alojamiento.num_noches, alojamiento.precio_total FROM reservas INNER JOIN alojamiento ON reservas.id_alojamiento = alojamiento.id WHERE reservas.nombre LIKE '" + palabra + "%'";

            Statement search = HotelApp.conn.createStatement();

            ResultSet rs = search.executeQuery(sqlSearch);

            clean();

            while (rs.next()){

                insertaFila(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), String.valueOf(rs.getInt(5)), String.valueOf(rs.getInt(6)));

            }

        }else{

            actualizaMensajes("Busqueda no válida. Debes introducir algún valor.", "alert-danger");

        }

    }

    public void iniciaSegundaEscena() throws IOException {

        FXMLLoader fxmlLoader2 = new FXMLLoader(HotelApp.class.getResource("secondaryScene.fxml"));

        Scene secondaryScene = new Scene(fxmlLoader2.load(), 1280, 720);

        secondaryScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        Stage secondaryStage = new Stage();

        secondaryStage.setScene(secondaryScene);

        secondaryStage.setTitle("Gestor de reservas");

        secondarySceneController = fxmlLoader2.getController();

        secondaryStage.show();

        HotelApp.getLastStage().close();

        HotelApp.setLastStage(secondaryStage);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            Statement stm = HotelApp.conn.createStatement();
            String select = """
        SELECT reservas.id, reservas.nombre, CONCAT_WS(' ', reservas.apellido1, reservas.apellido2) AS apellidos, alojamiento.tipo, alojamiento.num_noches, alojamiento.precio_total
         FROM reservas INNER JOIN alojamiento
         ON reservas.id_alojamiento = alojamiento.id;
         """;

            ResultSet rs = stm.executeQuery(select);

            while(rs.next()) {

                insertaFila(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), String.valueOf(rs.getInt(5)), String.valueOf(rs.getInt(6)));

            }

        } catch (SQLException e) {

            actualizaMensajes("Base de datos no coincidente. Se requiere revisión.", "alert-danger");

        }

        try {
            String saludoPorHora = (LocalTime.now().getHour() < 12 &&  LocalTime.now().getHour() > 6) ? "Buenos días" : ((LocalTime.now().getHour() >= 12 && LocalTime.now().getHour() < 20) ? "Buenas tardes" : "Buenas noches");
            actualizaMensajes(saludoPorHora + " usuario: " + eliminaIP(HotelApp.conn.getMetaData().getUserName()), "alert-success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertaFila(String idS, String nombreS, String apellidosS, String tipoS, String numNochesS, String precioS){

            HBox fila = new HBox();
            fila.setAlignment(Pos.TOP_CENTER);

            VBox huecoId = new VBox();
            huecoId.setPrefHeight(200);
            huecoId.setPrefWidth(137);
            Text id = new Text();
            huecoId.getChildren().add(id);
            id.setText(idS);

            VBox huecoNombre = new VBox();
            huecoNombre.setPrefHeight(200);
            huecoNombre.setPrefWidth(137);
            Text nombre = new Text();
            huecoNombre.getChildren().add(nombre);
            nombre.setText(nombreS);

            VBox huecoApellidos = new VBox();
            huecoApellidos.setPrefHeight(200);
            huecoApellidos.setPrefWidth(137);
            Text apellidos = new Text();
            huecoApellidos.getChildren().add(apellidos);
            apellidos.setText(apellidosS);

            VBox huecoTipo = new VBox();
            huecoTipo.setPrefHeight(200);
            huecoTipo.setPrefWidth(137);
            Text tipo = new Text();
            huecoTipo.getChildren().add(tipo);
            tipo.setText(tipoS);

            VBox huecoNumNoches = new VBox();
            huecoNumNoches.setPrefHeight(200);
            huecoNumNoches.setPrefWidth(137);
            Text numNoches = new Text();
            huecoNumNoches.getChildren().add(numNoches);
            numNoches.setText(numNochesS);

            VBox huecoPrecio = new VBox();
            huecoPrecio.setPrefHeight(200);
            huecoPrecio.setPrefWidth(137);
            Text precio = new Text();
            huecoPrecio.getChildren().add(precio);
            precio.setText(precioS);

            huecoId.setAlignment(Pos.CENTER);
            huecoNombre.setAlignment(Pos.CENTER);
            huecoApellidos.setAlignment(Pos.CENTER);
            huecoTipo.setAlignment(Pos.CENTER);
            huecoNumNoches.setAlignment(Pos.CENTER);
            huecoPrecio.setAlignment(Pos.CENTER);

            fila.setPrefHeight(71);

            // Agregar cada columna VBox a la fila HBox
            fila.getChildren().addAll(huecoId, huecoNombre, huecoApellidos, huecoTipo, huecoNumNoches, huecoPrecio);

            fila.getStyleClass().add("panel-default");

            // Agregar la fila HBox al VBox principal
            areaInserciones.getChildren().add(fila);

    }

    public void clean() {

        areaInserciones.getChildren().clear();

    }

    public void actualizaMensajes(String mensajeTexto, String claseCSS) {
        mensaje.setText(mensajeTexto);
        textFlow.getStyleClass().removeAll("alert-success", "alert-danger");
        textFlow.getStyleClass().add(claseCSS);
    }

    public String eliminaIP(String user){

        int breakpoint = 0;

        for(int i = 0; i<user.toCharArray().length; i++){

            if(user.toCharArray()[i] == '@'){

                breakpoint = i;

            }

        }

        return user.substring(0, breakpoint);

    }

}