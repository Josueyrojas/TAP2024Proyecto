module com.example.tap2024proyecto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tap2024proyecto to javafx.fxml;
    exports com.example.tap2024proyecto;
}