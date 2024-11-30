package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasDAO {
    private int idVenta;
    private int idCliente;
    private String fechaVenta;
    private double totalVenta;

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    // Método para insertar una venta
    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblVenta (idCliente, fechaVenta, totalVenta) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idCliente);
            stmt.setString(2, this.fechaVenta);
            stmt.setDouble(3, this.totalVenta);

            rowCount = stmt.executeUpdate();
            System.out.println("Venta registrada correctamente.");
        } catch (SQLException e) {
            rowCount = 0;
            System.err.println("Error al registrar venta:");
            e.printStackTrace();
        }
        return rowCount;
    }

    // Método para actualizar una venta
    public void UPDATE() {
        String query = "UPDATE tblVenta SET idCliente = ?, fechaVenta = ?, totalVenta = ? WHERE idVenta = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idCliente);
            stmt.setString(2, this.fechaVenta);
            stmt.setDouble(3, this.totalVenta);
            stmt.setInt(4, this.idVenta);

            stmt.executeUpdate();
            System.out.println("Venta actualizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar venta:");
            e.printStackTrace();
        }
    }

    // Método para eliminar una venta
    public void DELETE() {
        String query = "DELETE FROM tblVenta WHERE idVenta = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idVenta);
            stmt.executeUpdate();
            System.out.println("Venta eliminada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar venta:");
            e.printStackTrace();
        }
    }

    // Método para obtener todas las ventas
    public ObservableList<VentasDAO> SELECTALL() {
        ObservableList<VentasDAO> listaVentas = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblVenta";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                VentasDAO venta = new VentasDAO();
                venta.setIdVenta(rs.getInt("idVenta"));
                venta.setIdCliente(rs.getInt("idCliente"));
                venta.setFechaVenta(rs.getString("fechaVenta"));
                venta.setTotalVenta(rs.getDouble("totalVenta"));
                listaVentas.add(venta);
            }
            System.out.println("Ventas cargadas: " + listaVentas.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas:");
            e.printStackTrace();
        }
        return listaVentas;
    }

    // Método para obtener las canciones más vendidas
    public ObservableList<CancionesMasVendidas> obtenerCancionesMasVendidas() {
        ObservableList<CancionesMasVendidas> lista = FXCollections.observableArrayList();
        String query = "SELECT c.tituloCancion, COUNT(dv.idVenta) AS ventas " +
                "FROM tblCancion c " +
                "JOIN tblDetalleVenta dv ON c.idCancion = dv.idCancion " +
                "JOIN tblVenta v ON dv.idVenta = v.idVenta " +
                "WHERE DATE_FORMAT(v.fechaVenta, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') " +
                "GROUP BY c.tituloCancion " +
                "ORDER BY ventas DESC " +
                "LIMIT 10;";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new CancionesMasVendidas(rs.getString("tituloCancion"), rs.getInt("ventas")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las canciones más vendidas:");
            e.printStackTrace();
        }
        return lista;
    }

    // Método para obtener los artistas con más ventas en el mes actual
    public ObservableList<ArtistasVentas> obtenerArtistasConMasVentas() {
        ObservableList<ArtistasVentas> lista = FXCollections.observableArrayList();
        String query = "SELECT a.nombreArtista, COUNT(v.idVenta) AS ventas " +
                "FROM tblArtista a " +
                "JOIN tblCancion c ON a.idArtista = c.idArtista " +
                "JOIN tblDetalleVenta dv ON c.idCancion = dv.idCancion " +
                "JOIN tblVenta v ON dv.idVenta = v.idVenta " +
                "WHERE DATE_FORMAT(v.fechaVenta, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') " +
                "GROUP BY a.nombreArtista " +
                "ORDER BY ventas DESC;";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ArtistasVentas(rs.getString("nombreArtista"), rs.getInt("ventas")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los artistas con más ventas:");
            e.printStackTrace();
        }
        return lista;
    }

    // Método para obtener las ventas realizadas en el mes actual
    public ObservableList<VentasMensuales> obtenerVentasMensuales() {
        ObservableList<VentasMensuales> lista = FXCollections.observableArrayList();
        String query = "SELECT DATE_FORMAT(fechaVenta, '%Y-%m') AS mes, COUNT(*) AS cantidad_ventas " +
                "FROM tblVenta " +
                "WHERE DATE_FORMAT(fechaVenta, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') " +
                "GROUP BY mes;";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new VentasMensuales(rs.getString("mes"), rs.getInt("cantidad_ventas")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las ventas mensuales:");
            e.printStackTrace();
        }
        return lista;
    }

    // Clases auxiliares para las estadísticas
    public static class VentasMensuales {
        private final String mes;
        private final int cantidadVentas;

        public VentasMensuales(String mes, int cantidadVentas) {
            this.mes = mes;
            this.cantidadVentas = cantidadVentas;
        }

        public String getMes() {
            return mes;
        }

        public int getCantidadVentas() {
            return cantidadVentas;
        }
    }

    public static class ArtistasVentas {
        private final String artista;
        private final int ventas;

        public ArtistasVentas(String artista, int ventas) {
            this.artista = artista;
            this.ventas = ventas;
        }

        public String getArtista() {
            return artista;
        }

        public int getVentas() {
            return ventas;
        }
    }

    public static class CancionesMasVendidas {
        private final String cancion;
        private final int ventas;

        public CancionesMasVendidas(String cancion, int ventas) {
            this.cancion = cancion;
            this.ventas = ventas;
        }

        public String getCancion() {
            return cancion;
        }

        public int getVentas() {
            return ventas;
        }
    }
}
