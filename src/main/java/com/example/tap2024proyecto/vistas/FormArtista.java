package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.ArtistaDAO;

public class FormArtista extends Stage {
    private TextField txtNombreArtista;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private ArtistaDAO objArtista;
    private TableView<ArtistaDAO> tblArtista;

    public FormArtista(TableView<ArtistaDAO> tableView, ArtistaDAO artista) {
        tblArtista = tableView;
        crearUI();

        // Si es un artista existente, cargamos sus datos
        if (artista != null) {
            this.objArtista = artista;
            txtNombreArtista.setText(objArtista.getNombreArt());
            this.setTitle("Editar Artista");
        } else {
            this.objArtista = new ArtistaDAO();
            this.setTitle("Agregar Artista");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        // Campo de texto para el nombre del artista
        txtNombreArtista = new TextField();
        txtNombreArtista.setPromptText("Nombre del Artista");

        // Botón para guardar el artista
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> guardarArtista());

        // Layout vertical con padding y espaciado
        vBox = new VBox(txtNombreArtista, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        // Escena con el VBox
        escena = new Scene(vBox, 300, 150);
    }

    private void guardarArtista() {
        // Verificamos que el campo del nombre no esté vacío
        String nombre = txtNombreArtista.getText().trim();
        if (nombre.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("El nombre del artista no puede estar vacío.");
            alerta.showAndWait();
            return;
        }

        objArtista.setNombreArt(nombre);

        String mensaje;
        Alert.AlertType tipo;

        // Si el artista ya tiene un ID, es porque estamos editando
        if (objArtista.getIdArtista() > 0) {
            objArtista.UPDATE();  // Actualiza el artista
            mensaje = "Artista actualizado con éxito.";
            tipo = Alert.AlertType.INFORMATION;
        } else {
            // Si no tiene ID, estamos agregando un nuevo artista
            if (objArtista.INSERT() > 0) {
                mensaje = "Artista agregado con éxito.";
                tipo = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Error al agregar el artista.";
                tipo = Alert.AlertType.ERROR;
            }
        }

        // Mostrar el mensaje de éxito o error
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Resultado");
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        // Actualizamos la lista de artistas en la tabla
        tblArtista.setItems(objArtista.SELECTALL());
        tblArtista.refresh();

        // Cerramos el formulario después de guardar
        this.close();
    }
}
