package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {
    private int idGenero;
    private String nombreGenero;

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }
    public String toString() {
        return nombreGenero;
    }

    // Método para insertar un género
    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblGenero(nombreGen) VALUES (?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.nombreGenero);
            rowCount = stmt.executeUpdate();
            System.out.println("Género agregado correctamente.");
        } catch (SQLException e) {
            rowCount = 0;
            System.err.println("Error al insertar género:");
            e.printStackTrace();
        }
        return rowCount;
    }

    // Método para actualizar un género
    public void UPDATE() {
        String query = "UPDATE tblGenero SET nombreGen = ? WHERE idGenero = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.nombreGenero);
            stmt.setInt(2, this.idGenero);

            stmt.executeUpdate();
            System.out.println("Género actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar género:");
            e.printStackTrace();
        }
    }

    // Método para eliminar un género
    public void DELETE() {
        String query = "DELETE FROM tblGenero WHERE idGenero = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idGenero);
            stmt.executeUpdate();
            System.out.println("Género eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar género:");
            e.printStackTrace();
        }
    }

    // Método para obtener todos los géneros
    public ObservableList<GeneroDAO> SELECTALL() {
        ObservableList<GeneroDAO> listaGeneros = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblGenero";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GeneroDAO genero = new GeneroDAO();
                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombreGenero(rs.getString("nombreGen"));
                listaGeneros.add(genero);
            }
            System.out.println("Géneros cargados: " + listaGeneros.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener géneros:");
            e.printStackTrace();
        }
        return listaGeneros;
    }

    public Iterable<GeneroDAO> obtenerTodos() {
        List<GeneroDAO> listaGeneros = new ArrayList<>();
        String query = "SELECT * FROM tblgenero";

        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                GeneroDAO genero = new GeneroDAO();
                genero.setIdGenero(res.getInt("idGenero"));
                genero.setNombreGenero(res.getString("nombreGen"));
                listaGeneros.add(genero);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaGeneros;
    }

}
