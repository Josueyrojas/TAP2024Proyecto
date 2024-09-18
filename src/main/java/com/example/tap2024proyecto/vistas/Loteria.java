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
    private String[] arImages = {"1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg","9.jpg","10.jpg","11.jpg","12.jpg","13.jpg","14.jpg","15.jpg","16.jpg","17.jpg","18.jpg","19.jpg","20.jpg","21.jpg","22.jpg","23.jpg","24.jpg","25.jpg","26.jpg","27.jpg","28.jpg","29.jpg","30.jpg","31.jpg","32.jpg","33.jpg","34.jpg","35.jpg","36.jpg","37.jpg","38.jpg","39.jpg","40.jpg","41.jpg","42.jpg","43.jpg","44.jpg","45.jpg","46.jpg","47.jpg","48.jpg","49.jpg","50.jpg","51.jpg","52.jpg","53.jpg","54.jpg"};
    private Button[][] arBtnTab;
    private Timer timer;
    private int plantillaActual = 0;
    private List<Button[][]> plantillas;
    private int tiempoRestante = 5; // Tiempo en segundos para cada carta
    private TimerTask timerTask;
    private List<String> cartasDisponibles;
    private List<String> cartasMazo;
    private boolean juegoIniciado = false; // Estado del juego

    private Panel pnlPrincipal;

    public Loteria(){
        CrearUI();
        this.setTitle("Loteria Mexicana :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/izquierda.png").toString()));
        imvAnt.setFitHeight(50);
        imvAnt.setFitWidth(50);
        imvSig = new ImageView(new Image(getClass().getResource("/images/derecha.png").toString()));
        imvSig.setFitWidth(50);
        imvSig.setFitHeight(50);

        gdpTablilla = new GridPane();
        CrearPlantillas();  // Modificación aquí para crear varias plantillas

        btnAnterior = new Button();
        btnAnterior.setGraphic(imvAnt);

        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);

        btnIniciar = new Button("Iniciar Juego");
        btnIniciar.getStyleClass().setAll("btn-sm","btn-danger");

        btnFinalizar = new Button("Finalizar Juego");
        btnFinalizar.getStyleClass().setAll("btn-sm", "btn-warning");
        btnFinalizar.setDisable(true); // Deshabilitar al inicio

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

        // Funcionalidad botones
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
            btnIniciar.setDisable(true); // Desactivar el botón de iniciar
            btnFinalizar.setDisable(false); // Habilitar el botón de finalizar
            btnAnterior.setDisable(true); // Desactivar el botón de cambiar plantilla
            btnSiguiente.setDisable(true); // Desactivar el botón de cambiar plantilla
            juegoIniciado = true; // Cambiar el estado del juego
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

    // Modificación: Crea varias plantillas y las almacena en una lista
    private void CrearPlantillas() {
        plantillas = new ArrayList<>();
        cartasDisponibles = new ArrayList<>(List.of(arImages)); // Lista con todas las cartas disponibles

        for (int p = 0; p < 5; p++) { // Genera 5 plantillas
            arBtnTab = new Button[4][4];
            List<String> cartasRevueltas = new ArrayList<>(cartasDisponibles); // Crea una copia de la lista de cartas
            Collections.shuffle(cartasRevueltas); // Revuélvela

            GridPane gridPane = new GridPane(); // Nueva tablilla por plantilla

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    String carta = cartasRevueltas.get(i * 4 + j); // Toma una carta revuelta
                    Image img = new Image(getClass().getResource("/images/" + carta).toString());
                    ImageView imv = new ImageView(img);
                    imv.setFitWidth(70);
                    imv.setFitHeight(120);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(imv);

                    // Crear el label con "X" para marcar
                    Label lblMarcador = new Label("X");
                    lblMarcador.getStyleClass().add("carta-marcador"); // Clase CSS para estilo del marcador
                    lblMarcador.setVisible(false); // Ocultar inicialmente
                    stackPane.getChildren().add(lblMarcador);

                    arBtnTab[j][i] = new Button();
                    arBtnTab[j][i].setGraphic(stackPane);
                    gridPane.add(arBtnTab[j][i], j, i);

                    // Marcar carta si coincide con el mazo
                    int finalI = i, finalJ = j;
                    String finalCarta = carta;  // Guardar la referencia de la carta
                    arBtnTab[j][i].setOnAction(e -> {
                        if (juegoIniciado) {
                            marcarCarta(stackPane, lblMarcador, finalCarta);  // Llamada a la nueva función marcarCarta
                        }
                    });
                }
            }
            plantillas.add(arBtnTab); // Añade la plantilla generada
        }

        mostrarPlantilla(0); // Muestra la primera plantilla por defecto
    }

    // Mostrar la plantilla actual en el GridPane
    private void mostrarPlantilla(int index) {
        gdpTablilla.getChildren().clear();
        Button[][] plantilla = plantillas.get(index);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gdpTablilla.add(plantilla[j][i], j, i);
            }
        }
    }

    // Nueva función para marcar la carta si coincide con la del mazo
    private void marcarCarta(StackPane stackPane, Label lblMarcador, String cartaDeLaPlantilla) {
        String imagenActualMazoNombre = imvMazo.getImage().getUrl().substring(imvMazo.getImage().getUrl().lastIndexOf('/') + 1);

        if (cartaDeLaPlantilla.equals(imagenActualMazoNombre)) {
            lblMarcador.setVisible(true); // Mostrar "X" si coincide
            ((Button) stackPane.getParent().getParent()).setDisable(true); // Desactivar el botón para evitar más clics
        }
    }

    // Temporizador que controla el cambio de cartas en el mazo
    private void iniciarJuego() {
        if (timer != null) {
            timer.cancel();
        }
        cartasMazo = new ArrayList<>(cartasDisponibles); // Inicializa el mazo de cartas disponibles
        Collections.shuffle(cartasMazo); // Baraja las cartas del mazo
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    siguienteCarta();
                    tiempoRestante = 5; // Reinicia el tiempo para la siguiente carta
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 5000); // Cambiar cada 5 segundos
    }

    // Muestra la siguiente carta del mazo
    private void siguienteCarta() {
        if (!cartasMazo.isEmpty()) {
            int cartaIndex = new Random().nextInt(cartasMazo.size()); // Selecciona una carta aleatoria del mazo
            String cartaActual = cartasMazo.remove(cartaIndex); // Elimina la carta del mazo
            imvMazo.setImage(new Image(getClass().getResource("/images/" + cartaActual).toString()));
            lblTimer.setText(String.format("00:0%d", tiempoRestante)); // Actualiza el temporizador
        } else {
            // Si no quedan cartas, mostrar un mensaje
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin del Juego");
            alert.setHeaderText(null);
            alert.setContentText("El mazo se ha agotado.");
            alert.showAndWait();
            finalizarJuego(); // Terminar el juego si el mazo se agota
        }
    }

    // Finaliza el juego
    private void finalizarJuego() {
        if (timer != null) {
            timer.cancel();
        }
        btnIniciar.setDisable(false); // Habilitar el botón de iniciar juego
        btnFinalizar.setDisable(true); // Desactivar el botón de finalizar juego
        btnAnterior.setDisable(false); // Habilitar el botón de cambiar plantilla
        btnSiguiente.setDisable(false); // Habilitar el botón de cambiar plantilla
        juegoIniciado = false; // Restablecer el estado del juego
    }

    // Verificar si el jugador ha ganado (todas las cartas marcadas)
    private void verificarVictoria() {
        boolean haGanado = true;
        Button[][] plantilla = plantillas.get(plantillaActual);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!arBtnTab[j][i].getStyle().contains("green")) {
                    haGanado = false;
                    break;
                }
            }
        }

        if (haGanado) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            alert.setContentText("¡Has ganado el juego!");
            alert.showAndWait();
        }
    }
}
