package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.VentasDAO;

public class VistaCliente extends Stage {
    private Scene scene;
    private BorderPane contenidoPrincipal;

    public VistaCliente() {
        crearUI();
        this.setTitle("Cliente - Panel de Usuario");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {
        // Menú lateral
        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(10));
        menuLateral.setStyle("-fx-background-color: #2A2A2A;");

        Label lblMenu = new Label("Menú del Cliente");
        lblMenu.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnComprar = new Button("Comprar Canciones/Álbumes");
        Button btnHistorial = new Button("Historial de Compras");
        Button btnDatosPersonales = new Button("Mis Datos");
        Button btnCerrarSesion = new Button("Cerrar Sesion");

        // Estilo para los botones
        btnComprar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnHistorial.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnDatosPersonales.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnCerrarSesion.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");

        menuLateral.getChildren().addAll(lblMenu, btnComprar, btnHistorial, btnDatosPersonales, btnCerrarSesion);

        // Área principal (contenido dinámico)
        contenidoPrincipal = new BorderPane();
        Label lblBienvenida = new Label("Bienvenido, selecciona una opción del menú.");
        lblBienvenida.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");
        contenidoPrincipal.setCenter(lblBienvenida);

        // Layout principal
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(contenidoPrincipal);

        // Escena
        scene = new Scene(layoutPrincipal, 1000, 600);

        // Acciones de los botones
        btnComprar.setOnAction(e -> cargarVistaComprar());
        btnHistorial.setOnAction(e -> cargarHistorialCompras());
        btnDatosPersonales.setOnAction(e -> cargarDatosPersonales());
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }

    private void cerrarSesion() {
        new Login(); // Regresa a la pantalla de login
        this.close(); // Cierra la vista del administrador

    }

    private void cargarVistaComprar() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label lblTitulo = new Label("Comprar Canciones o Álbumes");
        lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // Aquí se pueden agregar elementos como listas de canciones/álbumes disponibles para compra
        // Por ahora es solo un placeholder
        ListView<String> listaCompra = new ListView<>();
        listaCompra.getItems().addAll("Canción 1", "Canción 2", "Álbum 1", "Álbum 2");

        Button btnComprar = new Button("Comprar");
        btnComprar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnComprar.setOnAction(e -> mostrarAlerta("Compra realizada", "Has comprado los artículos seleccionados."));

        contenido.getChildren().addAll(lblTitulo, listaCompra, btnComprar);
        contenidoPrincipal.setCenter(contenido);
    }

    private void cargarHistorialCompras() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label lblTitulo = new Label("Historial de Compras");
        lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        TableView<VentasDAO> tblHistorial = new TableView<>();

        TableColumn<VentasDAO, Integer> colIdVenta = new TableColumn<>("ID Venta");
        colIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));

        TableColumn<VentasDAO, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));

        TableColumn<VentasDAO, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));

        tblHistorial.getColumns().addAll(colIdVenta, colFecha, colTotal);

        // Cargar datos en la tabla
        tblHistorial.setItems(new VentasDAO().SELECTALL());

        contenido.getChildren().addAll(lblTitulo, tblHistorial);
        contenidoPrincipal.setCenter(contenido);
    }

    private void cargarDatosPersonales() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label lblTitulo = new Label("Mis Datos Personales");
        lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // Mostrar datos personales (placeholders por ahora)
        Label lblNombre = new Label("Nombre: Juan Pérez");
        Label lblCorreo = new Label("Correo: juan.perez@example.com");
        Label lblTelefono = new Label("Teléfono: 123-456-7890");

        Button btnEditar = new Button("Editar Datos");
        btnEditar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnEditar.setOnAction(e -> mostrarAlerta("Editar Datos", "Funcionalidad para editar datos en construcción."));

        contenido.getChildren().addAll(lblTitulo, lblNombre, lblCorreo, lblTelefono, btnEditar);
        contenidoPrincipal.setCenter(contenido);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}