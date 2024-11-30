package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.models.CancionDAO;

public class FormAlbum extends Stage {
    private TextField txtTituloAlbum;
    private TextField txtFechaAlbum;
    private TextField txtCostoAlbum;
    private Button btnGuardar;
    private Button btnAgregarCancion;
    private VBox vBox;
    private Scene escena;
    private AlbumDAO objAlbum;
    private TableView<AlbumDAO> tblAlbum;

    public FormAlbum(TableView<AlbumDAO> tableView, AlbumDAO album) {
        tblAlbum = tableView;
        crearUI();

        if (album != null) {
            this.objAlbum = album;
            txtTituloAlbum.setText(objAlbum.getTituloAlbum());
            txtFechaAlbum.setText(objAlbum.getFechaAlbum());
            txtCostoAlbum.setText(String.valueOf(objAlbum.getCostoAlbum()));
            this.setTitle("Editar Álbum");
        } else {
            this.objAlbum = new AlbumDAO();
            this.setTitle("Agregar Álbum");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtTituloAlbum = new TextField();
        txtTituloAlbum.setPromptText("Título del Álbum");

        txtFechaAlbum = new TextField();
        txtFechaAlbum.setPromptText("Fecha del Álbum (YYYY-MM-DD)");

        txtCostoAlbum = new TextField();
        txtCostoAlbum.setPromptText("Costo del Álbum");

        btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnGuardar.setOnAction(actionEvent -> guardarAlbum());

        btnAgregarCancion = new Button("Agregar Canción");
        btnAgregarCancion.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnAgregarCancion.setOnAction(actionEvent -> agregarCancion());

        vBox = new VBox(txtTituloAlbum, txtFechaAlbum, txtCostoAlbum, btnGuardar, btnAgregarCancion);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        escena = new Scene(vBox, 400, 300);
    }

    private void guardarAlbum() {
        objAlbum.setTituloAlbum(txtTituloAlbum.getText());
        objAlbum.setFechaAlbum(txtFechaAlbum.getText());
        objAlbum.setCostoAlbum(Double.parseDouble(txtCostoAlbum.getText()));

        String msj;
        Alert.AlertType type;

        if (objAlbum.getIdAlbum() > 0) {
            objAlbum.UPDATE();
            msj = "Registro actualizado con éxito";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objAlbum.INSERT() > 0) {
                msj = "Registro insertado con éxito";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Registro NO insertado, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tblAlbum.setItems(objAlbum.SELECTALL());
        tblAlbum.refresh();
        this.close();
    }

    private void agregarCancion() {
        TableView<CancionDAO> tblCancion = new TableView<>(); // Tabla temporal para canciones.
        CancionDAO nuevaCancion = new CancionDAO(); // Nueva instancia de CancionDAO.
        nuevaCancion.setIdAlbum(objAlbum.getIdAlbum()); // Asocia la canción con el álbum actual.
        new FormCancion(tblCancion, nuevaCancion); // Llama al constructor de FormCancion.
    }
}
