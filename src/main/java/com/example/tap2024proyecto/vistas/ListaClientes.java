package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.components.ButtonCell;

public class ListaClientes extends Stage {
    private TableView<ClienteDAO> tblCliente;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaClientes() {
        crearUI();
        this.setTitle("Lista de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        tlbMenu = new ToolBar();
        Button btnAddCliente = new Button("Agregar Cliente");
        btnAddCliente.setOnAction(actionEvent -> new FormCliente(tblCliente, null));
        tlbMenu.getItems().add(btnAddCliente);

        tblCliente = new TableView<>();
        crearTabla();

        vBox = new VBox(tlbMenu, tblCliente);
        escena = new Scene(vBox, 700, 400);
    }

    private void crearTabla() {
        TableColumn<ClienteDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClienteDAO, String> tbcTelefono = new TableColumn<>("Teléfono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClienteDAO, String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClienteDAO, String> tbcPassword = new TableColumn<>("Contraseña");
        tbcPassword.setCellValueFactory(new PropertyValueFactory<>("password")); // Nuevo campo

        TableColumn<ClienteDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCell<>("Editar", tblCliente));

        TableColumn<ClienteDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar", tblCliente));

        tblCliente.getColumns().addAll(tbcNombre, tbcTelefono, tbcEmail, tbcPassword, tbcEditar, tbcEliminar); // Agregar la columna de contraseña
        tblCliente.setItems(new ClienteDAO().SELECTALL());
    }

    /**
     * Método para devolver el contenido de la tabla de clientes.
     *
     * @return el TableView con los clientes.
     */
    public TableView<ClienteDAO> getContenido() {
        return tblCliente;
    }
}
