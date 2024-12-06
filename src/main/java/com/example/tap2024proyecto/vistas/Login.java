package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.models.LoginDAO;
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
        // Imagen del logo de Spotify
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Spotify3.png").toExternalForm()));
        logo.setFitWidth(150); // Ajusta el ancho de la imagen
        logo.setPreserveRatio(true); // Mantén la proporción

        // Campos de texto para usuario y contraseña
        txtUsername = new TextField();
        txtUsername.setPromptText("Username or Email");
        txtUsername.getStyleClass().add("input-field");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.getStyleClass().add("input-field");

        // Botón de inicio de sesión
        btnLogin = new Button("Sign In");
        btnLogin.getStyleClass().add("sign-in-btn");
        btnLogin.setOnAction(event -> autenticarUsuario());

        // Label para mensajes de error
        lblError = new Label();
        lblError.getStyleClass().add("label");

        // Diseño del VBox
        VBox vbox = new VBox(10, logo, txtUsername, txtPassword, btnLogin, lblError);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black; -fx-padding: 20;");
        scene = new Scene(vbox, 400, 400); // Ajusta el tamaño de la ventana
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
    }
    /*
        private void autenticarUsuario() {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblError.setText("Please fill in all fields.");
                return;
            }

            // Verificar rol del usuario
            String rol = loginDAO.autenticar(username, password);
            if (rol != null) {
                switch (rol) {
                    case "administrador":
                        new VistaAdministrador(); // Abre la vista de administrador
                        break;
                    case "cliente":
                        new VistaCliente(); // Abre la vista de cliente
                        break;
                    default:
                        lblError.setText("Unknown role.");
                        return;
                }
                this.close(); // Cierra la ventana de login
            } else {
                lblError.setText("Invalid username or password.");
            }
        }

     */
    private void autenticarUsuario() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Please fill in all fields.");
            return;
        }

        // Verificar rol del usuario
        String rol = loginDAO.autenticar(username, password);
        if (rol != null) {
            switch (rol) {
                case "administrador":
                    new VistaAdministrador(); // Abre la vista de administrador
                    this.close(); // Cierra la ventana de login
                    break;
                case "cliente":
                    // Obtener los datos del cliente
                    ClienteDAO cliente = ClienteDAO.obtenerClientePorEmailYPassword(username, password);
                    if (cliente != null) {
                        new VistaCliente(cliente); // Pasa el objeto cliente a la vista de cliente
                        this.close(); // Cierra la ventana de login
                    } else {
                        lblError.setText("Error retrieving client data.");
                    }
                    break;
                default:
                    lblError.setText("Unknown role.");
                    return;
            }
        } else {
            lblError.setText("Invalid username or password.");
        }
    }


}