package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.LoginDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Stage {
    private Scene scene;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnLogin;
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
        txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.getStyleClass().add("input-field");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.getStyleClass().add("input-field");

        btnLogin = new Button("Sign In");
        btnLogin.getStyleClass().add("sign-in-btn");
        btnLogin.setOnAction(event -> autenticarUsuario());

        lblError = new Label();
        lblError.getStyleClass().add("label");

        VBox vbox = new VBox(10, txtUsername, txtPassword, btnLogin, lblError);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black; -fx-padding: 20;");
        scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
    }

    private void autenticarUsuario() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (loginDAO.autenticar(username, password)) {
            new ListaClientes(); // Si el login es exitoso, mostrar la pantalla de ListaClientes
            this.close(); // Cerrar la ventana de login
        } else {
            lblError.setText("Invalid username or password");
        }
    }
}
