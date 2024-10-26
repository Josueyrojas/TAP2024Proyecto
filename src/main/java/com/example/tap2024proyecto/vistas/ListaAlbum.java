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

public class ListaAlbum extends Stage{
    private TableView<AlbumDAO> tblAlbum;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaAlbum() {
        CrearUI();
        this.setTitle("Lista de Albums");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/derecha.png").toString());
        Button btnAddArtista = new Button();
        btnAddArtista.setGraphic(imv);
        btnAddArtista.setOnAction(actionEvent -> new FormAlbum(tblAlbum, null));
        tlbMenu.getItems().add(btnAddArtista);

        tblAlbum = new TableView<>();
        CrearTabla();

        vBox = new VBox(tlbMenu, tblAlbum);
        escena = new Scene(vBox, 400, 400);
    }

    private void CrearTabla() {
        AlbumDAO objAlbum = new AlbumDAO();

        TableColumn<AlbumDAO, String> tbcNomAlb = new TableColumn<>("Nombre del Album");
        tbcNomAlb.setCellValueFactory(new PropertyValueFactory<>("tituloAlbum"));

        TableColumn<AlbumDAO, String> tbcFechaAlb = new TableColumn<>("Fecha del Album");
        tbcFechaAlb.setCellValueFactory(new PropertyValueFactory<>("fechaAlbum"));

        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAlbum("Editar");
            }
        });

        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAlbum("Eliminar");
            }
        });

        tblAlbum.getColumns().addAll(tbcNomAlb, tbcFechaAlb, tbcEditar, tbcEliminar);
        tblAlbum.setItems(objAlbum.SELECTALL());
    }
}