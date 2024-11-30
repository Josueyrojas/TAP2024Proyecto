package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.GeneroDAO;
import com.example.tap2024proyecto.components.ButtonCellGenero;

public class ListaGeneros extends Stage {
    private TableView<GeneroDAO> tblGenero;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaGeneros() {
        crearUI();
        this.setTitle("Lista de Géneros");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        tlbMenu = new ToolBar();
        Button btnAddGenero = new Button("Agregar Género");
        btnAddGenero.setOnAction(actionEvent -> new FormGenero(tblGenero, null));
        tlbMenu.getItems().add(btnAddGenero);

        tblGenero = new TableView<>();
        crearTabla();

        vBox = new VBox(tlbMenu, tblGenero);
        escena = new Scene(vBox, 700, 400);
    }

    private void crearTabla() {
        TableColumn<GeneroDAO, String> tbcNombre = new TableColumn<>("Nombre del Género");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        TableColumn<GeneroDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellGenero("Editar"));

        TableColumn<GeneroDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellGenero("Eliminar"));

        tblGenero.getColumns().addAll(tbcNombre, tbcEditar, tbcEliminar);
        tblGenero.setItems(new GeneroDAO().SELECTALL());
    }
}
