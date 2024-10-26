package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.components.ButtonCell;
import com.example.tap2024proyecto.models.ClienteDAO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;


public class ListaClientes extends Stage {

    private TableView<ClienteDAO> tbvClientes;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    public ListaClientes(){
        CrearUI();
        this.setTitle("Lista de clientes :)");
        this.setScene(escena);
        this.show();
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        tlbMenu.getStyleClass().add("toolbar");

        ImageView imv = new ImageView(getClass().getResource("/images/Agregar.png").toString());
        imv.setFitWidth(50);
        imv.setFitHeight(50);

        Button btnAddCte = new Button();
        btnAddCte.setOnAction(actionEvent -> new FormCliente(tbvClientes, null));
        btnAddCte.setGraphic(imv);
        btnAddCte.getStyleClass().add("button");

        tlbMenu.getItems().add(btnAddCte);

        tbvClientes = new TableView<>();
        tbvClientes.getStyleClass().add("table-view");
        CrearTable();

        vBox = new VBox(tlbMenu, tbvClientes);
        escena = new Scene(vBox, 800, 600);
    }



    private void CrearTable() {
        ClienteDAO objCte = new ClienteDAO();
        TableColumn<ClienteDAO,String> tbcNomCte = new TableColumn<>("Cliente");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClienteDAO,String> tbcEmailCte = new TableColumn<>("Email");
        tbcEmailCte.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClienteDAO,String> tbcTelCte = new TableColumn<>("Telefono");
        tbcTelCte.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClienteDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new ButtonCell("Editar");
            }
        });

        TableColumn<ClienteDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNomCte,tbcEmailCte,tbcTelCte,tbcEditar,tbcEliminar);
        tbvClientes.setItems(objCte.SELECTALL());

    }
}