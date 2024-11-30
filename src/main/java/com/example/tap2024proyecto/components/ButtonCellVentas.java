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
            new FormVenta(this.getTableView(), venta);
        } else if (action.equals("Eliminar")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Deseas eliminar esta venta?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                venta.DELETE();

                // Actualizar tabla
                this.getTableView().setItems(venta.SELECTALL());
                this.getTableView().refresh();
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
