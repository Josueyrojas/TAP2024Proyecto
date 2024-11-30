package com.example.tap2024proyecto.models;

/*
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
}*/

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
     * @param username Nombre de usuario
     * @param password Contraseña ingresada
     * @return El rol del usuario (administrador o cliente) si las credenciales son válidas, null en caso contrario
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
            // Verificar en la tabla tblcliente
            String queryCliente = "SELECT * FROM tblcliente WHERE emailCte = ? AND password = ?";
            try (PreparedStatement stmtCliente = connection.prepareStatement(queryCliente)) {
                stmtCliente.setString(1, username); // Usa el nombre correcto de la columna
                stmtCliente.setString(2, password);

                ResultSet rsCliente = stmtCliente.executeQuery();
                if (rsCliente.next()) {
                    rol = "cliente";
                }

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


