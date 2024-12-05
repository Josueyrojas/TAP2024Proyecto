package com.example.tap2024proyecto.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private static final String DB = "spotify"; // Nombre de la base de datos
    private static final String USER = "admin"; // Usuario de la base de datos
    private static final String PWD = "12345678"; // Contraseña de la base de datos
    private static final String HOST = "localhost"; // Host de la base de datos
    private static final String PORT = "3306"; // Puerto de la base de datos

    /**
     * Autenticar al usuario verificando si es administrador o cliente.
     *
     * @param username Nombre de usuario (email o nombre de admin)
     * @param password Contraseña ingresada
     * @return El rol del usuario ("administrador" o "cliente") si las credenciales son válidas, null en caso contrario.
     */
    public String autenticar(String username, String password) {
        String rol = null;

        try (Connection connection = getConnection()) {
            // Verificar en la tabla administradores
            String queryAdmin = "SELECT * FROM administradores WHERE username = ? AND password = ?";
            try (PreparedStatement stmtAdmin = connection.prepareStatement(queryAdmin)) {
                stmtAdmin.setString(1, username);
                stmtAdmin.setString(2, password);

                ResultSet rsAdmin = stmtAdmin.executeQuery();
                if (rsAdmin.next()) {
                    rol = "administrador";
                    return rol; // Si es administrador, no verificamos más
                }
            }

            // Verificar en la tabla tblcliente
            ClienteDAO cliente = ClienteDAO.obtenerClientePorEmailYPassword(username, password);
            if (cliente != null) {
                rol = "cliente";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }


    /**
     * Conexión centralizada a la base de datos.
     *
     * @return Objeto Connection
     * @throws SQLException Si ocurre un error en la conexión
     */
    private Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB, USER, PWD
        );
    }
}
