package com.example.tap2024proyecto.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.tap2024proyecto.vistas.FormCancion;
import com.example.tap2024proyecto.models.CancionDAO;

import java.util.Optional;

public class ButtonCellCancion extends TableCell<CancionDAO, String> {

    Button btnCelda;

    public ButtonCellCancion(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(actionEvent -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        CancionDAO objCancion = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")) {
            new FormCancion(this.getTableView(), objCancion);
        } else if (str.equals("Eliminar")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar la canción?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objCancion.DELETE();
                this.getTableView().setItems(objCancion.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            this.setGraphic(btnCelda);
        } else {
            this.setGraphic(null);
        }
    }
}