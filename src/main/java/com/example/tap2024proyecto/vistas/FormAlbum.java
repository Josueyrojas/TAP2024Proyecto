package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.models.CancionDAO;

import java.io.File;

public class FormAlbum extends Stage {
    private TextField txtTituloAlbum;
    private TextField txtFechaAlbum;
    private TextField txtCostoAlbum;
    private Button btnGuardar;
    private Button btnAgregarCancion;
    private Button btnCargarImagen;
    private ImageView imgViewAlbum;
    private VBox vBox;
    private Scene escena;
    private AlbumDAO objAlbum;
    private TableView<AlbumDAO> tblAlbum;

    public FormAlbum(TableView<AlbumDAO> tableView, AlbumDAO album) {
        tblAlbum = tableView;
        crearUI();

        if (album != null) {
            this.objAlbum = album;
            // Cargar los datos del álbum en los campos
            txtTituloAlbum.setText(objAlbum.getTituloAlbum());
            txtFechaAlbum.setText(objAlbum.getFechaAlbum());
            txtCostoAlbum.setText(String.valueOf(objAlbum.getCostoAlbum()));
            if (objAlbum.getImagenAlbum() != null && !objAlbum.getImagenAlbum().isEmpty()) {
                imgViewAlbum.setImage(new Image("file:" + objAlbum.getImagenAlbum())); // Cargar la imagen
            }
            this.setTitle("Editar Álbum");
        } else {
            this.objAlbum = new AlbumDAO();
            this.setTitle("Agregar Álbum");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        // Campos de texto
        txtTituloAlbum = new TextField();
        txtTituloAlbum.setPromptText("Título del Álbum");

        txtFechaAlbum = new TextField();
        txtFechaAlbum.setPromptText("Fecha del Álbum (YYYY-MM-DD)");

        txtCostoAlbum = new TextField();
        txtCostoAlbum.setPromptText("Costo del Álbum");

        // Botón para cargar la imagen
        btnCargarImagen = new Button("Cargar Imagen");
        btnCargarImagen.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnCargarImagen.setOnAction(actionEvent -> cargarImagen());

        // Vista previa de la imagen cargada
        imgViewAlbum = new ImageView();
        imgViewAlbum.setFitHeight(150); // Ajustar el tamaño de la imagen
        imgViewAlbum.setFitWidth(150);

        // Botones de acción
        btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnGuardar.setOnAction(actionEvent -> guardarAlbum());

        btnAgregarCancion = new Button("Agregar Canción");
        btnAgregarCancion.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnAgregarCancion.setOnAction(actionEvent -> agregarCancion());

        // Contenedor para los controles
        vBox = new VBox(txtTituloAlbum, txtFechaAlbum, txtCostoAlbum, btnCargarImagen, imgViewAlbum, btnGuardar, btnAgregarCancion);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        escena = new Scene(vBox, 400, 400);
    }

    private void cargarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivo = fileChooser.showOpenDialog(this);

        if (archivo != null) {
            // Guardar la ruta de la imagen en el objeto AlbumDAO
            objAlbum.setImagenAlbum(archivo.getAbsolutePath());

            // Mostrar la imagen en la interfaz
            imgViewAlbum.setImage(new Image("file:" + archivo.getAbsolutePath()));
        }
    }

    private void guardarAlbum() {
        // Obtener los valores de los campos de texto
        objAlbum.setTituloAlbum(txtTituloAlbum.getText());
        objAlbum.setFechaAlbum(txtFechaAlbum.getText());

        // Validar y guardar el costo
        try {
            objAlbum.setCostoAlbum(Double.parseDouble(txtCostoAlbum.getText()));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El costo debe ser un número válido.");
            alert.showAndWait();
            return;
        }

        String msj;
        Alert.AlertType type;

        // Verificar si el álbum ya existe (tiene id > 0) o si es uno nuevo
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

        // Mostrar el mensaje correspondiente
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        // Actualizar la tabla de álbumes
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
