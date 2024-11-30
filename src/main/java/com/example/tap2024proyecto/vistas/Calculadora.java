package com.example.tap2024proyecto.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;

public class Calculadora extends Stage {

    private Button[][] arBtns;
    private Button btnClear;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-"};

    private String operador = "";
    private double operando1 = 0;
    private boolean nuevoOperando = true;
    private boolean calculoEnCadena = false;

    public Calculadora() {
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        arBtns = new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        txtPantalla.setId("pantalla");

        gdpTeclado = new GridPane();
        CrearTeclado();

        btnClear = new Button("Clear");
        btnClear.setId("btn-clear"); // ID para el botón Clear
        btnClear.setOnAction(event -> limpiar());

        vBox = new VBox(txtPantalla, gdpTeclado, btnClear);
        escena = new Scene(vBox, 200, 250);
        escena.getStylesheets().add(getClass().getResource("/styles/cal.css").toExternalForm());
    }

    private void CrearTeclado() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int index = 4 * i + j;
                arBtns[i][j] = new Button(strTeclas[index]);
                arBtns[i][j].setPrefSize(50, 50);
                arBtns[i][j].setId("btn-" + strTeclas[index]);

                int finalI = i;
                int finalJ = j;
                arBtns[i][j].setOnAction(event -> detectarTecla(strTeclas[4 * finalI + finalJ]));
                gdpTeclado.add(arBtns[i][j], j, i);
            }
        }
    }

    private boolean esNumeroValido(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void detectarTecla(String tecla) {
        if (!esNumeroValido(txtPantalla.getText())) {
            limpiar();
            return;
        }

        if (tecla.matches("[0-9]")) {
            if (nuevoOperando) {
                txtPantalla.setText(tecla);
                nuevoOperando = false;
            } else {
                txtPantalla.appendText(tecla);
            }
            calculoEnCadena = false;
        } else if (tecla.equals(".")) {
            if (!txtPantalla.getText().contains(".")) {
                txtPantalla.appendText(tecla);
                nuevoOperando = false;
            }
        } else if (tecla.matches("[\\+\\-\\*/]")) {
            if (!nuevoOperando) {
                realizarOperacion();
            }
            operador = tecla;
            nuevoOperando = true;
        } else if (tecla.equals("=")) {
            realizarOperacion();
            operador = "";
        }
    }

    private void realizarOperacion() {
        double operando2;
        try {
            operando2 = parseDouble(txtPantalla.getText());
        } catch (NumberFormatException e) {
            txtPantalla.setText("Error: Entrada inválida");
            return;
        }

        double resultado = 0;
        boolean error = false;

        switch (operador) {
            case "+":
                resultado = operando1 + operando2;
                break;
            case "-":
                resultado = operando1 - operando2;
                break;
            case "*":
                resultado = operando1 * operando2;
                break;
            case "/":
                if (operando2 != 0) {
                    resultado = operando1 / operando2;
                } else {
                    txtPantalla.setText("Error: División por 0");
                    error = true;
                }
                break;
            default:
                resultado = operando2;
                break;
        }

        if (!error) {
            txtPantalla.setText(String.valueOf(resultado));
            operando1 = resultado;
            nuevoOperando = true;
            calculoEnCadena = true;
        }
    }

    private void limpiar() {
        txtPantalla.setText("0");
        operador = "";
        operando1 = 0;
        nuevoOperando = true;
        calculoEnCadena = false;
    }
}