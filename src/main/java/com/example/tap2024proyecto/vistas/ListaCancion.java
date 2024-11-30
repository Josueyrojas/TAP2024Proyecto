package com.example.tap2024proyecto.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.CancionDAO;
import com.example.tap2024proyecto.components.ButtonCellCancion;

public class ListaCancion extends Stage {
    private TableView<CancionDAO> tblCancion;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaCancion() {
        crearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        tlbMenu = new ToolBar();

        // Botón para agregar una nueva canción
        Button btnAddCancion = new Button("Agregar Canción");
        btnAddCancion.setOnAction(actionEvent -> new FormCancion(tblCancion, null)); // null porque estamos agregando una nueva canción
        tlbMenu.getItems().add(btnAddCancion);

        tblCancion = new TableView<>();
        crearTabla();

        vBox = new VBox(tlbMenu, tblCancion);
        escena = new Scene(vBox, 700, 400);
    }

    private void crearTabla() {
        // Columna para mostrar el título de la canción
        TableColumn<CancionDAO, String> tbcTitulo = new TableColumn<>("Título");
        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloCan"));

        // Columna para editar una canción (con ButtonCellCancion)
        TableColumn<CancionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellCancion("Editar"));

        // Columna para eliminar una canción (con ButtonCellCancion)
        TableColumn<CancionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellCancion("Eliminar"));

        // Añadimos las columnas a la tabla
        tblCancion.getColumns().addAll(tbcTitulo, tbcEditar, tbcEliminar);

        // Cargamos las canciones desde la base de datos
        tblCancion.setItems(new CancionDAO().SELECTALL());
    }

    // Método para obtener el VBox, que es el contenedor principal
    public VBox getContenido() {
        return vBox;
    }
}
