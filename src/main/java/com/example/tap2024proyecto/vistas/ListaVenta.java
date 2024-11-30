package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.tap2024proyecto.models.VentasDAO;
import com.example.tap2024proyecto.components.ButtonCell;

public class ListaVenta extends Stage {

    private TableView<VentasDAO> tblVenta;
    private ToolBar tlbMenu;
    private VBox vBoxTabla;
    private VBox vBoxGraficas;
    private HBox hBoxPrincipal;
    private Scene escena;

    public ListaVenta() {
        crearUI();
        this.setTitle("Lista de Ventas y Estadísticas");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        // Crear el menú de herramientas
        tlbMenu = new ToolBar();
        Button btnAddVenta = new Button("Agregar Venta");
        btnAddVenta.setOnAction(actionEvent -> new FormVenta(tblVenta, null));
        tlbMenu.getItems().add(btnAddVenta);

        // Crear la tabla de ventas
        tblVenta = new TableView<>();
        crearTabla();

        // Sección de la tabla
        vBoxTabla = new VBox(tlbMenu, tblVenta);
        vBoxTabla.setSpacing(10);

        // Crear las gráficas
        vBoxGraficas = crearGraficas();

        // Contenedor principal
        hBoxPrincipal = new HBox(vBoxTabla, vBoxGraficas);
        hBoxPrincipal.setSpacing(20);

        // Escena
        escena = new Scene(hBoxPrincipal, 1000, 600);
    }

    private void crearTabla() {
        TableColumn<VentasDAO, String> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        TableColumn<VentasDAO, String> tbcFecha = new TableColumn<>("Fecha de Venta");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));

        TableColumn<VentasDAO, String> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));

        TableColumn<VentasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCell<>("Editar", tblVenta));

        TableColumn<VentasDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCell<>("Eliminar", tblVenta));

        tblVenta.getColumns().addAll(tbcCliente, tbcFecha, tbcTotal, tbcEditar, tbcEliminar);
        tblVenta.setItems(new VentasDAO().SELECTALL());
    }

    private VBox crearGraficas() {
        // DAO para obtener datos de las estadísticas
        VentasDAO dao = new VentasDAO();

        // Gráfica de Ventas Mensuales
        BarChart<String, Number> graficaVentasMensuales = new BarChart<>(new CategoryAxis(), new NumberAxis());
        graficaVentasMensuales.setTitle("Ventas Mensuales");
        var serieVentasMensuales = new BarChart.Series<String, Number>();
        serieVentasMensuales.setName("Ventas por Mes");
        dao.obtenerVentasMensuales().forEach(vm ->
                serieVentasMensuales.getData().add(new BarChart.Data<>(vm.getMes(), vm.getCantidadVentas()))
        );
        graficaVentasMensuales.getData().add(serieVentasMensuales);

        // Gráfica de Artistas con Más Ventas
        PieChart graficaArtistas = new PieChart();
        graficaArtistas.setTitle("Artistas con Más Ventas");
        dao.obtenerArtistasConMasVentas().forEach(av ->
                graficaArtistas.getData().add(new PieChart.Data(av.getArtista(), av.getVentas()))
        );

        // Gráfica de Canciones Más Vendidas
        BarChart<String, Number> graficaCanciones = new BarChart<>(new CategoryAxis(), new NumberAxis());
        graficaCanciones.setTitle("Canciones Más Vendidas");
        var serieCanciones = new BarChart.Series<String, Number>();
        serieCanciones.setName("Canciones por Ventas");
        dao.obtenerCancionesMasVendidas().forEach(cv ->
                serieCanciones.getData().add(new BarChart.Data<>(cv.getCancion(), cv.getVentas()))
        );
        graficaCanciones.getData().add(serieCanciones);

        // Contenedor de gráficas
        VBox vBoxGraficas = new VBox(20, graficaVentasMensuales, graficaArtistas, graficaCanciones);
        vBoxGraficas.setSpacing(20);

        return vBoxGraficas;
    }
}
