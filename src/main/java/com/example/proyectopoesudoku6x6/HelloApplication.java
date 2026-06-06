package com.example.proyectopoesudoku6x6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Ajustamos el tamaño a 600x650 para que el tablero se vea perfectamente
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        stage.setTitle("Sudoku 6x6 - Proyecto FPOE");
        stage.setScene(scene);
        stage.setResizable(false); // Evita que dañen el diseño redimensionando la ventana
        stage.show();
    }
}