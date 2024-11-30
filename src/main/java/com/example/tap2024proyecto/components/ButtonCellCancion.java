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

        // Acción para Editar
        if (str.equals("Editar")) {
            new FormCancion(this.getTableView(), objCancion); // Pasa la canción seleccionada para editar
        }
        // Acción para Eliminar
        else if (str.equals("Eliminar")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("¿Deseas eliminar la canción?");
            alert.setContentText("La canción '" + objCancion.getTituloCan() + "' será eliminada.");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                objCancion.DELETE(); // Llama al método DELETE() para eliminar la canción
                // Recarga las canciones después de eliminar la actual
                this.getTableView().setItems(new CancionDAO().SELECTALL());
                this.getTableView().refresh(); // Refresca la tabla para que la eliminación sea visible
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            this.setGraphic(btnCelda); // Establece el botón en la celda
        } else {
            this.setGraphic(null); // Si la celda está vacía, no muestra el botón
        }
    }
}
