package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.ArtistaDAO;

public class FormArtista extends Stage {

    private TextField txtNombreArt;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private ArtistaDAO objArtista;

    public FormArtista(TableView<ArtistaDAO> tableView, ArtistaDAO artista) {
        if (artista != null) {
            this.objArtista = artista;
        } else {
            this.objArtista = new ArtistaDAO();
        }
        CrearUI(tableView);
        this.setTitle("Formulario de Artista");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(TableView<ArtistaDAO> tableView) {
        txtNombreArt = new TextField();
        txtNombreArt.setPromptText("Nombre del Artista");

        if (objArtista.getIdArtista() != 0) {
            txtNombreArt.setText(objArtista.getNombreArt());
        }

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> {
            objArtista.setNombreArt(txtNombreArt.getText());
            if (objArtista.getIdArtista() == 0) {
                objArtista.INSERT();
            } else {
                objArtista.UPDATE();
            }
            tableView.setItems(objArtista.SELECTALL());
            tableView.refresh();
            this.close();
        });

        vBox = new VBox(new Label("Nombre del Artista:"), txtNombreArt, btnGuardar);
        escena = new Scene(vBox, 300, 200);
    }
}
