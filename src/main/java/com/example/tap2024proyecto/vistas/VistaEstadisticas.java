package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.VentasDAO;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaEstadisticas extends Stage {

    public VistaEstadisticas() {
        VentasDAO dao = new VentasDAO();

        // Gráfica de Ventas Mensuales
        BarChart<String, Number> graficaVentasMensuales = new BarChart<>(new CategoryAxis(), new NumberAxis());
        graficaVentasMensuales.setTitle("Ventas Mensuales");
        var serieMensuales = new BarChart.Series<String, Number>();
        serieMensuales.setName("Ventas");
        dao.obtenerVentasMensuales().forEach(vm ->
                serieMensuales.getData().add(new BarChart.Data<>(vm.getMes(), vm.getCantidadVentas()))
        );
        graficaVentasMensuales.getData().add(serieMensuales);

        // Gráfica de Artistas con Más Ventas

        // Gráfica de Canciones Más Vendidas
        BarChart<String, Number> graficaCanciones = new BarChart<>(new CategoryAxis(), new NumberAxis());
        graficaCanciones.setTitle("Canciones Más Vendidas");
        var serieCanciones = new BarChart.Series<String, Number>();
        serieCanciones.setName("Ventas");
        dao.obtenerCancionesMasVendidas().forEach(cv ->
                serieCanciones.getData().add(new BarChart.Data<>(cv.getCancion(), cv.getVentas()))
        );
        graficaCanciones.getData().add(serieCanciones);

        VBox vbox = new VBox(10, graficaVentasMensuales, graficaCanciones);
        Scene scene = new Scene(vbox, 800, 600);

        this.setTitle("Estadísticas de Ventas");
        this.setScene(scene);
        this.show();
    }
}
