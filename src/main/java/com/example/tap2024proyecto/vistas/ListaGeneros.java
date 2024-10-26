package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.tap2024proyecto.components.ButtonCellGenero;
import com.example.tap2024proyecto.models.GeneroDAO;

public class ListaGeneros extends Stage {

    private TableView<GeneroDAO> tblGenero;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaGeneros(){
        CrearUI();
        this.setTitle("Lista de géneros");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/derecha.png").toString());
        Button btnAddGen = new Button();
        btnAddGen.setOnAction(actionEvent -> new FormGenero(tblGenero, null));
        btnAddGen.setGraphic(imv);
        tlbMenu.getItems().add(btnAddGen);

        tblGenero = new TableView<>();
        CrearTable();

        vBox = new VBox(tlbMenu, tblGenero);
        escena = new Scene(vBox, 415, 550);
    }

    private void CrearTable() {
        GeneroDAO objGen = new GeneroDAO();
        TableColumn<GeneroDAO,String> tbcNomGen = new TableColumn<>("Genero");
        tbcNomGen.setCellValueFactory(new PropertyValueFactory<>("nombreGen"));

        TableColumn<GeneroDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<GeneroDAO, String>, TableCell<GeneroDAO, String>>() {
            @Override
            public TableCell<GeneroDAO, String> call(TableColumn<GeneroDAO, String> generoDAOStringTableColumn) {
                return new ButtonCellGenero("Editar");
            }
        });

        TableColumn<GeneroDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<GeneroDAO, String>, TableCell<GeneroDAO, String>>() {
            @Override
            public TableCell<GeneroDAO, String> call(TableColumn<GeneroDAO, String> generoDAOStringTableColumn) {
                return new ButtonCellGenero("Eliminar");
            }
        });

        tblGenero.getColumns().addAll(tbcNomGen,tbcEditar,tbcEliminar);
        tblGenero.setItems(objGen.SELECTALL());
    }
}
