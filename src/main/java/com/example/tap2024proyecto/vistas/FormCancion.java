package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import com.example.tap2024proyecto.models.CancionDAO;
import com.example.tap2024proyecto.models.GeneroDAO;

public class FormCancion extends Stage {

    private TextField txtTituloCancion;
    private TextField txtDuracionCancion;
    private ComboBox<GeneroDAO> cmbGenero;
    private Button btnGuardar;
    private VBox vBox;
    private CancionDAO objCancion;
    private Scene escena;
    private TableView<CancionDAO> tblCancion;

    public FormCancion(TableView<CancionDAO> tbl, CancionDAO cancion) {
        tblCancion = tbl;
        crearUI();

        if (cancion != null) {
            this.objCancion = cancion;
            txtTituloCancion.setText(objCancion.getTituloCan());
            txtDuracionCancion.setText(objCancion.getDuracionCan());
            cmbGenero.setValue(getGeneroById(objCancion.getIdGenero()));
            this.setTitle("Editar Canción");
        } else {
            this.objCancion = new CancionDAO();
            this.setTitle("Agregar Canción");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtTituloCancion = new TextField();
        txtTituloCancion.setPromptText("Ingrese el título de la canción");

        txtDuracionCancion = new TextField();
        txtDuracionCancion.setPromptText("Ingrese la duración de la canción (HH:MM:SS)");

        cmbGenero = new ComboBox<>();
        cargarGeneros();

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> guardarCancion());

        vBox = new VBox(txtTituloCancion, txtDuracionCancion, cmbGenero, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 250);
    }

    private void cargarGeneros() {
        GeneroDAO objGenero = new GeneroDAO();
        cmbGenero.setItems(objGenero.SELECTALL());
        cmbGenero.setPromptText("Seleccione un género");
    }

    private GeneroDAO getGeneroById(int idGenero) {
        for (GeneroDAO genero : cmbGenero.getItems()) {
            if (genero.getIdGenero() == idGenero) {
                return genero;
            }
        }
        return null;
    }

    private void guardarCancion() {
        objCancion.setTituloCan(txtTituloCancion.getText());
        objCancion.setDuracionCan(txtDuracionCancion.getText());
        GeneroDAO generoSeleccionado = cmbGenero.getValue();

        if (generoSeleccionado != null) {
            objCancion.setIdGenero(generoSeleccionado.getIdGenero());

            String msj;
            Alert.AlertType type;

            if (objCancion.getIdCancion() > 0) {
                objCancion.UPDATE();
                msj = "Canción actualizada con éxito.";
                type = Alert.AlertType.INFORMATION;
            } else {
                if (objCancion.INSERT() > 0) {
                    msj = "Canción registrada con éxito.";
                    type = Alert.AlertType.INFORMATION;
                } else {
                    msj = "Error al registrar la canción. Intente nuevamente.";
                    type = Alert.AlertType.ERROR;
                }
            }

            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();

            tblCancion.setItems(objCancion.SELECTALL());
            tblCancion.refresh();
            this.close();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setContentText("Por favor seleccione un género.");
            alerta.showAndWait();
        }
    }
}
