package com.example.tap2024proyecto.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.tap2024proyecto.vistas.FormGenero;
import com.example.tap2024proyecto.models.GeneroDAO;

import java.util.Optional;

public class ButtonCellGenero extends TableCell<GeneroDAO, String> {

    Button btnCelda;

    public ButtonCellGenero(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(actionEvent -> EventoBoton(str));
    }

    private void EventoBoton(String str) {
        GeneroDAO objGen = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormGenero(this.getTableView(), objGen);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("Deseas eliminar el registro?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                objGen.DELETE();
                this.getTableView().setItems(objGen.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (!b){
            this.setGraphic(btnCelda);
        }
    }
}