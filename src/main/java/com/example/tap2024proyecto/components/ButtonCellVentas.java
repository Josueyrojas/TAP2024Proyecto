package com.example.tap2024proyecto.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.tap2024proyecto.vistas.FormVenta;
import com.example.tap2024proyecto.models.VentasDAO;

import java.util.Optional;

public class ButtonCellVentas extends TableCell<VentasDAO, String> {

    private final Button btnCelda;

    public ButtonCellVentas(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(actionEvent -> handleButtonClick(str));
    }

    private void handleButtonClick(String action) {
        int rowIndex = this.getIndex();

        // Validar índice válido
        if (rowIndex < 0 || rowIndex >= this.getTableView().getItems().size()) {
            return;
        }

        VentasDAO venta = this.getTableView().getItems().get(rowIndex);

        if (action.equals("Editar")) {
            // Abrir formulario de edición
            new FormVenta(this.getTableView(), venta);
        } else if (action.equals("Eliminar")) {
            // Confirmación de eliminación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Deseas eliminar esta venta?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Llamar al método DELETE() de VentasDAO
                int rowsAffected = venta.DELETE();

                if (rowsAffected > 0) {
                    // Si la venta se eliminó correctamente, actualizar la tabla
                    this.getTableView().getItems().remove(venta);
                    this.getTableView().refresh();
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Éxito");
                    successAlert.setContentText("Venta eliminada correctamente.");
                    successAlert.showAndWait();
                } else {
                    // Si no se eliminó correctamente
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setContentText("Hubo un error al eliminar la venta.");
                    errorAlert.showAndWait();
                }
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            this.setGraphic(null);
        } else {
            this.setGraphic(btnCelda);
        }
    }
}
