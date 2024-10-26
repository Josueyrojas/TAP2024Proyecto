package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionDAO {

    private int idCancion;
    private String tituloCan;
    private String duracionCan;
    private int idGenero; // Llave foránea
    private String nombreGen; // Nombre del género

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

    public String getDuracionCan() {
        return duracionCan;
    }

    public void setDuracionCan(String duracionCan) {
        this.duracionCan = duracionCan;
    }

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
        int rowCount;
        String query = "INSERT INTO tblcancion(tituloCan, duracionCan, idGenero) " +
                "VALUES('" + this.tituloCan + "', '" + this.duracionCan + "', " + this.idGenero + ")";
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
        String query = "UPDATE tblcancion SET tituloCan = '" + this.tituloCan + "', " +
                "duracionCan = '" + this.duracionCan + "', idGenero = " + this.idGenero +
                " WHERE idCancion = " + this.idCancion;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblcancion WHERE idCancion = " + this.idCancion;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CancionDAO> SELECTALL() {
        ObservableList<CancionDAO> listaCanciones = FXCollections.observableArrayList();
        String query = "SELECT c.idCancion, c.tituloCan, c.duracionCan, c.idGenero, g.nombreGen " +
                "FROM tblcancion c " +
                "JOIN tblgenero g ON c.idGenero = g.idGenero"; // JOIN para obtener el nombre del género
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                CancionDAO cancion = new CancionDAO();
                cancion.setIdCancion(res.getInt("idCancion"));
                cancion.setTituloCan(res.getString("tituloCan"));
                cancion.setDuracionCan(res.getString("duracionCan"));
                cancion.setIdGenero(res.getInt("idGenero"));
                cancion.setNombreGen(res.getString("nombreGen")); // Asignar el nombre del género
                listaCanciones.add(cancion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCanciones;
    }
}