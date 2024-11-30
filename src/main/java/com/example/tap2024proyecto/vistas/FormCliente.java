package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCliente extends Stage {

    private TextField txtNomCte;
    private TextField txtEmailCte;
    private TextField txtTelCte;
    private PasswordField txtPassword; // Nuevo campo para contraseña
    private Button btnGuardar;
    private VBox vBox;
    private TableView<ClienteDAO> tbvCliente;

    private ClienteDAO objCte;
    private Scene escena;

    public FormCliente(TableView<ClienteDAO> tbv, ClienteDAO objC) {
        this.tbvCliente = tbv;
        CrearUI();
        if (objC != null) {
            this.objCte = objC;
            txtNomCte.setText(objCte.getNomCte());
            txtEmailCte.setText(objCte.getEmailCte());
            txtTelCte.setText(objCte.getTelCte());
            txtPassword.setText(objCte.getPassword()); // Establecer contraseña existente
            this.setTitle("Editar Cliente :)");
        } else {
            this.objCte = new ClienteDAO();
            this.setTitle("Agregar Cliente :)");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomCte = new TextField();
        txtNomCte.setPromptText("Nombre del cliente");

        txtEmailCte = new TextField();
        txtEmailCte.setPromptText("Email del Cliente");

        txtTelCte = new TextField();
        txtTelCte.setPromptText("Teléfono del cliente");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña del cliente"); // Nuevo campo para contraseña

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarCliente());

        vBox = new VBox(txtNomCte, txtEmailCte, txtTelCte, txtPassword, btnGuardar); // Agregar el nuevo campo
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        escena = new Scene(vBox, 300, 200); // Ajustar el tamaño de la ventana
    }

    private void GuardarCliente() {
        objCte.setEmailCte(txtEmailCte.getText());
        objCte.setNomCte(txtNomCte.getText());
        objCte.setTelCte(txtTelCte.getText());
        objCte.setPassword(txtPassword.getText()); // Guardar la contraseña

        String msj;
        Alert.AlertType type;

        if (objCte.getIdCte() > 0) {
            objCte.UPDATE();
            msj = "Registro actualizado";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objCte.INSERT() > 0) {
                msj = "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrió un error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema :)");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tbvCliente.setItems(objCte.SELECTALL());
        tbvCliente.refresh();
        this.close();
    }
}
