package com.example.tap2024proyecto.components;

import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.vistas.FormCliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCell extends TableCell<ClienteDAO,String> {
    Button btnCelda;

    public ButtonCell(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(event ->EventoBoton(str));
    }

    private void EventoBoton(String str) {
        ClienteDAO objCte = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")) {
            new FormCliente(this.getTableView(), objCte);

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del Sistema:)");
            alert.setContentText("¿Desea eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alert.showAndWait();
            if (opcion.get() == ButtonType.OK) {
                objCte.DELETE();
                this.getTableView().setItems(objCte.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }
    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if( !b ){
            this.setGraphic(btnCelda);
        }
    }
}