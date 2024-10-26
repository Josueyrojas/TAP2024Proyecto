package com.example.tap2024proyecto.models;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private static final String DB = "spotify"; // Nombre de la base de datos
    private static final String USER = "admin";  // Usuario de la base de datos
    private static final String PWD = "12345678"; // Contraseña de la base de datos
    private static final String HOST = "localhost"; // Host de la base de datos
    private static final String PORT = "3306"; // Puerto de la base de datos

    // Método para autenticar al usuario
    public boolean autenticar(String username, String password) {
        boolean autenticado = false;

        // Conexión a la base de datos
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB, USER, PWD)) {
            String query = "SELECT * FROM administradores WHERE username = ? AND password = ?"; // Ajusta el nombre de la tabla según tu esquema

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Si encontramos un resultado, el usuario existe y las credenciales son correctas
                    autenticado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autenticado;
    }
}

