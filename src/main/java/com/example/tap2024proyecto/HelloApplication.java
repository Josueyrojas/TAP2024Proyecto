package com.example.tap2024proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Button button (btn1, btn2, btn3);
    private VBox vBox;


    public void CrearUI(){
        btn1 = new Button(text: "Boton 1");
        btn2 = new Button(text: "Boton 2");
        btn3 = new Button(text: "Boton 3");
        vBox = new VBox(btn1, btn2, btn3);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}