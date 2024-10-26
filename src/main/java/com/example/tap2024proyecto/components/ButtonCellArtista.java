package com.example.tap2024proyecto.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import com.example.tap2024proyecto.vistas.FormArtista;
import com.example.tap2024proyecto.models.ArtistaDAO;

import java.util.Optional;

public class ButtonCellArtista extends TableCell<ArtistaDAO, String> {

    Button btnCelda;
    TableView<ArtistaDAO> tableView;

    public ButtonCellArtista(String str, TableView<ArtistaDAO> tableView) {
        this.btnCelda = new Button(str);
        this.tableView = tableView;
        this.btnCelda.setOnAction(actionEvent -> EventoBoton(str));
    }

    private void EventoBoton(String str) {
        ArtistaDAO objArtista = this.getTableView().getItems().get(this.getIndex());

        if (str.equals("Editar")) {
            new FormArtista(tableView, objArtista);
        } else if (str.equals("Eliminar")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Deseas eliminar este artista?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objArtista.DELETE();
                tableView.setItems(objArtista.SELECTALL());
                tableView.refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (!b) {
            this.setGraphic(btnCelda);
        } else {
            this.setGraphic(null);
        }
    }
}