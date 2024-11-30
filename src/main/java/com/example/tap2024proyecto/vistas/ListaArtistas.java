package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.ArtistaDAO;
import com.example.tap2024proyecto.components.ButtonCellArtista;

public class ListaArtistas extends Stage {
    private TableView<ArtistaDAO> tblArtista;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaArtistas() {
        crearUI();
        this.setTitle("Lista de Artistas");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        // Barra de herramientas con un botón para agregar un nuevo artista
        tlbMenu = new ToolBar();
        Button btnAddArtista = new Button("Agregar Artista");
        btnAddArtista.setOnAction(actionEvent -> new FormArtista(tblArtista, null)); // Null porque estamos agregando un nuevo artista
        tlbMenu.getItems().add(btnAddArtista);

        // Crear la tabla de artistas
        tblArtista = new TableView<>();
        crearTabla();

        // Layout contenedor
        vBox = new VBox(tlbMenu, tblArtista);
        escena = new Scene(vBox, 700, 400);
    }

    private void crearTabla() {
        // Columna para mostrar el nombre del artista
        TableColumn<ArtistaDAO, String> tbcNombre = new TableColumn<>("Nombre del Artista");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArt"));

        // Columna para editar un artista, con ButtonCellArtista
        TableColumn<ArtistaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellArtista("Editar", tblArtista));

        // Columna para eliminar un artista, con ButtonCellArtista
        TableColumn<ArtistaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellArtista("Eliminar", tblArtista));

        // Añadir las columnas a la tabla
        tblArtista.getColumns().addAll(tbcNombre, tbcEditar, tbcEliminar);

        // Cargar los artistas desde la base de datos
        tblArtista.setItems(new ArtistaDAO().SELECTALL());
    }

    // Método para obtener el contenido de la lista de artistas
    public VBox getContenido() {
        return vBox;  // Devuelve el VBox que contiene la tabla de artistas
    }
}
