package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.GeneroDAO;

public class FormGenero extends Stage {
    private TextField txtNombreGenero;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private GeneroDAO objGenero;
    private TableView<GeneroDAO> tblGenero;

    public FormGenero(TableView<GeneroDAO> tableView, GeneroDAO genero) {
        tblGenero = tableView;
        crearUI();

        if (genero != null) {
            this.objGenero = genero;
            txtNombreGenero.setText(objGenero.getNombreGenero());
            this.setTitle("Editar Género");
        } else {
            this.objGenero = new GeneroDAO();
            this.setTitle("Agregar Género");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtNombreGenero = new TextField();
        txtNombreGenero.setPromptText("Nombre del Género");

        btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnGuardar.setOnAction(actionEvent -> guardarGenero());

        vBox = new VBox(txtNombreGenero, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        escena = new Scene(vBox, 300, 150);
    }

    private void guardarGenero() {
        String nombreGenero = txtNombreGenero.getText().trim();

        // Validación del campo
        if (nombreGenero.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, completa el nombre del género.");
            return;
        }

        objGenero.setNombreGenero(nombreGenero);

        String mensaje;
        Alert.AlertType tipo;

        if (objGenero.getIdGenero() > 0) {
            objGenero.UPDATE();
            mensaje = "Género actualizado con éxito.";
            tipo = Alert.AlertType.INFORMATION;
        } else {
            if (objGenero.INSERT() > 0) {
                mensaje = "Género agregado con éxito.";
                tipo = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Error al agregar el género.";
                tipo = Alert.AlertType.ERROR;
            }
        }

        mostrarAlerta(tipo, "Resultado", mensaje);

        tblGenero.setItems(objGenero.SELECTALL());
        tblGenero.refresh();
        this.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
