package com.example.tap2024proyecto.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.tap2024proyecto.vistas.FormVenta;
import com.example.tap2024proyecto.models.VentasDAO;

import java.util.Optional;

public class ButtonCellVentas extends TableCell<VentasDAO, String> {

    Button btnCelda;

    public ButtonCellVentas(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(actionEvent -> EventoBoton(str));
    }

    private void EventoBoton(String str) {
        VentasDAO objVenta = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")) {
            new FormVenta(this.getTableView(), objVenta);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar la venta?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objVenta.DELETE();
                this.getTableView().setItems(objVenta.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (!b) {
            this.setGraphic(btnCelda);
        }
    }
}