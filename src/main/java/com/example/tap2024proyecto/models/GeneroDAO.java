package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneroDAO {

    private int idGenero;
    private String nombreGen;

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGen() {
        return nombreGen;
    }

    public void setNombreGen(String nombreGen) {
        this.nombreGen = nombreGen;
    }

    public int INSERT() {
        int result = 0;
        String query = "INSERT INTO tblGenero(nombreGen) VALUES('" + this.nombreGen + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void UPDATE() {
        String query = "UPDATE tblGenero SET nombreGen = '" + this.nombreGen + "' WHERE idGenero = " + this.idGenero;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblGenero WHERE idGenero = " + this.idGenero;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<GeneroDAO> SELECTALL() {
        ObservableList<GeneroDAO> listaGeneros = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblGenero";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GeneroDAO genero = new GeneroDAO();
                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombreGen(rs.getString("nombreGen"));
                listaGeneros.add(genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaGeneros;
    }
}