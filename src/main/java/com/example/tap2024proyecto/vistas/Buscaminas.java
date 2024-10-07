package com.example.tap2024proyecto.vistas;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

import static javafx.application.Application.launch;

public class Buscaminas extends Stage {

    private int gridSize = 10; // Tamaño de la cuadrícula
    private int bombCount = 10; // Cantidad de bombas
    private Button[][] buttons; // Botones que representan las casillas
    private boolean[][] bombGrid; // Almacena la ubicación de las bombas
    private boolean[][] revealed; // Almacena si una casilla ha sido descubierta
    private boolean[][] flagged;  // Almacena si una casilla está marcada
    private Random random = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Buscaminas");

        // Crear layout principal
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Campo de texto para ingresar cantidad de bombas
        TextField bombInput = new TextField();
        bombInput.setPromptText("Cantidad de Bombas");

        // Botón para generar el campo minado
        Button generarButton = new Button("Generar Campo Minado");

        // Layout para el campo minado
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // Evento del botón "Generar Campo Minado"
        generarButton.setOnAction(e -> {
            try {
                bombCount = Integer.parseInt(bombInput.getText());
                if (bombCount < 1 || bombCount >= gridSize * gridSize) {
                    throw new NumberFormatException("Cantidad inválida de bombas.");
                }
                inicializarCampo(grid);
            } catch (NumberFormatException ex) {
                mostrarAlerta("Por favor, ingrese una cantidad válida de bombas.");
            }
        });

        // Agregar componentes al layout
        root.getChildren().addAll(bombInput, generarButton, grid);

        // Configuración de la escena
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void inicializarCampo(GridPane grid) {
        // Limpiar el campo anterior
        grid.getChildren().clear();

        // Inicializar las matrices de bombas y casillas descubiertas
        buttons = new Button[gridSize][gridSize];
        bombGrid = new boolean[gridSize][gridSize];
        revealed = new boolean[gridSize][gridSize];
        flagged = new boolean[gridSize][gridSize];

        // Colocar bombas aleatoriamente
        for (int i = 0; i < bombCount; i++) {
            int x, y;
            do {
                x = random.nextInt(gridSize);
                y = random.nextInt(gridSize);
            } while (bombGrid[x][y]); // Reintentar si ya hay una bomba en esa posición
            bombGrid[x][y] = true;
        }

        // Crear los botones de la cuadrícula
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Button button = new Button();
                button.setPrefSize(40, 40);
                final int finalX = x;
                final int finalY = y;

                // Clic izquierdo para descubrir casilla
                button.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        descubrirCasilla(finalX, finalY);
                    }
                    // Clic derecho para marcar casilla
                    if (e.getButton() == MouseButton.SECONDARY) {
                        marcarCasilla(finalX, finalY);
                    }
                });

                buttons[x][y] = button;
                grid.add(button, x, y);
            }
        }
    }

    private void descubrirCasilla(int x, int y) {
        if (revealed[x][y] || flagged[x][y]) {
            return; // Ya ha sido descubierta o marcada
        }

        if (bombGrid[x][y]) {
            // Bomba encontrada, el jugador pierde
            buttons[x][y].setText("B");
            buttons[x][y].setStyle("-fx-background-color: red;");
            mostrarAlerta("¡Has perdido!");
            revelarTodo();
        } else {
            int bombasAdyacentes = contarBombasAdyacentes(x, y);
            revealed[x][y] = true;

            if (bombasAdyacentes > 0) {
                buttons[x][y].setText(String.valueOf(bombasAdyacentes));
            } else {
                buttons[x][y].setText(""); // Espacio vacío
                // Descubrir casillas adyacentes si no hay bombas alrededor
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (esPosicionValida(nx, ny)) {
                            descubrirCasilla(nx, ny);
                        }
                    }
                }
            }
            buttons[x][y].setDisable(true); // Desactivar la casilla
            verificarVictoria();
        }
    }

    private void marcarCasilla(int x, int y) {
        if (revealed[x][y]) {
            return; // No se puede marcar una casilla descubierta
        }
        if (!flagged[x][y]) {
            buttons[x][y].setText("F");
            flagged[x][y] = true;
        } else {
            buttons[x][y].setText("");
            flagged[x][y] = false;
        }
    }

    private int contarBombasAdyacentes(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (esPosicionValida(nx, ny) && bombGrid[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean esPosicionValida(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    private void revelarTodo() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (bombGrid[x][y]) {
                    buttons[x][y].setText("B");
                    buttons[x][y].setStyle("-fx-background-color: red;");
                }
                buttons[x][y].setDisable(true);
            }
        }
    }

    private void verificarVictoria() {
        int casillasDescubiertas = 0;
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (revealed[x][y]) {
                    casillasDescubiertas++;
                }
            }
        }
        if (casillasDescubiertas == (gridSize * gridSize - bombCount)) {
            mostrarAlerta("¡Has ganado!");
            revelarTodo();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado del Juego");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

