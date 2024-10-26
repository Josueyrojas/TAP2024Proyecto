package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.GeneroDAO;

public class FormGenero extends Stage {

    private TextField txtNombreGen;
    private Button btnGuardar;
    private VBox vBox;
    private GeneroDAO objGenero;
    private Scene escena;
    private TableView<GeneroDAO> tvbGenero;

    public FormGenero(TableView<GeneroDAO> tvb, GeneroDAO objG) {
        tvbGenero = tvb;
        CrearUI();

        if (objG != null){
            this.objGenero = objG;
            txtNombreGen.setText(objGenero.getNombreGen());
            this.setTitle("Editar Genero");
        } else {
            this.objGenero = new GeneroDAO();
            this.setTitle("Agregar Genero");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombreGen = new TextField();
        txtNombreGen.setPromptText("Ingrese el nombre del genero");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarGenero());
        vBox = new VBox(txtNombreGen, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 150, 150);
    }

    private void GuardarGenero() {
        objGenero.setNombreGen(txtNombreGen.getText());
        String msj;
        Alert.AlertType type;

        if (objGenero.getIdGenero() > 0){
            objGenero.UPDATE();
            msj = "Registro Actualizado con éxito";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objGenero.INSERT() > 0){
                msj = "Registro insertado con éxito";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Registro NO Insertado, intente de nuevo";
                type = Alert.AlertType.ERROR;

            }
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Mensaje del Sistema :)");
            alerta.setContentText(msj);
            alerta.showAndWait();
        }

        tvbGenero.setItems(objGenero.SELECTALL());
        tvbGenero.refresh();
    }
}