package com.example.tap2024proyecto;

import com.example.tap2024proyecto.models.Conexion;
import com.example.tap2024proyecto.vistas.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    private BorderPane bdpPrincipal;
    private MenuBar menBar;
    private Menu menCompetencia1, menCompetencia2, menSalir;
    private MenuItem mitCalculadora, mitLoteria, mitSpotify, mitBuscaminas, mitPista;

    public void CrearUI() {
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new Calculadora());

        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(actionEvent -> new Loteria());

        mitSpotify = new MenuItem("Spotify");
        mitSpotify.setOnAction(actionEvent -> new Login());

        mitBuscaminas = new MenuItem("Buscaminas");
        mitBuscaminas.setOnAction(actionEvent -> new Buscaminas());

        mitPista = new MenuItem("Pista Hilos");
        mitPista.setOnAction(actionEvent -> new Pista());

        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria, mitBuscaminas);

        menCompetencia2 = new Menu("Competencia 2");
        menCompetencia2.getItems().addAll(mitSpotify, mitPista);

        menBar = new MenuBar(menCompetencia1, menCompetencia2);

        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(menBar);
    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        Scene scene = new Scene(bdpPrincipal, 320, 240);
        scene.getStylesheets().add(getClass().getResource("/Styles/main.css").toExternalForm());
        stage.setTitle("Topicos Avanzados de Programacion :)");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // Establecer la conexión a la base de datos
        Conexion.CrearConexion();
    }

    public static void main(String... args) {
        launch();
    }
}
