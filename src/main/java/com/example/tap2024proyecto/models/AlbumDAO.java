package com.example.tap2024proyecto.models;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AlbumDAO {

    private int idAlbum;
    private String tituloAlbum;
    private String fechaAlbum;

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


    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblalbum(tituloAlbum, fechaAlbum) " + "VALUES('" + this.tituloAlbum + "', '" + this.fechaAlbum + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    public void UPDATE() {
        String query = "UPDATE tblalbum SET tituloAlbum = '" + this.tituloAlbum + "', fechaAlbum = '" + this.fechaAlbum + "' " +
                "WHERE idAlbum = " + this.idAlbum;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblalbum WHERE idAlbum = " + this.idAlbum;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AlbumDAO> SELECTALL() {
        ObservableList<AlbumDAO> listaAlbums = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblalbum";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setIdAlbum(rs.getInt("idAlbum"));
                album.setTituloAlbum(rs.getString("tituloAlbum"));
                album.setFechaAlbum(rs.getString("fechaAlbum"));
                listaAlbums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAlbums;
    }
}