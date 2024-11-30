package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumDAO {
    private int idAlbum;
    private String tituloAlbum;
    private String fechaAlbum;
    private double costoAlbum; // Nuevo campo para el costo del álbum

    // Getters y Setters
    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getTituloAlbum() {
        return tituloAlbum;
    }

    public void setTituloAlbum(String tituloAlbum) {
        this.tituloAlbum = tituloAlbum;
    }

    public String getFechaAlbum() {
        return fechaAlbum;
    }

    public void setFechaAlbum(String fechaAlbum) {
        this.fechaAlbum = fechaAlbum;
    }

    public double getCostoAlbum() {
        return costoAlbum;
    }

    public void setCostoAlbum(double costoAlbum) {
        this.costoAlbum = costoAlbum;
    }
    public String toString() {
        return tituloAlbum; // Devuelve el título del álbum
    }

    // Métodos para operaciones en la base de datos

    // INSERT
    public int INSERT() {
        String query = "INSERT INTO tblAlbum (tituloAlbum, fechaAlbum, costoAlbum) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tituloAlbum);
            stmt.setString(2, fechaAlbum);
            stmt.setDouble(3, costoAlbum);

            return stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al insertar álbum:");
            e.printStackTrace();
        }
        return 0;
    }

    // UPDATE
    public void UPDATE() {
        String query = "UPDATE tblAlbum SET tituloAlbum = ?, fechaAlbum = ?, costoAlbum = ? WHERE idAlbum = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tituloAlbum);
            stmt.setString(2, fechaAlbum);
            stmt.setDouble(3, costoAlbum);
            stmt.setInt(4, idAlbum);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al actualizar álbum:");
            e.printStackTrace();
        }
    }

    // DELETE
    public void DELETE() {
        String query = "DELETE FROM tblAlbum WHERE idAlbum = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAlbum);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al eliminar álbum:");
            e.printStackTrace();
        }
    }

    // SELECT ALL
    public ObservableList<AlbumDAO> SELECTALL() {
        ObservableList<AlbumDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblAlbum";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setIdAlbum(rs.getInt("idAlbum"));
                album.setTituloAlbum(rs.getString("tituloAlbum"));
                album.setFechaAlbum(rs.getString("fechaAlbum"));
                album.setCostoAlbum(rs.getDouble("costoAlbum")); // Lee el costo del álbum
                lista.add(album);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener álbumes:");
            e.printStackTrace();
        }
        return lista;
    }
}
