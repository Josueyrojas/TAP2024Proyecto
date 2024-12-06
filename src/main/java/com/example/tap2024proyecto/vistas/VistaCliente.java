package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.models.CancionDAO;
import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.models.VentasDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaCliente extends Stage {
    private Scene scene;
    private BorderPane contenidoPrincipal;
    private ClienteDAO clienteActual; // Cliente logueado

    public VistaCliente(ClienteDAO cliente) {
        this.clienteActual = cliente;
        crearUI();
        this.setTitle("Cliente - Panel de Usuario");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {
        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(10));
        menuLateral.setStyle("-fx-background-color: #2A2A2A;");

        Label lblMenu = new Label("Menú del Cliente");
        lblMenu.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnComprar = new Button("Comprar Canciones/Álbumes");
        Button btnHistorial = new Button("Historial de Compras");
        Button btnDatosPersonales = new Button("Mis Datos");
        Button btnCerrarSesion = new Button("Cerrar Sesion");

        btnComprar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnHistorial.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnDatosPersonales.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnCerrarSesion.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");

        menuLateral.getChildren().addAll(lblMenu, btnComprar, btnHistorial, btnDatosPersonales, btnCerrarSesion);

        contenidoPrincipal = new BorderPane();
        Label lblBienvenida = new Label("Bienvenido, selecciona una opción del menú.");
        lblBienvenida.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");
        contenidoPrincipal.setCenter(lblBienvenida);

        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(contenidoPrincipal);

        scene = new Scene(layoutPrincipal, 1000, 600);

        btnComprar.setOnAction(e -> cargarVistaComprar());
        btnHistorial.setOnAction(e -> cargarHistorialCompras());
        btnDatosPersonales.setOnAction(e -> cargarDatosPersonales());
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }

    private void cerrarSesion() {
        new Login(); // Regresa a la pantalla de login
        this.close();
    }

    private void cargarVistaComprar() {
        // Contenedor principal vertical
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label lblTitulo = new Label("Comprar Canciones o Álbumes");
        lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // Crear el TabPane
        TabPane tabPane = new TabPane();

        // Tab para Albums
        Tab tabAlbumes = new Tab("Álbumes");
        tabAlbumes.setClosable(false);

        // Crear tabla para Albums
        TableView<AlbumDAO> tblAlbumes = new TableView<>();
        TableColumn<AlbumDAO, Integer> colIdAlbum = new TableColumn<>("ID");
        colIdAlbum.setCellValueFactory(new PropertyValueFactory<>("idAlbum"));

        TableColumn<AlbumDAO, String> colTituloAlbum = new TableColumn<>("Título");
        colTituloAlbum.setCellValueFactory(new PropertyValueFactory<>("tituloAlbum"));

        TableColumn<AlbumDAO, String> colFechaAlbum = new TableColumn<>("Fecha");
        colFechaAlbum.setCellValueFactory(new PropertyValueFactory<>("fechaAlbum"));

        TableColumn<AlbumDAO, Double> colCostoAlbum = new TableColumn<>("Costo");
        colCostoAlbum.setCellValueFactory(new PropertyValueFactory<>("costoAlbum"));

        // Columna para mostrar las imágenes
        TableColumn<AlbumDAO, String> colImagenAlbum = new TableColumn<>("Imagen");
        colImagenAlbum.setCellValueFactory(new PropertyValueFactory<>("imagenAlbum"));

        colImagenAlbum.setCellFactory(col -> new TableCell<AlbumDAO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Crear un ImageView con la ruta de la imagen
                    Image image = new Image(item);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50); // Ajustar altura
                    imageView.setFitWidth(50);  // Ajustar ancho
                    setGraphic(imageView);     // Asignar el ImageView como gráfico de la celda
                }
            }
        });

        tblAlbumes.getColumns().addAll(colIdAlbum, colTituloAlbum, colFechaAlbum, colCostoAlbum, colImagenAlbum);

        // Cargar datos de álbumes
        AlbumDAO albumDAO = new AlbumDAO();
        tblAlbumes.setItems(albumDAO.SELECTALL());

        tabAlbumes.setContent(tblAlbumes);

        // Tab para Canciones
        Tab tabCanciones = new Tab("Canciones");
        tabCanciones.setClosable(false);

        // Crear tabla para Canciones
        TableView<CancionDAO> tblCanciones = new TableView<>();
        TableColumn<CancionDAO, Integer> colIdCancion = new TableColumn<>("ID");
        colIdCancion.setCellValueFactory(new PropertyValueFactory<>("idCancion"));

        TableColumn<CancionDAO, String> colTituloCancion = new TableColumn<>("Título");
        colTituloCancion.setCellValueFactory(new PropertyValueFactory<>("tituloCan"));

        TableColumn<CancionDAO, Double> colCostoCancion = new TableColumn<>("Costo");
        colCostoCancion.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

        tblCanciones.getColumns().addAll(colIdCancion, colTituloCancion, colCostoCancion);

        // Cargar datos de canciones
        CancionDAO cancionDAO = new CancionDAO();
        tblCanciones.setItems(cancionDAO.SELECTALL());

        tabCanciones.setContent(tblCanciones);

        // Agregar las tabs al tabPane
        tabPane.getTabs().addAll(tabAlbumes, tabCanciones);

        // Botón para comprar (al seleccionar ítems)
        Button btnComprar = new Button("Comprar");
        btnComprar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnComprar.setOnAction(e -> {
            mostrarAlerta("Compra", "Funcionalidad de compra en construcción.");
        });

        contenido.getChildren().addAll(lblTitulo, tabPane, btnComprar);
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

        // Cargar datos en la tabla filtrando por cliente actual
        tblHistorial.setItems(new VentasDAO().SELECTBYCLIENTE(clienteActual.getIdCte()));

        contenido.getChildren().addAll(lblTitulo, tblHistorial);
        contenidoPrincipal.setCenter(contenido);
    }

    private void cargarDatosPersonales() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label lblTitulo = new Label("Mis Datos Personales");
        lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label lblNombre = new Label("Nombre: " + clienteActual.getNomCte());
        Label lblCorreo = new Label("Correo: " + clienteActual.getEmailCte());
        Label lblTelefono = new Label("Teléfono: " + clienteActual.getTelCte());

        Button btnEditar = new Button("Editar Datos");
        btnEditar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnEditar.setOnAction(e -> {
            FormCliente form = new FormCliente(null, clienteActual);
            form.setOnHidden(evt -> {
                cargarDatosPersonales();
            });
        });

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
