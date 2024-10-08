package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCliente extends Stage {

    private TextField txtNomCte;
    private TextField txtEmailCte;
    private TextField txtTelCte;
    private Button btnGuardar;
    private VBox vBox;


    private Scene escena;

    public FormCliente() {
        CrearUI();

        this.setTitle("Agregar Cliente :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomCte = new TextField();
        txtEmailCte = new TextField();
        txtTelCte = new TextField();
        btnGuardar = new Button("Guardar");
        vBox = new VBox(txtNomCte, txtEmailCte, txtTelCte);
    }

}
