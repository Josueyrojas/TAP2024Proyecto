package com.example.tap2024proyecto.components;

import com.example.tap2024proyecto.models.ArtistaDAO;
import com.example.tap2024proyecto.vistas.FormArtista;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.Optional;

public class ButtonCellArtista extends TableCell<ArtistaDAO, String> {
    private Button btnCelda;
    private TableView<ArtistaDAO> tableView;

    public ButtonCellArtista(String str, TableView<ArtistaDAO> tableView) {
        this.btnCelda = new Button(str);
        this.tableView = tableView;
        this.btnCelda.setOnAction(actionEvent -> EventoBoton(str));
    }

    private void EventoBoton(String str) {
        // Obtener el artista seleccionado en la fila
        ArtistaDAO objArtista = this.getTableView().getItems().get(this.getIndex());

        if (str.equals("Editar")) {
            // Abrir el formulario de edición del artista
            new FormArtista(tableView, objArtista);
        } else if (str.equals("Eliminar")) {
            // Mostrar un cuadro de confirmación para eliminar el artista
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Deseas eliminar este artista?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                objArtista.DELETE();
                tableView.setItems(new ArtistaDAO().SELECTALL()); // Refrescar la lista de artistas
                tableView.refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);
        if (!empty) {
            // Cuando la celda no está vacía, añadir el botón
            this.setGraphic(btnCelda);
        } else {
            // Si la celda está vacía, no mostrar nada
            this.setGraphic(null);
        }
    }

    public static Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>> forTableColumn(TableView<ArtistaDAO> tableView) {
        return param -> new ButtonCellArtista("Editar", tableView);  // Puedes cambiar "Editar" por "Eliminar" según la columna
    }
}
