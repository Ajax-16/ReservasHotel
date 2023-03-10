module com.example.reservashotel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    opens com.example.reservashotel to javafx.fxml;
    exports com.example.reservashotel;
}