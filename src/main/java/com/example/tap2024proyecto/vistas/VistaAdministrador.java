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
        String estiloBoton = "-fx-background-color: limegreen; -fx-text-fill: white; -fx-cursor: hand;";
        btnCRUDAlbum.setStyle(estiloBoton);
        btnCRUDCliente.setStyle(estiloBoton);
        btnEstadisticas.setStyle(estiloBoton);
        btnReportes.setStyle(estiloBoton);
        btnGeneros.setStyle(estiloBoton);
        btnCanciones.setStyle(estiloBoton);
        btnArtistas.setStyle(estiloBoton);


        btnCerrarSesion.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");


        menuLateral.getChildren().addAll(lblMenu, btnCRUDAlbum, btnCRUDCliente, btnGeneros,btnEstadisticas,btnReportes, btnCanciones, btnArtistas, btnCerrarSesion);


        contenidoPrincipal = new BorderPane();
        Label lblBienvenida = new Label("Bienvenido al Panel de Administración");
        lblBienvenida.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");
        contenidoPrincipal.setCenter(lblBienvenida);


        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(contenidoPrincipal);


        scene = new Scene(layoutPrincipal, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());


        btnCRUDAlbum.setOnAction(e -> cargarListaAlbumes());
        btnCRUDCliente.setOnAction(e -> cargarListaClientes());
        btnEstadisticas.setOnAction(e -> new VistaEstadisticas());
        btnReportes.setOnAction(e -> contenidoPrincipal.setCenter(new Label("Generación de Reportes"))); // Placeholder para reportes
        btnGeneros.setOnAction(e -> cargarListaGeneros());
        btnCanciones.setOnAction(e -> cargarListaCanciones());
        btnArtistas.setOnAction(e -> cargarListaArtistas());
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }


    private void cargarListaAlbumes() {
        ListaAlbum listaAlbumes = new ListaAlbum(); // Crea una nueva instancia de ListaAlbum
        contenidoPrincipal.setCenter(listaAlbumes.getContenido()); // Establece la lista en el área principal
    }


    private void cargarListaClientes() {
        ListaClientes listaClientes = new ListaClientes(); // Crea una nueva instancia de ListaClientes
        contenidoPrincipal.setCenter(listaClientes.getContenido()); // Establece la lista en el área principal
    }
    private void cargarListaGeneros() {
        ListaGeneros listaGeneros = new ListaGeneros(); // Crea una nueva instancia de ListaCancion
        contenidoPrincipal.setCenter(listaGeneros.getContenido()); // Establece la lista de canciones en el área principal
    }


    private void cargarListaCanciones() {
        ListaCancion listaCanciones = new ListaCancion(); // Crea una nueva instancia de ListaCancion
        contenidoPrincipal.setCenter(listaCanciones.getContenido()); // Establece la lista de canciones en el área principal
    }

    private void cargarListaArtistas() {
        ListaArtistas listaArtistas = new ListaArtistas(); // Crea una nueva instancia de ListaArtistas
        contenidoPrincipal.setCenter(listaArtistas.getContenido()); // Establece la lista de artistas en el área principal
    }

    private void cerrarSesion() {
        new Login();
        this.close();
    }
}
