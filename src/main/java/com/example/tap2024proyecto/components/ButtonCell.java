package com.example.tap2024proyecto.components;

import com.example.tap2024proyecto.models.ArtistaDAO;
import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.models.VentasDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

import java.util.Optional;

public class ButtonCell<T> extends TableCell<T, String> {

    private final Button btnCelda;
    private final TableView<T> tableView;

    public ButtonCell(String action, TableView<T> tableView) {
        this.btnCelda = new Button(action);
        this.tableView = tableView;
        this.btnCelda.setOnAction(event -> performAction(action));
    }

    private void performAction(String action) {
        T obj = tableView.getItems().get(getIndex());

        if ("Editar".equals(action)) {
            if (obj instanceof ArtistaDAO) {
                new com.example.tap2024proyecto.vistas.FormArtista((TableView<ArtistaDAO>) tableView, (ArtistaDAO) obj);
            } else if (obj instanceof VentasDAO) {
                new com.example.tap2024proyecto.vistas.FormVenta((TableView<VentasDAO>) tableView, (VentasDAO) obj);
            } else if (obj instanceof ClienteDAO) { // Manejar edición para ClienteDAO
                new com.example.tap2024proyecto.vistas.FormCliente((TableView<ClienteDAO>) tableView, (ClienteDAO) obj);
            }
        } else if ("Eliminar".equals(action)) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Deseas eliminar este registro?");
            Optional<javafx.scene.control.ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
                if (obj instanceof ArtistaDAO) {
                    ((ArtistaDAO) obj).DELETE();
                } else if (obj instanceof VentasDAO) {
                    ((VentasDAO) obj).DELETE();
                } else if (obj instanceof ClienteDAO) { // Manejar eliminación para ClienteDAO
                    ((ClienteDAO) obj).DELETE();
                }
                refreshTable(obj);
            }
        }
    }

    /**
     * Refrescar la tabla con los datos actualizados después de una acción.
     */
    private void refreshTable(T obj) {
        tableView.getItems().clear();
        if (obj instanceof ArtistaDAO) {
            tableView.getItems().addAll((T) ((ArtistaDAO) obj).SELECTALL());
        } else if (obj instanceof VentasDAO) {
            tableView.getItems().addAll((T) ((VentasDAO) obj).SELECTALL());
        } else if (obj instanceof ClienteDAO) { // Refrescar para ClienteDAO
            tableView.getItems().addAll((T) ((ClienteDAO) obj).SELECTALL());
        }
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
