module com.example.tap2024proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tap2024proyecto to javafx.fxml;
    exports com.example.tap2024proyecto;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    requires kernel;
    requires layout;
    opens com.example.tap2024proyecto.models;

}