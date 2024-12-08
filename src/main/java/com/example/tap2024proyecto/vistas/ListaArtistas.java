package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.ArtistaDAO;
import com.example.tap2024proyecto.components.ButtonCellArtista;

public class ListaArtistas extends Stage {
    private TableView<ArtistaDAO> tblArtista;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaArtistas() {
        crearUI();
        this.setTitle("Lista de Artistas");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        tlbMenu = new ToolBar();
        Button btnAddArtista = new Button("Agregar Artista");
        btnAddArtista.setOnAction(actionEvent -> new FormArtista(tblArtista, null));
        tlbMenu.getItems().add(btnAddArtista);


        tblArtista = new TableView<>();
        crearTabla();

        vBox = new VBox(tlbMenu, tblArtista);
        escena = new Scene(vBox, 700, 400);
    }

    private void crearTabla() {
        TableColumn<ArtistaDAO, String> tbcNombre = new TableColumn<>("Nombre del Artista");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArt"));

        TableColumn<ArtistaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellArtista("Editar", tblArtista));

        TableColumn<ArtistaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellArtista("Eliminar", tblArtista));

        tblArtista.getColumns().addAll(tbcNombre, tbcEditar, tbcEliminar);

        tblArtista.setItems(new ArtistaDAO().SELECTALL());
    }
    public VBox getContenido() {
        return vBox;
    }
}
