package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.models.CancionDAO;
import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.models.VentasDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label lblTitulo = new Label("Comprar Canciones o Álbumes");
        lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        TabPane tabPane = new TabPane();

        // Tab Álbumes
        Tab tabAlbumes = new Tab("Álbumes");
        tabAlbumes.setClosable(false);

        TableView<AlbumDAO> tblAlbumes = new TableView<>();
        tblAlbumes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<AlbumDAO, Integer> colIdAlbum = new TableColumn<>("ID");
        colIdAlbum.setCellValueFactory(new PropertyValueFactory<>("idAlbum"));

        TableColumn<AlbumDAO, String> colTituloAlbum = new TableColumn<>("Título");
        colTituloAlbum.setCellValueFactory(new PropertyValueFactory<>("tituloAlbum"));

        TableColumn<AlbumDAO, String> colFechaAlbum = new TableColumn<>("Fecha");
        colFechaAlbum.setCellValueFactory(new PropertyValueFactory<>("fechaAlbum"));

        TableColumn<AlbumDAO, Double> colCostoAlbum = new TableColumn<>("Costo");
        colCostoAlbum.setCellValueFactory(new PropertyValueFactory<>("costoAlbum"));

        // Columna para mostrar la imagen del álbum
        TableColumn<AlbumDAO, String> colImagenAlbum = new TableColumn<>("Imagen");
        colImagenAlbum.setCellValueFactory(new PropertyValueFactory<>("imagenAlbum"));
        colImagenAlbum.setCellFactory(param -> new TableCell<AlbumDAO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView();
                    try {
                        // Cargar la imagen desde la ruta proporcionada en la base de datos
                        Image image = new Image("file:" + item); // Asume que la ruta es local
                        imageView.setImage(image);
                        imageView.setFitWidth(100); // Ajusta el tamaño de la imagen
                        imageView.setFitHeight(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setGraphic(imageView);
                }
            }
        });

        tblAlbumes.getColumns().addAll(colIdAlbum, colTituloAlbum, colFechaAlbum, colCostoAlbum, colImagenAlbum);

        AlbumDAO albumDAO = new AlbumDAO();
        tblAlbumes.setItems(albumDAO.SELECTALL());

        tabAlbumes.setContent(tblAlbumes);


        // Tab Canciones
        Tab tabCanciones = new Tab("Canciones");
        tabCanciones.setClosable(false);

        TableView<CancionDAO> tblCanciones = new TableView<>();
        tblCanciones.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<CancionDAO, Integer> colIdCancion = new TableColumn<>("ID");
        colIdCancion.setCellValueFactory(new PropertyValueFactory<>("idCancion"));

        TableColumn<CancionDAO, String> colTituloCancion = new TableColumn<>("Título");
        colTituloCancion.setCellValueFactory(new PropertyValueFactory<>("tituloCan"));

        TableColumn<CancionDAO, Double> colCostoCancion = new TableColumn<>("Costo");
        colCostoCancion.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

        tblCanciones.getColumns().addAll(colIdCancion, colTituloCancion, colCostoCancion);

        CancionDAO cancionDAO = new CancionDAO();
        tblCanciones.setItems(cancionDAO.SELECTALL());

        tabCanciones.setContent(tblCanciones);

        tabPane.getTabs().addAll(tabAlbumes, tabCanciones);

        Button btnComprar = new Button("Comprar");
        btnComprar.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-cursor: hand;");
        btnComprar.setOnAction(e -> {
            // Obtener selección de álbumes y canciones
            ObservableList<AlbumDAO> albumesSeleccionados = tblAlbumes.getSelectionModel().getSelectedItems();
            ObservableList<CancionDAO> cancionesSeleccionadas = tblCanciones.getSelectionModel().getSelectedItems();

            if (albumesSeleccionados.isEmpty() && cancionesSeleccionadas.isEmpty()) {
                mostrarAlerta("Sin selección", "No has seleccionado ningún elemento para comprar.");
                return;
            }

            // Construimos una lista de ids de canciones finales
            ObservableList<Integer> idsCancionesCompra = FXCollections.observableArrayList();
            double totalVenta = 0.0;

            // Agregar canciones de álbumes seleccionados
            for (AlbumDAO albumSel : albumesSeleccionados) {
                // Obtener las canciones del álbum
                ObservableList<CancionDAO> cancionesDeAlbum = cancionDAO.obtenerCancionesDeAlbum(albumSel.getIdAlbum());
                if (cancionesDeAlbum.isEmpty()) {
                    // Si el álbum no tiene canciones, podríamos tratarlo como sin costo o ignorarlo
                    // Aquí asumimos que un álbum siempre tendrá al menos una canción.
                    continue;
                }
                // Sumar el costo de todas las canciones del álbum
                for (CancionDAO cancionAlbum : cancionesDeAlbum) {
                    idsCancionesCompra.add(cancionAlbum.getIdCancion());
                    totalVenta += cancionAlbum.getCostoCancion();
                }
            }

            // Agregar las canciones seleccionadas directamente
            for (CancionDAO cancionSel : cancionesSeleccionadas) {
                idsCancionesCompra.add(cancionSel.getIdCancion());
                totalVenta += cancionSel.getCostoCancion();
            }

            if (idsCancionesCompra.isEmpty()) {
                mostrarAlerta("Sin Canciones", "No se encontraron canciones para el ítem seleccionado.");
                return;
            }

            // Crear el objeto venta
            VentasDAO venta = new VentasDAO();
            venta.setIdCliente(clienteActual.getIdCte()); // ID del cliente logueado
            venta.setFechaVenta(java.time.LocalDate.now().toString()); // Fecha actual
            venta.setTotalVenta(totalVenta);

            // Convertir a array de int
            int[] idsProductos = idsCancionesCompra.stream().mapToInt(Integer::intValue).toArray();

            if (venta.INSERT(idsProductos) > 0) {
                mostrarAlerta("Compra realizada", "Has comprado los artículos seleccionados.");
            } else {
                mostrarAlerta("Error", "Ocurrió un error al realizar la compra.");
            }
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
            // Cuando se cierre el formulario, podemos actualizar los datos en la vista:
            form.setOnHidden(evt -> {
                // Se asume que el clienteActual ya fue actualizado en la BD
                // Recargamos datos desde la BD si se desea o simplemente refrescamos el contenido
                // Si queremos volver a cargar la info actualizada:
                // clienteActual = ClienteDAO.obtenerClientePorEmailYPassword(clienteActual.getEmailCte(), clienteActual.getPassword());
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