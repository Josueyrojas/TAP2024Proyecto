package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.LoginDAO;
import com.example.tap2024proyecto.models.ClienteDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Stage {
    private Scene scene;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnLogin;
    private Button btnRegister; // Nuevo botón para registrarse
    private Label lblError;
    private LoginDAO loginDAO;

    public Login() {
        loginDAO = new LoginDAO();
        CrearUI();
        this.setTitle("Spotify - Login");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Spotify3.png").toExternalForm()));
        logo.setFitWidth(150); // Ajusta el ancho de la imagen
        logo.setPreserveRatio(true); // Mantén la proporción

        txtUsername = new TextField();
        txtUsername.setPromptText("Username or Email");
        txtUsername.getStyleClass().add("input-field");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.getStyleClass().add("input-field");

        btnLogin = new Button("Sign In");
        btnLogin.getStyleClass().add("sign-in-btn");
        btnLogin.setOnAction(event -> autenticarUsuario());

        btnRegister = new Button("Register");
        btnRegister.getStyleClass().add("sign-in-btn");
        btnRegister.setOnAction(event -> abrirFormularioRegistro());

        lblError = new Label();
        lblError.getStyleClass().add("label");

        VBox vbox = new VBox(10, logo, txtUsername, txtPassword, btnLogin, btnRegister, lblError);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black; -fx-padding: 20;");
        scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
    }

    private void autenticarUsuario() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Please fill in all fields.");
            return;
        }

        String rol = loginDAO.autenticar(username, password);
        if (rol != null) {
            switch (rol) {
                case "administrador":
                    new VistaAdministrador();
                    break;
                case "cliente":
                    ClienteDAO cliente = ClienteDAO.obtenerClientePorEmailYPassword(username, password);
                    if (cliente != null) {
                        new VistaCliente(cliente);
                    } else {
                        lblError.setText("Error retrieving client data.");
                    }
                    break;
                default:
                    lblError.setText("Unknown role.");
                    return;
            }
            this.close();
        } else {
            lblError.setText("Invalid username or password.");
        }
    }

    private void abrirFormularioRegistro() {
        new FormCliente(null, null);
    }
}