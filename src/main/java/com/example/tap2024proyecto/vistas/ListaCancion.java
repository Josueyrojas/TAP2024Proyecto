package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.tap2024proyecto.components.ButtonCellCancion;
import com.example.tap2024proyecto.models.CancionDAO;


public class ListaCancion extends Stage {

    private TableView<CancionDAO> tblCancion;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaCancion() {
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/derecha.png").toString());
        Button btnAddCancion = new Button();
        btnAddCancion.setOnAction(actionEvent -> new FormCancion(tblCancion, null));
        btnAddCancion.setGraphic(imv);
        tlbMenu.getItems().add(btnAddCancion);

        tblCancion = new TableView<>();
        CrearTable();

        vBox = new VBox(tlbMenu, tblCancion);
        escena = new Scene(vBox, 600, 400);
    }

    private void CrearTable() {
        CancionDAO objCancion = new CancionDAO();

        TableColumn<CancionDAO, String> tbcTitulo = new TableColumn<>("Título");
        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloCan"));

        TableColumn<CancionDAO, String> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionCan"));

        TableColumn<CancionDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGen"));

        TableColumn<CancionDAO, String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<CancionDAO, String>, TableCell<CancionDAO, String>>() {
            @Override
            public TableCell<CancionDAO, String> call(TableColumn<CancionDAO, String> cancionDAOStringTableColumn) {
                return new ButtonCellCancion("Editar");
            }
        });

        TableColumn<CancionDAO, String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<CancionDAO, String>, TableCell<CancionDAO, String>>() {
            @Override
            public TableCell<CancionDAO, String> call(TableColumn<CancionDAO, String> cancionDAOStringTableColumn) {
                return new ButtonCellCancion("Eliminar");
            }
        });

        tblCancion.getColumns().addAll(tbcTitulo, tbcDuracion, tbcGenero, tbcEditar, tbcEliminar);
        tblCancion.setItems(objCancion.SELECTALL());
    }
}
