package com.example.tap2024proyecto.vistas;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.CancionDAO;
import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.models.GeneroDAO;
import com.example.tap2024proyecto.models.ArtistaDAO; // Importamos el modelo ArtistaDAO

public class FormCancion extends Stage {
    private TextField txtTituloCancion;
    private TextField txtCostoCancion;
    private ComboBox<AlbumDAO> cmbAlbum;
    private ComboBox<GeneroDAO> cmbGenero;
    private ComboBox<ArtistaDAO> cmbArtista; // ComboBox para seleccionar artista
    private CheckBox chkEsSencillo;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private CancionDAO objCancion;
    private TableView<CancionDAO> tblCancion;

    public FormCancion(TableView<CancionDAO> tableView, CancionDAO cancion) {
        tblCancion = tableView;
        crearUI();

        if (cancion != null) {
            this.objCancion = cancion;
            txtTituloCancion.setText(objCancion.getTituloCan());
            txtCostoCancion.setText(String.valueOf(objCancion.getCostoCancion()));
            cmbAlbum.getSelectionModel().select(findAlbum(objCancion.getIdAlbum()));
            cmbGenero.getSelectionModel().select(findGenero(objCancion.getIdGenero()));
            cmbArtista.getSelectionModel().select(findArtista(objCancion.getIdArtista())); // Seleccionamos el artista
            chkEsSencillo.setSelected(objCancion.getIdAlbum() == 0); // Si no tiene álbum, es un sencillo
            this.setTitle("Editar Canción");
        } else {
            this.objCancion = new CancionDAO();
            this.setTitle("Agregar Canción");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtTituloCancion = new TextField();
        txtTituloCancion.setPromptText("Título de la Canción");

        txtCostoCancion = new TextField();
        txtCostoCancion.setPromptText("Costo de la Canción");

        cmbAlbum = new ComboBox<>();
        cmbAlbum.setPromptText("Selecciona un Álbum");
        cmbAlbum.setItems(loadAlbums());

        cmbGenero = new ComboBox<>();
        cmbGenero.setPromptText("Selecciona un Género");
        cmbGenero.setItems(loadGeneros());

        cmbArtista = new ComboBox<>();
        cmbArtista.setPromptText("Selecciona un Artista");
        cmbArtista.setItems(loadArtistas()); // Cargamos los artistas disponibles

        chkEsSencillo = new CheckBox("Es un Sencillo");
        chkEsSencillo.setOnAction(e -> manejarSencillo());

        btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-cursor: hand;");
        btnGuardar.setOnAction(actionEvent -> guardarCancion());

        vBox = new VBox(10, txtTituloCancion, txtCostoCancion, cmbAlbum, cmbGenero, cmbArtista, chkEsSencillo, btnGuardar);
        vBox.setPadding(new Insets(10));

        escena = new Scene(vBox, 400, 400); // Ajustamos el tamaño para acomodar el nuevo campo
    }

    private ObservableList<AlbumDAO> loadAlbums() {
        return new AlbumDAO().SELECTALL(); // Método para cargar todos los álbumes desde la base de datos
    }

    private ObservableList<GeneroDAO> loadGeneros() {
        return new GeneroDAO().SELECTALL(); // Método para cargar todos los géneros desde la base de datos
    }

    private ObservableList<ArtistaDAO> loadArtistas() {
        return new ArtistaDAO().SELECTALL(); // Método para cargar todos los artistas desde la base de datos
    }

    private AlbumDAO findAlbum(int idAlbum) {
        for (AlbumDAO album : loadAlbums()) {
            if (album.getIdAlbum() == idAlbum) {
                return album;
            }
        }
        return null;
    }

    private GeneroDAO findGenero(int idGenero) {
        for (GeneroDAO genero : loadGeneros()) {
            if (genero.getIdGenero() == idGenero) {
                return genero;
            }
        }
        return null;
    }

    private ArtistaDAO findArtista(int idArtista) {
        for (ArtistaDAO artista : loadArtistas()) {
            if (artista.getIdArtista() == idArtista) {
                return artista;
            }
        }
        return null;
    }

    private void manejarSencillo() {
        if (chkEsSencillo.isSelected()) {
            cmbAlbum.setDisable(true);
            cmbAlbum.getSelectionModel().clearSelection(); // Deseleccionar cualquier álbum
        } else {
            cmbAlbum.setDisable(false);
        }
    }

    private void guardarCancion() {
        // Verificar que se haya ingresado el título
        if (txtTituloCancion.getText().isEmpty()) {
            mostrarAlerta("Error", "El título de la canción no puede estar vacío.");
            return;
        }

        // Verificar que se haya ingresado el costo
        if (txtCostoCancion.getText().isEmpty()) {
            mostrarAlerta("Error", "El costo de la canción no puede estar vacío.");
            return;
        }

        // Verificar que se haya seleccionado un artista
        if (cmbArtista.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta("Error", "Debe seleccionar un artista.");
            return;
        }

        // Manejar sencillo
        int idAlbum = chkEsSencillo.isSelected() ? 0 : cmbAlbum.getSelectionModel().getSelectedItem().getIdAlbum();
        int idArtista = cmbArtista.getSelectionModel().getSelectedItem().getIdArtista();

        objCancion.setTituloCan(txtTituloCancion.getText());
        objCancion.setCostoCancion(Double.parseDouble(txtCostoCancion.getText()));
        objCancion.setIdAlbum(idAlbum);
        objCancion.setIdGenero(cmbGenero.getSelectionModel().getSelectedItem().getIdGenero());
        objCancion.setIdArtista(idArtista);
        objCancion.setIdArtista(cmbArtista.getSelectionModel().getSelectedItem().getIdArtista());// Establecer el artista

        String mensaje;
        Alert.AlertType tipo;

        // Insertar o actualizar la canción
        if (objCancion.getIdCancion() > 0) {
            objCancion.UPDATE(); // Actualiza la canción si ya existe
            mensaje = "Canción actualizada con éxito.";
            tipo = Alert.AlertType.INFORMATION;
        } else {
            if (objCancion.INSERT() > 0) { // Inserta la canción si es nueva
                mensaje = "Canción agregada con éxito.";
                tipo = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Error al agregar la canción.";
                tipo = Alert.AlertType.ERROR;
            }
        }

        mostrarAlerta("Resultado", mensaje);

        // Actualizar tabla y cerrar formulario
        tblCancion.setItems(objCancion.SELECTALL());
        tblCancion.refresh();
        this.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
