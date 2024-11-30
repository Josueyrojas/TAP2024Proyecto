package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClienteDAO {
    private int idCte;
    private String nomCte;
    private String telCte;
    private String emailCte;
    private String password; // Nuevo atributo para contraseña

    // Constructor vacío
    public ClienteDAO() {}

    // Constructor con parámetros
    public ClienteDAO(int idCte, String nomCte, String telCte, String emailCte, String password) {
        this.idCte = idCte;
        this.nomCte = nomCte;
        this.telCte = telCte;
        this.emailCte = emailCte;
        this.password = password;
    }

    // Getters y Setters
    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public String getTelCte() {
        return telCte;
    }

    public void setTelCte(String telCte) {
        this.telCte = telCte;
    }

    public String getEmailCte() {
        return emailCte;
    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método para insertar un nuevo cliente
    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblCliente(nomCte, telCte, emailCte, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.nomCte);
            stmt.setString(2, this.telCte);
            stmt.setString(3, this.emailCte);
            stmt.setString(4, this.password); // Incluir contraseña

            rowCount = stmt.executeUpdate();
            System.out.println("Cliente agregado correctamente.");
        } catch (Exception e) {
            rowCount = 0;
            System.err.println("Error al insertar cliente:");
            e.printStackTrace();
        }
        return rowCount;
    }

    // Método para actualizar un cliente existente
    public void UPDATE() {
        String query = "UPDATE tblCliente SET nomCte = ?, telCte = ?, emailCte = ?, password = ? WHERE idCte = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.nomCte);
            stmt.setString(2, this.telCte);
            stmt.setString(3, this.emailCte);
            stmt.setString(4, this.password); // Actualizar contraseña
            stmt.setInt(5, this.idCte);

            stmt.executeUpdate();
            System.out.println("Cliente actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente:");
            e.printStackTrace();
        }
    }

    // Método para eliminar un cliente
    public void DELETE() {
        String query = "DELETE FROM tblCliente WHERE idCte = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.idCte);

            stmt.executeUpdate();
            System.out.println("Cliente eliminado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente:");
            e.printStackTrace();
        }
    }

    // Método para obtener todos los clientes
    public ObservableList<ClienteDAO> SELECTALL() {
        ObservableList<ClienteDAO> listaC = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblCliente";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                ClienteDAO objCte = new ClienteDAO(
                        res.getInt("idCte"),
                        res.getString("nomCte"),
                        res.getString("telCte"),
                        res.getString("emailCte"),
                        res.getString("password") // Leer contraseña
                );
                listaC.add(objCte);
            }

            System.out.println("Clientes cargados: " + listaC.size());
        } catch (Exception e) {
            System.err.println("Error al obtener clientes:");
            e.printStackTrace();
        }

        return listaC;
    }
}

//cada renglón de la tabla representa un objeto de la clase Cliente




//Buscaminas si es con archivos sin arreglos