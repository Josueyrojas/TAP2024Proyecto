package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.tap2024proyecto.models.Conexion.getConnection;

public class CancionDAO {
    private int idCancion;
    private String tituloCan;
    private double costoCancion;
    private int idGenero;
    private int idAlbum;
    private int idArtista;


    public CancionDAO() {}


    public CancionDAO(int idCancion, String tituloCan, double costoCancion, int idGenero, int idAlbum, int idArtista) {
        this.idCancion = idCancion;
        this.tituloCan = tituloCan;
        this.costoCancion = costoCancion;
        this.idGenero = idGenero;
        this.idAlbum = idAlbum;
        this.idArtista = idArtista;
    }
    public List<String> obtenerCancionesPorAlbum(int idAlbum) {
        List<String> canciones = new ArrayList<>();
        try (Connection con = Conexion.getConnection()) {
            String query = "SELECT nombreCancion FROM canciones WHERE idAlbum = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idAlbum);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                canciones.add(rs.getString("nombreCancion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canciones;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getTituloCan() {
        return tituloCan;
    }

    public void setTituloCan(String tituloCan) {
        this.tituloCan = tituloCan;
    }

    public double getCostoCancion() {
        return costoCancion;
    }

    public void setCostoCancion(double costoCancion) {
        this.costoCancion = costoCancion;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }


    public int INSERT() {

        if (cancionExiste()) {
            System.out.println("Error: Ya existe una canción con ese título.");
            return 0;
        }

        String query = "INSERT INTO tblCancion (tituloCan, costoCan, idGenero, idAlbum, idArtista) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.tituloCan);
            stmt.setDouble(2, this.costoCancion);
            stmt.setInt(3, this.idGenero);
            stmt.setInt(4, this.idAlbum);
            stmt.setInt(5, this.idArtista); // Establecer la clave foránea
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar canción: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }


    public void UPDATE() {

        if (cancionExiste()) {
            System.out.println("Error: Ya existe una canción con ese título.");
            return;
        }

        String query = "UPDATE tblCancion SET tituloCan = ?, costoCan = ?, idGenero = ?, idAlbum = ?, idArtista = ? WHERE idCancion = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.tituloCan);
            stmt.setDouble(2, this.costoCancion);
            stmt.setInt(3, this.idGenero);
            stmt.setInt(4, this.idAlbum);
            stmt.setInt(5, this.idArtista); // Actualizar la clave foránea
            stmt.setInt(6, this.idCancion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar canción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean cancionExiste() {
        String query = "SELECT * FROM tblCancion WHERE tituloCan = ? AND idCancion != ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.tituloCan);
            stmt.setInt(2, this.idCancion);  // Excluir la canción actual en la validación
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Si ya existe una canción con el mismo título
        } catch (SQLException e) {
            System.err.println("Error al verificar si existe canción: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public ObservableList<CancionDAO> SELECTALL() {
        ObservableList<CancionDAO> listaCanciones = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblCancion";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CancionDAO cancion = new CancionDAO(
                        rs.getInt("idCancion"),
                        rs.getString("tituloCan"),
                        rs.getDouble("costoCan"),
                        rs.getInt("idGenero"),
                        rs.getInt("idAlbum"),
                        rs.getInt("idArtista") // Obtener idArtista de la consulta
                );
                listaCanciones.add(cancion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las canciones: " + e.getMessage());
            e.printStackTrace();
        }
        return listaCanciones;
    }


    public static CancionDAO SELECTBYID(int idCancion) {
        String query = "SELECT * FROM tblCancion WHERE idCancion = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCancion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CancionDAO(
                        rs.getInt("idCancion"),
                        rs.getString("tituloCan"),
                        rs.getDouble("costoCan"),
                        rs.getInt("idGenero"),
                        rs.getInt("idAlbum"),
                        rs.getInt("idArtista") // Obtener idArtista
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la canción por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean DELETE() {
        String query = "DELETE FROM tblCancion WHERE idCancion = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, this.idCancion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar canción: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}