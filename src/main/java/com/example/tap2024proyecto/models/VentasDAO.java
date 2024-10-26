package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentasDAO {

    private int idVenta;
    private String fechaVenta;
    private int idCliente;
    private String nombreCte ; // Nueva propiedad


    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCte ;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCte  = nombreCliente;
    }

    public int Insert(){
        int rowCount;
        String query = "INSERT INTO tblventa(fechaVenta, idCliente)" + " values('" + this.fechaVenta + "', " + this.idCliente + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        }catch (SQLException e){
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    public void UPDATE(){
        String query = "UPDATE tblventa SET fechaVenta = '" + this.fechaVenta + "', " + "idCliente = " + this.idCliente + " WHERE idVenta = " + this.idVenta;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void  DELETE(){
        String query = "DELETE FROM tblventa WHERE idVenta = " + this.idVenta;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<VentasDAO> SELECTALL() {
        ObservableList<VentasDAO> listaVentas = FXCollections.observableArrayList();
        String query = "SELECT v.idVenta, v.fechaVenta, v.idCliente, c.nombreCte " +
                "FROM tblVenta v " +
                "JOIN tblCliente c ON v.idCliente = c.idCliente"; // JOIN para obtener el nombre del cliente
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                VentasDAO venta = new VentasDAO();
                venta.setIdVenta(res.getInt("idVenta"));
                venta.setFechaVenta(res.getString("fechaVenta"));
                venta.setIdCliente(res.getInt("idCliente"));
                venta.setNombreCliente(res.getString("nombreCte")); // Asignar el nombre del cliente
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVentas;
    }
}
