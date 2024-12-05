package com.example.tap2024proyecto.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Simulador extends Stage {

    private TableView<Tarea> tablaTareas;
    private Button btnAgregarTarea, btnIniciarDetener;
    private ProgressBar progressBar;
    private boolean simuladorActivo = false;
    private Timeline timeline;
    private ArrayList<Tarea> listaTareas;
    private Random random = new Random();
    private ImageView impresoraImageView;

    public Simulador() {
        listaTareas = new ArrayList<>();
        this.setTitle("Simulador de Impresión");
        this.setWidth(600);
        this.setHeight(400);
        inicializarUI();
        this.show();
    }

    private void inicializarUI() {

        tablaTareas = new TableView<>();
        TableColumn<Tarea, String> colArchivo = new TableColumn<>("No. Archivo");
        colArchivo.setCellValueFactory(cellData -> cellData.getValue().noArchivoProperty());

        TableColumn<Tarea, String> colNombreArchivo = new TableColumn<>("Nombre de archivo");
        colNombreArchivo.setCellValueFactory(cellData -> cellData.getValue().nombreArchivoProperty());

        TableColumn<Tarea, String> colHojas = new TableColumn<>("Hojas a imprimir");
        colHojas.setCellValueFactory(cellData -> cellData.getValue().hojasProperty());

        TableColumn<Tarea, String> colHoraAcceso = new TableColumn<>("Hora de acceso");
        colHoraAcceso.setCellValueFactory(cellData -> cellData.getValue().horaAccesoProperty());

        TableColumn<Tarea, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        tablaTareas.getColumns().addAll(colArchivo, colNombreArchivo, colHojas, colHoraAcceso, colEstado);


        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(500);
        progressBar.getStyleClass().add("progress-bar");


        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.getStyleClass().add("button-simulador");
        btnAgregarTarea.setOnAction(e -> agregarTarea());

        btnIniciarDetener = new Button("Iniciar Simulador");
        btnIniciarDetener.getStyleClass().add("button-simulador");
        btnIniciarDetener.setOnAction(e -> toggleSimulador());


        Image impresoraImage = new Image(getClass().getResourceAsStream("/Images/Impresora.jpg"));
        impresoraImageView = new ImageView(impresoraImage);
        impresoraImageView.getStyleClass().add("image-impresora");
        impresoraImageView.setFitWidth(100);
        impresoraImageView.setFitHeight(100);


        VBox vBox = new VBox(10, tablaTareas, btnAgregarTarea, btnIniciarDetener, progressBar, impresoraImageView);
        vBox.getStyleClass().add("vbox-simulador");
        vBox.setPadding(new javafx.geometry.Insets(10));


        Scene escena = new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/styles/Simulador.css").toExternalForm());
        this.setScene(escena);
    }

    private void agregarTarea() {
        String noArchivo = String.valueOf(random.nextInt(10000));
        String nombreArchivo = generarNombreArchivo();
        int hojas = random.nextInt(20) + 1; // Número aleatorio de hojas entre 1 y 20
        String horaAcceso = new SimpleDateFormat("HH:mm:ss").format(new Date());


        Tarea nuevaTarea = new Tarea(noArchivo, nombreArchivo, hojas, horaAcceso);
        listaTareas.add(nuevaTarea);
        tablaTareas.getItems().add(nuevaTarea);
    }

    private String generarNombreArchivo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaHora = sdf.format(new Date());
        String extension = random.nextBoolean() ? ".pdf" : ".docx";
        return "Archivo_" + fechaHora + extension;
    }

    private void toggleSimulador() {
        if (simuladorActivo) {
            detenerSimulador();
        } else {
            iniciarSimulador();
        }
    }

    private void iniciarSimulador() {
        simuladorActivo = true;
        btnIniciarDetener.setText("Detener Simulador");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> ejecutarSimulacion()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void detenerSimulador() {
        simuladorActivo = false;
        btnIniciarDetener.setText("Iniciar Simulador");
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void ejecutarSimulacion() {
        if (!listaTareas.isEmpty()) {
            Tarea tarea = listaTareas.get(0);
            int hojas = tarea.getHojas();
            double progreso = progressBar.getProgress() + (1.0 / hojas);


            tarea.setEstado("Imprimiendo...");


            if (progreso <= 1.0) {
                progressBar.setProgress(progreso);
            } else {
                progressBar.setProgress(1.0);
                listaTareas.remove(0);
                tablaTareas.getItems().remove(0);
                progressBar.setProgress(0);
                tarea.setEstado("Impresa");
                mostrarVentanaEmergente(tarea);
            }
        }
    }

    public void mostrarVentanaEmergente(Tarea tarea) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Simulación Terminada");
            alert.setHeaderText("Tarea: " + tarea.getNombreArchivo());
            alert.setContentText("La tarea ha sido impresa correctamente.");

            alert.showAndWait();
        });
    }

    public static class Tarea {
        private final StringProperty noArchivo;
        private final StringProperty nombreArchivo;
        private final StringProperty hojas;
        private final StringProperty horaAcceso;
        private final StringProperty estado;

        public Tarea(String noArchivo, String nombreArchivo, int hojas, String horaAcceso) {
            this.noArchivo = new SimpleStringProperty(noArchivo);
            this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
            this.hojas = new SimpleStringProperty(String.valueOf(hojas));
            this.horaAcceso = new SimpleStringProperty(horaAcceso);
            this.estado = new SimpleStringProperty("En cola...");
        }


        public StringProperty noArchivoProperty() {
            return noArchivo;
        }

        public StringProperty nombreArchivoProperty() {
            return nombreArchivo;
        }

        public StringProperty hojasProperty() {
            return hojas;
        }

        public StringProperty horaAccesoProperty() {
            return horaAcceso;
        }

        public StringProperty estadoProperty() {
            return estado;
        }


        public int getHojas() {
            return Integer.parseInt(hojas.get());
        }

        public String getNombreArchivo() {
            return nombreArchivo.get();
        }

        public String getEstado() {
            return estado.get();
        }

        public void setEstado(String estado) {
            this.estado.set(estado);
        }
    }
}
