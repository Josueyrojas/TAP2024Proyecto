package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;

public class ArtistaDAO {
    private int idArtista;
    private String nombreArt;

    // Getters y Setters
    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombreArt() {
        return nombreArt;
    }

    public void setNombreArt(String nombreArt) {
        this.nombreArt = nombreArt;
    }

    // Sobrescribir el método toString() para mostrar el nombre del artista en el ComboBox
    @Override
    public String toString() {
        return this.nombreArt; // Retorna el nombre del artista en lugar del objeto
    }

    // Método para insertar un artista
    public int INSERT() {
        int rowCount = 0;
        String query = "INSERT INTO tblArtista(nombreArt) VALUES (?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.nombreArt);
            rowCount = stmt.executeUpdate();
            System.out.println("Artista agregado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar artista:");
            e.printStackTrace();
        }
        return rowCount;
    }

    // Método para actualizar un artista
    public void UPDATE() {
        String query = "UPDATE tblArtista SET nombreArt = ? WHERE idArtista = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.nombreArt);
            stmt.setInt(2, this.idArtista);
            stmt.executeUpdate();
            System.out.println("Artista actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar artista:");
            e.printStackTrace();
        }
    }

    // Método para eliminar un artista
    public void DELETE() {
        String query = "DELETE FROM tblArtista WHERE idArtista = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idArtista);
            stmt.executeUpdate();
            System.out.println("Artista eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar artista:");
            e.printStackTrace();
        }
    }

    // Método para obtener todos los artistas
    public ObservableList<ArtistaDAO> SELECTALL() {
        ObservableList<ArtistaDAO> listaArtistas = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblArtista";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ArtistaDAO artista = new ArtistaDAO();
                artista.setIdArtista(rs.getInt("idArtista"));
                artista.setNombreArt(rs.getString("nombreArt"));
                listaArtistas.add(artista);
            }
            System.out.println("Artistas cargados: " + listaArtistas.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener artistas:");
            e.printStackTrace();
        }
        return listaArtistas;
    }

    // Método para obtener solo los nombres de los artistas (útil para ComboBox)
    public static ObservableList<String> getAllArtistas() {
        ObservableList<String> artistas = FXCollections.observableArrayList();
        String query = "SELECT nombreArt FROM tblArtista";

        ComboBox<String> comboArtistas = new ComboBox<>();
        comboArtistas.setItems(ArtistaDAO.getAllArtistas());


        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                artistas.add(rs.getString("nombreArt"));
            }
            System.out.println("Artistas cargados: " + artistas.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener los nombres de los artistas:");
            e.printStackTrace();
        }

        return artistas;
    }

    // Método para obtener un artista por su ID
    public static ArtistaDAO SELECTBYID(int idArtista) {
        ArtistaDAO artista = null;
        String query = "SELECT * FROM tblArtista WHERE idArtista = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idArtista);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                artista = new ArtistaDAO();
                artista.setIdArtista(rs.getInt("idArtista"));
                artista.setNombreArt(rs.getString("nombreArt"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el artista por ID:");
            e.printStackTrace();
        }
        return artista;
    }
}
