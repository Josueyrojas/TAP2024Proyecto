package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
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
        // Columnas de la tabla
        TableColumn<AlbumDAO, String> tbcNomAlb = new TableColumn<>("Nombre del Álbum");
        tbcNomAlb.setCellValueFactory(new PropertyValueFactory<>("tituloAlbum"));

        TableColumn<AlbumDAO, String> tbcFechaAlb = new TableColumn<>("Fecha del Álbum");
        tbcFechaAlb.setCellValueFactory(new PropertyValueFactory<>("fechaAlbum"));

        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellAlbum("Editar", tblAlbum));

        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellAlbum("Eliminar", tblAlbum));

        // Agregar columnas a la tabla
        tblAlbum.getColumns().addAll(tbcNomAlb, tbcFechaAlb, tbcEditar, tbcEliminar);

        // Vincular datos a la tabla
        tblAlbum.setItems(objAlbum.SELECTALL());
    }
    public TableView<AlbumDAO> getContenido() {
        return tblAlbum;
    }

}
