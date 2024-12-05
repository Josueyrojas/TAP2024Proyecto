package com.example.tap2024proyecto.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    static private final String DB = "spotify";
    static private final String USER = "admin";
    static private final String PWD = "12345678";
    static private final String HOST = "localhost";
    static private final String PORT = "3306";
    public static Connection connection;

    /**
     * Crea o restablece la conexión si no está activa.
     */
    public static void CrearConexion() {
        try {
            if (connection == null || connection.isClosed()) { // Verifica si la conexión es nula o está cerrada
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB, USER, PWD
                );
                System.out.println("Conexión establecida a la base de datos.");
            } else {
                System.out.println("Conexión ya activa.");
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexión actual o la crea si es necesario.
     *
     * @return Conexión activa
     */
    public static Connection getConexion() {
        CrearConexion(); // Garantiza que la conexión esté activa
        return connection;
    }

    /**
     * Cierra la conexión si está activa.
     */
    public static void CerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexión:");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        CrearConexion(); // Garantiza que la conexión esté activa
        return connection;
    }
}


