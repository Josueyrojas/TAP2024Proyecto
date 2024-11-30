package com.example.tap2024proyecto.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.vistas.FormAlbum;

import java.util.Optional;

public class ButtonCellAlbum extends TableCell<AlbumDAO, String> {

    private final Button btnCelda;

    public ButtonCellAlbum(String str, TableView<AlbumDAO> table) {
        btnCelda = new Button(str);
        btnCelda.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand;");
        btnCelda.setOnAction(actionEvent -> handleButtonClick(str, table));
    }

    private void handleButtonClick(String action, TableView<AlbumDAO> table) {
        try {
            AlbumDAO objAlb = getTableView().getItems().get(getIndex());
            if (action.equals("Editar")) {
                new FormAlbum(table, objAlb);
            } else if (action.equals("Eliminar")) {
                confirmAndDelete(objAlb, table);
            }
        } catch (Exception e) {
            System.err.println("Error al procesar acción en ButtonCellAlbum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void confirmAndDelete(AlbumDAO album, TableView<AlbumDAO> table) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Deseas eliminar este álbum?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {
            album.DELETE();
            table.setItems(album.SELECTALL());
            table.refresh();
            System.out.println("Álbum eliminado correctamente.");
        }
    }

    @Override
    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);
        if (!empty) {
            this.setGraphic(btnCelda);
        } else {
            this.setGraphic(null);
        }
    }
}
