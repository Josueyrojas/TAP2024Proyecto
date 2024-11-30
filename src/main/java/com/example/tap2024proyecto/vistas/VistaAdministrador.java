package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaAdministrador extends Stage {
    private Scene scene;
    private BorderPane contenidoPrincipal; // Área principal donde se cargará el contenido dinámico

    public VistaAdministrador() {
        CrearUI();
        this.setTitle("Admin Dashboard");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        // Menú lateral
        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(10));
        menuLateral.setStyle("-fx-background-color: gray;");

        Label lblMenu = new Label("Menú de Administración");
        lblMenu.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-font-weight: bold;");

        // Botones de navegación
        Button btnCRUDAlbum = new Button("Gestionar Álbumes");
        Button btnCRUDCliente = new Button("Gestionar Clientes");
        Button btnEstadisticas = new Button("Ver Estadísticas");
        Button btnReportes = new Button("Generar Reportes");
        Button btnGeneros = new Button("Gestionar Géneros");
        Button btnCanciones = new Button("Gestionar Canciones"); // Botón nuevo para gestionar canciones
        Button btnArtistas = new Button("Gestionar Artistas"); // Botón para gestionar artistas
        Button btnCerrarSesion = new Button("Cerrar Sesión");

        // Estilo común para los botones (excepto Cerrar Sesión)
        String estiloBoton = "-fx-background-color: green; -fx-text-fill: white; -fx-cursor: hand;";
        btnCRUDAlbum.setStyle(estiloBoton);
        btnCRUDCliente.setStyle(estiloBoton);
        btnEstadisticas.setStyle(estiloBoton);
        btnReportes.setStyle(estiloBoton);
        btnGeneros.setStyle(estiloBoton);
        btnCanciones.setStyle(estiloBoton); // Aplicamos el estilo común al nuevo botón
        btnArtistas.setStyle(estiloBoton); // Estilo para el botón de artistas

        // Estilo específico para el botón "Cerrar Sesión" (color rojo)
        btnCerrarSesion.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");

        // Agregar los botones al menú lateral
        menuLateral.getChildren().addAll(lblMenu, btnCRUDAlbum, btnCRUDCliente, btnEstadisticas, btnReportes, btnGeneros, btnCanciones, btnArtistas, btnCerrarSesion);

        // Área principal (contenido)
        contenidoPrincipal = new BorderPane();
        Label lblBienvenida = new Label("Bienvenido al Panel de Administración");
        lblBienvenida.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");
        contenidoPrincipal.setCenter(lblBienvenida);

        // Layout principal
        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(contenidoPrincipal);

        // Escena
        scene = new Scene(layoutPrincipal, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());

        // Acciones de los botones
        btnCRUDAlbum.setOnAction(e -> cargarListaAlbumes()); // Carga la lista de álbumes
        btnCRUDCliente.setOnAction(e -> cargarListaClientes()); // Carga la lista de clientes
        btnEstadisticas.setOnAction(e -> new VistaEstadisticas()); // Placeholder para estadísticas
        btnReportes.setOnAction(e -> contenidoPrincipal.setCenter(new Label("Generación de Reportes"))); // Placeholder para reportes
        btnGeneros.setOnAction(e -> new ListaGeneros());
        btnCanciones.setOnAction(e -> cargarListaCanciones()); // Acción para el botón de gestionar canciones
        btnArtistas.setOnAction(e -> cargarListaArtistas()); // Acción para gestionar artistas
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }

    /**
     * Cargar la vista de lista de álbumes en el área principal.
     */
    private void cargarListaAlbumes() {
        ListaAlbum listaAlbumes = new ListaAlbum(); // Crea una nueva instancia de ListaAlbum
        contenidoPrincipal.setCenter(listaAlbumes.getContenido()); // Establece la lista en el área principal
    }

    /**
     * Cargar la vista de lista de clientes en el área principal.
     */
    private void cargarListaClientes() {
        ListaClientes listaClientes = new ListaClientes(); // Crea una nueva instancia de ListaClientes
        contenidoPrincipal.setCenter(listaClientes.getContenido()); // Establece la lista en el área principal
    }

    /**
     * Cargar la vista de lista de canciones en el área principal.
     */
    private void cargarListaCanciones() {
        ListaCancion listaCanciones = new ListaCancion(); // Crea una nueva instancia de ListaCancion
        contenidoPrincipal.setCenter(listaCanciones.getContenido()); // Establece la lista de canciones en el área principal
    }

    /**
     * Cargar la vista de lista de artistas en el área principal.
     */
    private void cargarListaArtistas() {
        ListaArtistas listaArtistas = new ListaArtistas(); // Crea una nueva instancia de ListaArtistas
        contenidoPrincipal.setCenter(listaArtistas.getContenido()); // Establece la lista de artistas en el área principal
    }

    private void cerrarSesion() {
        new Login(); // Regresa a la pantalla de login
        this.close(); // Cierra la vista del administrador
    }
}
