package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.components.ButtonCellAlbum;
import com.example.tap2024proyecto.models.AlbumDAO;

public class ListaAlbum extends Stage {
    private TableView<AlbumDAO> tblAlbum;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    private AlbumDAO objAlbum;

    public ListaAlbum() {
        objAlbum = new AlbumDAO();
        crearUI();
        this.setTitle("Lista de Álbumes");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        // Barra de herramientas
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Album.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);

        Button btnAddAlbum = new Button("Agregar Álbum");
        btnAddAlbum.setGraphic(imv);
        btnAddAlbum.setStyle("-fx-background-color: #1db954; -fx-text-fill: white;");
        btnAddAlbum.setOnAction(actionEvent -> new FormAlbum(tblAlbum, null));
        tlbMenu.getItems().add(btnAddAlbum);

        // Tabla
        tblAlbum = new TableView<>();
        crearTabla();

        vBox = new VBox(tlbMenu, tblAlbum);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");

        escena = new Scene(vBox, 600, 400);
    }

    private void crearTabla() {
        // Columna de la imagen
        TableColumn<AlbumDAO, String> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellValueFactory(param -> param.getValue().getImagenAlbum() != null ?
                new javafx.beans.property.SimpleStringProperty(param.getValue().getImagenAlbum()) :
                new javafx.beans.property.SimpleStringProperty(""));

        // Personalizar cómo se muestra la imagen en la celda
        tbcImagen.setCellFactory(param -> new TableCell<AlbumDAO, String>() {
            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setGraphic(null);
                } else {
                    // Se añade el manejo de la imagen
                    ImageView imageView = new ImageView(new Image("file:" + imagePath));
                    imageView.setFitHeight(50);  // Ajusta el tamaño de la imagen
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });

        // Columna del nombre del álbum
        TableColumn<AlbumDAO, String> tbcnomAlbum = new TableColumn<>("Nombre del Álbum");
        tbcnomAlbum.setCellValueFactory(new PropertyValueFactory<>("tituloAlbum"));

        // Columna de la fecha del álbum
        TableColumn<AlbumDAO, String> tbcFechaAlb = new TableColumn<>("Fecha del Álbum");
        tbcFechaAlb.setCellValueFactory(new PropertyValueFactory<>("fechaAlbum"));

        // Columna de editar
        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellAlbum("Editar", tblAlbum));

        // Columna de eliminar
        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellAlbum("Eliminar", tblAlbum));

        // Agregar todas las columnas a la tabla
        tblAlbum.getColumns().addAll(tbcImagen, tbcnomAlbum, tbcFechaAlb, tbcEditar, tbcEliminar);

        // Vincular los datos a la tabla
        tblAlbum.setItems(objAlbum.SELECTALL());
    }

    public TableView<AlbumDAO> getContenido() {
        return tblAlbum;
    }
}
