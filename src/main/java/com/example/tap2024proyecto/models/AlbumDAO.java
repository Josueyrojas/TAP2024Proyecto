package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {
    private int idAlbum;
    private String tituloAlbum;
    private String fechaAlbum;
    private double costoAlbum; // Nuevo campo para el costo del álbum
    private String imagenAlbum; // Nuevo campo para la ruta de la imagen


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

    public String getImagenAlbum() {
        return imagenAlbum;
    }

    public void setImagenAlbum(String imagenAlbum) {
        this.imagenAlbum = imagenAlbum;
    }

    public String toString() {
        return tituloAlbum; //
    }

    // Métodos para operaciones en la base de datos

    // INSERT
    public int INSERT() {
        // Validar que los campos obligatorios no sean nulos o vacíos
        if (tituloAlbum == null || tituloAlbum.trim().isEmpty()) {
            System.err.println("El título del álbum no puede estar vacío.");
            return 0; // Salir si el título es inválido
        }
        if (fechaAlbum == null || fechaAlbum.trim().isEmpty()) {
            System.err.println("La fecha del álbum no puede estar vacía.");
            return 0; // Salir si la fecha es inválida
        }
        if (costoAlbum <= 0) {
            System.err.println("El costo del álbum debe ser mayor a 0.");
            return 0; // Salir si el costo es inválido
        }

        // Consulta SQL para insertar el álbum
        String query = "INSERT INTO tblAlbum (tituloAlbum, fechaAlbum, costoAlbum, imagenAlbum) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer los valores de los parámetros
            stmt.setString(1, tituloAlbum);  // Título del álbum
            stmt.setString(2, fechaAlbum);   // Fecha del álbum
            stmt.setDouble(3, costoAlbum);   // Costo del álbum
            stmt.setString(4, imagenAlbum);  // Imagen del álbum (puede ser nula)

            // Ejecutar la inserción
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al insertar álbum:");
            e.printStackTrace();
        }
        return 0; // Si ocurre un error, retornar 0
    }

    // UPDATE
    public void UPDATE() {
        String query = "UPDATE tblAlbum SET tituloAlbum = ?, fechaAlbum = ?, costoAlbum = ?, imagenAlbum = ? WHERE idAlbum = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tituloAlbum);
            stmt.setString(2, fechaAlbum);
            stmt.setDouble(3, costoAlbum);
            stmt.setString(4, imagenAlbum); // Actualizar la ruta de la imagen
            stmt.setInt(5, idAlbum);

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
        ObservableList<AlbumDAO> albumes = FXCollections.observableArrayList();
        String query = "SELECT idAlbum, tituloAlbum, fechaAlbum, costoAlbum, imagenAlbum FROM tblalbum"; // Incluye imagenAlbum
        try (Connection con = Conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setIdAlbum(rs.getInt("idAlbum"));
                album.setTituloAlbum(rs.getString("tituloAlbum"));
                album.setFechaAlbum(rs.getString("fechaAlbum"));
                album.setCostoAlbum(rs.getDouble("costoAlbum"));  // Obtener el costo del álbum
                album.setImagenAlbum(rs.getString("imagenAlbum"));  // Obtener la ruta de la imagen
                albumes.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albumes;


    }
    public Iterable<AlbumDAO> obtenerTodos() {
        List<AlbumDAO> lista = new ArrayList<>();
        String query = "SELECT * FROM tblalbum";

        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setIdAlbum(res.getInt("idAlbum"));
                album.setTituloAlbum(res.getString("tituloAlbum"));
               // album.setFechaAlbum(String.valueOf(res.getInt("fechaAlbum")));
               // album.setCostoAlbum(res.getInt("costoAlbum"));
                lista.add(album);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


}
