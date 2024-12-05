package com.example.tap2024proyecto.components;

import com.example.tap2024proyecto.models.ClienteDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ButtonCellCliente extends TableCell<ClienteDAO, String> {

    private final Button btnCelda;
    private final TableView<ClienteDAO> tableView;

    public ButtonCellCliente(String action, TableView<ClienteDAO> tableView) {
        this.btnCelda = new Button(action);
        this.tableView = tableView;
        this.btnCelda.setOnAction(event -> performAction(action));
    }

    private void performAction(String action) {
        ClienteDAO cliente = tableView.getItems().get(getIndex());

        if ("Editar".equals(action)) {
            new com.example.tap2024proyecto.vistas.FormCliente(tableView, cliente);
        } else if ("Eliminar".equals(action)) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Deseas eliminar este cliente?");
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                cliente.DELETE();  // Llamada al método DELETE de ClienteDAO
                refreshTable();
            }
        }
    }

    private void refreshTable() {
        tableView.setItems(new ClienteDAO().SELECTALL());  // Refresca la lista de clientes
        tableView.refresh();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCelda);
        } else {
            setGraphic(null);
        }
    }
}
