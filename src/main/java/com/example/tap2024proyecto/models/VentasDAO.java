package com.example.tap2024proyecto.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Statement;
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

    // Método para calcular el total de la venta basado en los precios de los productos
    public double calcularTotalVenta(int[] idItems) {
        double total = 0.0;
        String query = "SELECT precio FROM tblCancion WHERE idCancion = ?";  // Asumimos que estamos vendiendo canciones

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int itemId : idItems) {
                stmt.setInt(1, itemId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        total += rs.getDouble("precio");  // Acumulamos el precio de cada item
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular el total de la venta:");
            e.printStackTrace();
        }
        return total;
    }

    public int INSERT(int[] idsProductos) {
        String queryVenta = "INSERT INTO tblVenta (idCliente, fechaVenta, totalVenta) VALUES (?, ?, ?)";
        String queryDetalleVenta = "INSERT INTO tblDetalleVenta (idVenta, idCancion) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmtVenta = conn.prepareStatement(queryVenta, Statement.RETURN_GENERATED_KEYS)) {

            // Insertar la venta
            stmtVenta.setInt(1, this.getIdCliente());
            stmtVenta.setString(2, this.getFechaVenta());
            stmtVenta.setDouble(3, this.getTotalVenta());

            int rows = stmtVenta.executeUpdate();

            if (rows > 0) {
                try (ResultSet generatedKeys = stmtVenta.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idVentaGenerada = generatedKeys.getInt(1);

                        // Insertar los productos asociados a la venta
                        try (PreparedStatement stmtDetalle = conn.prepareStatement(queryDetalleVenta)) {
                            for (int idProducto : idsProductos) {
                                stmtDetalle.setInt(1, idVentaGenerada);
                                stmtDetalle.setInt(2, idProducto);
                                stmtDetalle.addBatch();
                            }
                            stmtDetalle.executeBatch();
                        }
                        return 1; // Venta y productos insertados correctamente
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar la venta:");
            e.printStackTrace();
        }

        return 0; // Error al insertar la venta
    }

    public int UPDATE(int[] idItems) {
        int rowCount = 0;
        double total = calcularTotalVenta(idItems);  // Recálculo del total con los nuevos items
        String queryVenta = "UPDATE tblVenta SET idCliente = ?, fechaVenta = ?, totalVenta = ? WHERE idVenta = ?";

        try (Connection conn = Conexion.getConexion()) {
            // Actualizar la venta
            try (PreparedStatement stmt = conn.prepareStatement(queryVenta)) {
                stmt.setInt(1, this.idCliente);  // Establecemos el idCliente
                stmt.setString(2, this.fechaVenta);  // Establecemos la fecha de venta
                stmt.setDouble(3, total);  // Establecemos el total de la venta
                stmt.setInt(4, this.idVenta);  // Establecemos el id de la venta (la venta a actualizar)

                rowCount = stmt.executeUpdate();  // Ejecutar la actualización
            }

            // Si se actualizó la venta, actualizar los detalles
            if (rowCount > 0) {
                // Eliminar los detalles antiguos
                eliminarDetallesVenta();

                // Insertar los nuevos detalles
                String queryDetalleVenta = "INSERT INTO tblDetalleVenta (idVenta, idCancion, idAlbum) VALUES (?, ?, ?)";
                try (PreparedStatement stmtDetalle = conn.prepareStatement(queryDetalleVenta)) {
                    for (int itemId : idItems) {
                        stmtDetalle.setInt(1, this.idVenta);
                        stmtDetalle.setInt(2, itemId);  // Suponemos que itemId es un idCancion (modificar si es un álbum)
                        stmtDetalle.setNull(3, java.sql.Types.INTEGER); // El álbum puede ser nulo en el caso de compra de canciones

                        stmtDetalle.addBatch(); // Usamos batch para eficiencia
                    }

                    // Ejecutamos el batch de inserciones
                    stmtDetalle.executeBatch();
                }

                // Actualizar el stock de canciones
                actualizarStock(idItems);
            }

            System.out.println("Venta actualizada correctamente.");
        } catch (SQLException e) {
            rowCount = 0;
            System.err.println("Error al actualizar la venta:");
            e.printStackTrace();
        }
        return rowCount;  // Devuelve el número de filas afectadas
    }


    // Método para eliminar los detalles de una venta
    private void eliminarDetallesVenta() {
        String queryDetalle = "DELETE FROM tblDetalleVenta WHERE idVenta = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(queryDetalle)) {

            stmt.setInt(1, this.idVenta);
            stmt.executeUpdate();  // Eliminamos los detalles de la venta
            System.out.println("Detalles de la venta eliminados.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar los detalles de la venta:");
            e.printStackTrace();
        }
    }

    // Método para eliminar una venta
    public int DELETE() {
        int rowCount = 0;
        String queryDetalle = "DELETE FROM tblDetalleVenta WHERE idVenta = ?";  // Primero eliminamos los detalles
        String queryVenta = "DELETE FROM tblVenta WHERE idVenta = ?";  // Luego eliminamos la venta

        try (Connection conn = Conexion.getConexion()) {
            // Eliminar los detalles de la venta
            try (PreparedStatement stmtDetalle = conn.prepareStatement(queryDetalle)) {
                stmtDetalle.setInt(1, this.idVenta);
                stmtDetalle.executeUpdate();
            }

            // Eliminar la venta
            try (PreparedStatement stmtVenta = conn.prepareStatement(queryVenta)) {
                stmtVenta.setInt(1, this.idVenta);
                rowCount = stmtVenta.executeUpdate();
            }

            System.out.println("Venta eliminada correctamente.");
        } catch (SQLException e) {
            rowCount = 0;
            System.err.println("Error al eliminar la venta:");
            e.printStackTrace();
        }

        return rowCount;  // Devuelve el número de filas afectadas
    }

    // Método para actualizar el stock de productos (canciones/álbumes)
    public void actualizarStock(int[] idItems) {
        String queryUpdateStock = "UPDATE tblCancion SET stock = stock - 1 WHERE idCancion = ?";  // Si los productos son canciones

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(queryUpdateStock)) {

            for (int itemId : idItems) {
                stmt.setInt(1, itemId);
                stmt.addBatch();  // Añadimos al batch
            }

            // Ejecutamos el batch de actualizaciones de stock
            stmt.executeBatch();
            System.out.println("Stock actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar el stock de los productos:");
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

    // Método para obtener estadísticas de ventas mensuales
    public ObservableList<VentasMensuales> obtenerVentasMensuales() {
        ObservableList<VentasMensuales> lista = FXCollections.observableArrayList();
        String query = "SELECT DATE_FORMAT(fechaVenta, '%Y-%m') AS mes, COUNT(*) AS cantidadVentas " +
                "FROM tblVenta " +
                "GROUP BY mes " +
                "ORDER BY mes DESC";  // Agrupamos las ventas por mes

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new VentasMensuales(rs.getString("mes"), rs.getInt("cantidadVentas")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las ventas mensuales:");
            e.printStackTrace();
        }
        return lista;
    }
    public ObservableList<VentasDAO> SELECTBYCLIENTE(int idCte) {
        ObservableList<VentasDAO> listaVentas = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblVenta WHERE idCte = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCte);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    VentasDAO venta = new VentasDAO();
                    venta.setIdVenta(rs.getInt("idVenta"));
                    venta.setIdCliente(rs.getInt("idCte"));
                    venta.setFechaVenta(rs.getString("fechaVenta"));
                    // Si agregaste la columna totalVenta a la tabla tblVenta:
                    venta.setTotalVenta(rs.getDouble("totalVenta"));
                    listaVentas.add(venta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas del cliente:");
            e.printStackTrace();
        }
        return listaVentas;
    }

    // Método para obtener los artistas con más ventas
    public ObservableList<ArtistasVentas> obtenerArtistasConMasVentas() {
        ObservableList<ArtistasVentas> lista = FXCollections.observableArrayList();
        String query = "SELECT a.nombreArtista, COUNT(dv.idVenta) AS ventas " +
                "FROM tblArtista a " +
                "JOIN tblCancion c ON a.idArtista = c.idArtista " +
                "JOIN tblDetalleVenta dv ON c.idCancion = dv.idCancion " +
                "JOIN tblVenta v ON dv.idVenta = v.idVenta " +
                "WHERE DATE_FORMAT(v.fechaVenta, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') " +
                "GROUP BY a.nombreArtista " +
                "ORDER BY ventas DESC " +
                "LIMIT 10;";  // Limita la consulta a los 10 artistas más vendidos

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
    public ObservableList<CancionesMasVendidas> obtenerCancionesMasVendidas() {
        ObservableList<CancionesMasVendidas> lista = FXCollections.observableArrayList();
        String query = "SELECT c.tituloCancion, COUNT(dv.idVenta) AS ventas " +
                "FROM tblCancion c " +
                "JOIN tblDetalleVenta dv ON c.idCancion = dv.idCancion " +
                "JOIN tblVenta v ON dv.idVenta = v.idVenta " +
                "WHERE DATE_FORMAT(v.fechaVenta, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m') " +
                "GROUP BY c.tituloCancion " +
                "ORDER BY ventas DESC " +
                "LIMIT 10;";  // Limita la consulta a las 10 canciones más vendidas

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Recorrer los resultados y agregarlos a la lista
            while (rs.next()) {
                lista.add(new CancionesMasVendidas(rs.getString("tituloCancion"), rs.getInt("ventas")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las canciones más vendidas:");
            e.printStackTrace();
        }
        return lista;
    }


    // Clase auxiliar para los artistas más vendidos
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