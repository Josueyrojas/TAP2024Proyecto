package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.tap2024proyecto.components.ButtonCellVentas;
import com.example.tap2024proyecto.models.VentasDAO;

public class ListaVenta extends Stage {

    private TableView<VentasDAO> tblVenta;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaVenta() {
        CrearUI();
        this.setTitle("Lista de Ventas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/derecha.png").toString());
        Button btnAddVenta = new Button();
        btnAddVenta.setOnAction(actionEvent -> new FormVenta(tblVenta, null));
        btnAddVenta.setGraphic(imv);
        tlbMenu.getItems().add(btnAddVenta);

        tblVenta = new TableView<>();
        CrearTable();

        vBox = new VBox(tlbMenu, tblVenta);
        escena = new Scene(vBox, 415, 550);
    }

    private void CrearTable() {
        VentasDAO objVenta = new VentasDAO();

        TableColumn<VentasDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));

        // Mostrar el nombre del cliente en lugar del ID
        TableColumn<VentasDAO, String> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente")); // Mostrar el nombre del cliente

        TableColumn<VentasDAO, String> tbvEditar = new TableColumn<>("");
        tbvEditar.setCellFactory(new Callback<TableColumn<VentasDAO, String>, TableCell<VentasDAO, String>>() {
            @Override
            public TableCell<VentasDAO, String> call(TableColumn<VentasDAO, String> ventasDAOStringTableColumn) {
                return new ButtonCellVentas("Editar");
            }
        });

        TableColumn<VentasDAO, String> tbvEliminar = new TableColumn<>("");
        tbvEliminar.setCellFactory(new Callback<TableColumn<VentasDAO, String>, TableCell<VentasDAO, String>>() {
            @Override
            public TableCell<VentasDAO, String> call(TableColumn<VentasDAO, String> ventasDAOStringTableColumn) {
                return new ButtonCellVentas("Eliminar");
            }
        });

        tblVenta.getColumns().addAll(tbcFecha, tbcCliente, tbvEditar, tbvEliminar);
        tblVenta.setItems(objVenta.SELECTALL());
    }
}
