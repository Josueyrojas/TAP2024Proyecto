package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistaDAO {

    private int idArtista;
    private String nombreArt;

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

    public int INSERT(){
        int rowCount;
        String query = "INSERT INTO tblartista(nombreArt)" + " values('"+this.nombreArt+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    public void UPDATE(){
        String query = "UPDATE tblartista SET nombreArt = '" + this.nombreArt + "' WHERE idArtista = " + this.idArtista;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM tblartista WHERE idArtista = " + this.idArtista;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<ArtistaDAO> SELECTALL(){
        ArtistaDAO objArt;
        String query = "SELECT * FROM tblartista";
        ObservableList<ArtistaDAO> ListaA = FXCollections.observableArrayList();
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                objArt = new ArtistaDAO();
                objArt.idArtista = res.getInt("idArtista");
                objArt.nombreArt = res.getString("nombreArt");
                ListaA.add(objArt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaA;
    }
}