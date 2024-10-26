package com.example.tap2024proyecto.vistas;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.tap2024proyecto.components.ButtonCellArtista;
import com.example.tap2024proyecto.models.ArtistaDAO;

public class ListaArtistas extends Stage {

    private TableView<ArtistaDAO> tblArtista;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaArtistas() {
        CrearUI();
        this.setTitle("Lista de Artistas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/derecha.png").toString());
        Button btnAddArtista = new Button();
        btnAddArtista.setGraphic(imv);
        btnAddArtista.setOnAction(actionEvent -> new FormArtista(tblArtista, null));
        tlbMenu.getItems().add(btnAddArtista);

        tblArtista = new TableView<>();
        CrearTabla();

        vBox = new VBox(tlbMenu, tblArtista);
        escena = new Scene(vBox, 400, 400);
    }

    private void CrearTabla() {
        ArtistaDAO objArtista = new ArtistaDAO();

        TableColumn<ArtistaDAO, String> tbcNombreArt = new TableColumn<>("Nombre del Artista");
        tbcNombreArt.setCellValueFactory(new PropertyValueFactory<>("nombreArt"));

        TableColumn<ArtistaDAO, String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>>() {
            @Override
            public TableCell<ArtistaDAO, String> call(TableColumn<ArtistaDAO, String> param) {
                return new ButtonCellArtista("Editar", tblArtista);
            }
        });

        TableColumn<ArtistaDAO, String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>>() {
            @Override
            public TableCell<ArtistaDAO, String> call(TableColumn<ArtistaDAO, String> param) {
                return new ButtonCellArtista("Eliminar", tblArtista);
            }
        });

        tblArtista.getColumns().addAll(tbcNombreArt, tbcEditar, tbcEliminar);
        tblArtista.setItems(objArtista.SELECTALL());
    }
}