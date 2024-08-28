package com.example.tap2024proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

import static java.awt.SystemColor.text;
import static jdk.internal.org.jline.terminal.Terminal.MouseTracking.Button;

public class HelloApplication extends Application {

    private Button btn1, btn2, btn3;
    private VBox vBox;
    private HBox hBox;


    public void CrearUI(){
        btn1 = new Button(text; "Boton 1");
        btn2 = new Button(text; "Boton 2");
        btn3 = new Button(text;"Boton 3");
        vBox = new VBox(btn1, btn2, btn3);
        hBox = new HBox(btn1, btn2, btn3);
    }

    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(hbox, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}