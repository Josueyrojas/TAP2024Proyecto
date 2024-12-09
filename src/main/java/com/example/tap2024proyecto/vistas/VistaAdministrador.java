package com.example.tap2024proyecto.vistas;

import com.example.tap2024proyecto.components.PDFGenerator;
import com.example.tap2024proyecto.models.AlbumDAO;
import com.example.tap2024proyecto.models.ClienteDAO;
import com.example.tap2024proyecto.models.GeneroDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class VistaAdministrador extends Stage {
    private Scene scene;
    private BorderPane contenidoPrincipal;

    public VistaAdministrador() {
        CrearUI();
        this.setTitle("Admin Dashboard");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(10));
        menuLateral.setStyle("-fx-background-color: gray;");

        Label lblMenu = new Label("Menú de Administración");
        lblMenu.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnCRUDAlbum = new Button("Gestionar Álbumes");
        Button btnCRUDCliente = new Button("Gestionar Clientes");
        Button btnEstadisticas = new Button("Ver Estadísticas");
        Button btnReportes = new Button("Generar Reportes");
        Button btnGeneros = new Button("Gestionar Géneros");
        Button btnCanciones = new Button("Gestionar Canciones"); // Botón nuevo para gestionar canciones
        Button btnArtistas = new Button("Gestionar Artistas"); // Botón para gestionar artistas
        Button btnCerrarSesion = new Button("Cerrar Sesión");

        String estiloBoton = "-fx-background-color: limegreen; -fx-text-fill: white; -fx-cursor: hand;";
        btnCRUDAlbum.setStyle(estiloBoton);
        btnCRUDCliente.setStyle(estiloBoton);
        btnEstadisticas.setStyle(estiloBoton);
        btnReportes.setStyle(estiloBoton);
        btnGeneros.setStyle(estiloBoton);
        btnCanciones.setStyle(estiloBoton);
        btnArtistas.setStyle(estiloBoton);

        btnCerrarSesion.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");

        menuLateral.getChildren().addAll(lblMenu, btnCRUDAlbum, btnCRUDCliente, btnGeneros, btnEstadisticas, btnReportes, btnCanciones, btnArtistas, btnCerrarSesion);

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
        btnReportes.setOnAction(e -> generarReporteAdministrador());
        btnGeneros.setOnAction(e -> cargarListaGeneros());
        btnCanciones.setOnAction(e -> cargarListaCanciones());
        btnArtistas.setOnAction(e -> cargarListaArtistas());
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }

    private void cargarListaAlbumes() {
        ListaAlbum listaAlbumes = new ListaAlbum();
        contenidoPrincipal.setCenter(listaAlbumes.getContenido());
    }

    private void cargarListaClientes() {
        ListaClientes listaClientes = new ListaClientes();
        contenidoPrincipal.setCenter(listaClientes.getContenido());
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

    private void generarReporteAdministrador() {
        StringBuilder contenido = new StringBuilder("Reporte de Administración\n\n");

        // Agregar los álbumes al reporte
        AlbumDAO albumDAO = new AlbumDAO();
        contenido.append("Álbumes:\n");
        albumDAO.obtenerTodos().forEach(album ->
                contenido.append("Id Album: ").append(album.getIdAlbum())
                        .append(" Nombre: ").append(album.getTituloAlbum())
                        .append("\n")
        );
        contenido.append("\n");

        // Agregar los clientes al reporte
        ClienteDAO clienteDAO = new ClienteDAO();
        contenido.append("Clientes:\n");
        clienteDAO.obtenerTodos().forEach(cliente ->
                contenido.append("Nombre: ").append(cliente.getNomCte())
                        .append(", Correo: ").append(cliente.getEmailCte())
                        .append("\n")
        );
        contenido.append("\n");

        // Agregar los géneros al reporte
        GeneroDAO generoDAO = new GeneroDAO();
        contenido.append("Géneros:\n");
        generoDAO.obtenerTodos().forEach(genero ->
                contenido.append("Nombre: ").append(genero.getNombreGenero())
                        .append("\n")
        );

        // Llamar a la función para generar el PDF, pasando 'this' como el 'Window'
        PDFGenerator.generarReporteAdministrador(contenido.toString(), this);  // 'this' es un Stage (VistaAdministrador)
    }

}