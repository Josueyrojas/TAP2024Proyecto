package com.example.tap2024proyecto.vistas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Loteria extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar, btnFinalizar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg", "9.jpg", "10.jpg",
            "11.jpg", "12.jpg", "13.jpg", "14.jpg", "15.jpg", "16.jpg", "17.jpg", "18.jpg", "19.jpg",
            "20.jpg", "21.jpg", "22.jpg", "23.jpg", "24.jpg", "25.jpg", "26.jpg", "27.jpg", "28.jpg",
            "29.jpg", "30.jpg", "31.jpg", "32.jpg", "33.jpg", "34.jpg", "35.jpg", "36.jpg", "37.jpg",
            "38.jpg", "39.jpg", "40.jpg", "41.jpg", "42.jpg", "43.jpg", "44.jpg", "45.jpg", "46.jpg",
            "47.jpg", "48.jpg", "49.jpg", "50.jpg", "51.jpg", "52.jpg", "53.jpg", "54.jpg"};
    private Button[][] arBtnTab;
    private Timer timerGeneral, timerMazo;
    private int plantillaActual = 0;
    private List<Button[][]> plantillas;
    private TimerTask timerTaskGeneral, timerTaskMazo;
    private List<String> cartasDisponibles;
    private List<String> cartasMazo;
    private boolean juegoIniciado = false;
    private int segundosTotales = 0;

    private Panel pnlPrincipal;

    public Loteria() {
        CrearUI();
        this.setTitle("Loteria Mexicana :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/izquierda.png").toString()));
        imvAnt.setFitHeight(50);
        imvAnt.setFitWidth(50);
        imvSig = new ImageView(new Image(getClass().getResource("/images/derecha.png").toString()));
        imvSig.setFitWidth(50);
        imvSig.setFitHeight(50);

        gdpTablilla = new GridPane();
        CrearPlantillas();

        btnAnterior = new Button();
        btnAnterior.setGraphic(imvAnt);

        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);

        btnIniciar = new Button("Iniciar Juego");
        btnIniciar.getStyleClass().setAll("btn-sm", "btn-danger");

        btnFinalizar = new Button("Finalizar Juego");
        btnFinalizar.getStyleClass().setAll("btn-sm", "btn-warning");
        btnFinalizar.setDisable(true);

        hBoxButtons = new HBox(btnAnterior, btnSiguiente, btnIniciar, btnFinalizar);
        vbxTablilla = new VBox(gdpTablilla, hBoxButtons);

        CrearMazo();

        hBoxMain = new HBox(vbxTablilla, vbxMazo);
        pnlPrincipal = new Panel("Loteria Mexicana :)");
        pnlPrincipal.getStyleClass().add("panel-success");
        pnlPrincipal.setBody(hBoxMain);
        hBoxMain.setSpacing(20);
        hBoxMain.setPadding(new Insets(20));
        escena = new Scene(pnlPrincipal, 800, 600);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/styles/loteria.css").toExternalForm());

        btnAnterior.setOnAction(e -> {
            if (!juegoIniciado) {
                plantillaActual = (plantillaActual == 0) ? plantillas.size() - 1 : plantillaActual - 1;
                mostrarPlantilla(plantillaActual);
            }
        });

        btnSiguiente.setOnAction(e -> {
            if (!juegoIniciado) {
                plantillaActual = (plantillaActual == plantillas.size() - 1) ? 0 : plantillaActual + 1;
                mostrarPlantilla(plantillaActual);
            }
        });

        btnIniciar.setOnAction(e -> {
            iniciarJuego();
            btnIniciar.setDisable(true);
            btnFinalizar.setDisable(false);
            btnAnterior.setDisable(true);
            btnSiguiente.setDisable(true);
            juegoIniciado = true;
        });

        btnFinalizar.setOnAction(e -> finalizarJuego());
    }

    private void CrearMazo() {
        lblTimer = new Label("00:00");
        imvMazo = new ImageView(getClass().getResource("/images/dorso.jpeg").toString());
        imvMazo.setFitHeight(450);
        imvMazo.setFitWidth(300);
        vbxMazo = new VBox(lblTimer, imvMazo, btnIniciar);
        vbxMazo.setSpacing(10);
    }

    private void CrearPlantillas() {
        plantillas = new ArrayList<>();
        cartasDisponibles = new ArrayList<>(List.of(arImages));

        for (int p = 0; p < 5; p++) {
            arBtnTab = new Button[4][4];
            List<String> cartasRevueltas = new ArrayList<>(cartasDisponibles);
            Collections.shuffle(cartasRevueltas);

            GridPane gridPane = new GridPane();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    String carta = cartasRevueltas.get(i * 4 + j);
                    Image img = new Image(getClass().getResource("/images/" + carta).toString());
                    ImageView imv = new ImageView(img);
                    imv.setFitWidth(70);
                    imv.setFitHeight(120);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(imv);

                    Label lblMarcador = new Label("X");
                    lblMarcador.getStyleClass().add("carta-marcador");
                    lblMarcador.setVisible(false);
                    stackPane.getChildren().add(lblMarcador);

                    arBtnTab[j][i] = new Button();
                    arBtnTab[j][i].setGraphic(stackPane);
                    gridPane.add(arBtnTab[j][i], j, i);

                    int finalI = i, finalJ = j;
                    String finalCarta = carta;
                    arBtnTab[j][i].setOnAction(e -> {
                        if (juegoIniciado) {
                            marcarCarta(stackPane, lblMarcador, finalCarta);
                        }
                    });
                }
            }
            plantillas.add(arBtnTab);
        }

        mostrarPlantilla(0);
    }

    private void mostrarPlantilla(int index) {
        gdpTablilla.getChildren().clear();
        Button[][] plantilla = plantillas.get(index);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gdpTablilla.add(plantilla[j][i], j, i);
            }
        }
    }

    private void marcarCarta(StackPane stackPane, Label lblMarcador, String cartaDeLaPlantilla) {
        String imagenActualMazoNombre = imvMazo.getImage().getUrl().substring(imvMazo.getImage().getUrl().lastIndexOf("/") + 1);

        if (cartaDeLaPlantilla.equals(imagenActualMazoNombre)) {
            lblMarcador.setVisible(true);
            Button buttonClicked = (Button) stackPane.getParent();
            buttonClicked.setDisable(true);

            verificarVictoria();
        }
    }


    private void iniciarJuego() {
        if (timerGeneral != null) {
            timerGeneral.cancel();
        }
        if (timerMazo != null) {
            timerMazo.cancel();
        }

        cartasMazo = new ArrayList<>(cartasDisponibles);
        Collections.shuffle(cartasMazo);

        timerGeneral = new Timer();
        timerTaskGeneral = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> lblTimer.setText(String.format("%02d:%02d", segundosTotales / 60, segundosTotales % 60)));
                segundosTotales++;
            }
        };
        timerGeneral.scheduleAtFixedRate(timerTaskGeneral, 0, 1000);

        timerMazo = new Timer();
        timerTaskMazo = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> siguienteCarta());
            }
        };
        timerMazo.scheduleAtFixedRate(timerTaskMazo, 0, 2500); // Tiempo de las cartas
    }

    private void finalizarJuego() {
        detenerTimers();
        juegoIniciado = false;
        cartasMazo.clear();
        btnIniciar.setDisable(false);
        btnFinalizar.setDisable(true);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        lblTimer.setText("00:00");
        imvMazo.setImage(new Image(getClass().getResource("/images/dorso.jpeg").toString()));
        segundosTotales = 0;


        Button[][] plantillaActual = plantillas.get(this.plantillaActual);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button btn = plantillaActual[j][i];
                StackPane stackPane = (StackPane) btn.getGraphic();
                Label lblMarcador = (Label) stackPane.getChildren().get(1);
                lblMarcador.setVisible(false);
                btn.setDisable(false);
            }
        }
    }


    private void verificarVictoria() {
        boolean haGanado = true;
        Button[][] plantilla = plantillas.get(plantillaActual);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                StackPane stackPane = (StackPane) ((Button) plantilla[j][i]).getGraphic();
                Label lblMarcador = (Label) stackPane.getChildren().get(1);
                if (!lblMarcador.isVisible()) {
                    haGanado = false;
                    break;
                }
            }
            if (!haGanado) break;
        }

        if (haGanado) {
            detenerTimers();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has ganado el juego!");
            alert.showAndWait();
            finalizarJuego();
        }
    }
    private void detenerTimers() {
        if (timerGeneral != null) {
            timerGeneral.cancel();
        }
        if (timerMazo != null) {
            timerMazo.cancel();
        }
    }


    private void siguienteCarta() {
        if (!cartasMazo.isEmpty()) {
            int cartaIndex = new Random().nextInt(cartasMazo.size());
            String cartaActual = cartasMazo.remove(cartaIndex);
            imvMazo.setImage(new Image(getClass().getResource("/images/" + cartaActual).toString()));
        } else {
            if (juegoIniciado) {
                detenerTimers();
                verificarVictoria();

                if (juegoIniciado) {
                    Alert alertDerrota = new Alert(Alert.AlertType.INFORMATION);
                    alertDerrota.setTitle("Derrota");
                    alertDerrota.setHeaderText(null);
                    alertDerrota.setContentText("No has ganado. ¡Inténtalo de nuevo!");
                    alertDerrota.showAndWait();
                    finalizarJuego();
                }
            }
        }
    }
}
