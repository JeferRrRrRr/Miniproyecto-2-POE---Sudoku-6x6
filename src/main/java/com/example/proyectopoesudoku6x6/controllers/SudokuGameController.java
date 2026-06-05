package com.example.proyectopoesudoku6x6.controllers;

import com.example.proyectopoesudoku6x6.models.SudokuBoard;
import com.example.proyectopoesudoku6x6.utils.AlertsSudoku;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SudokuGameController {

    @FXML
    private GridPane boardContainer;

    @FXML
    private Button btnNuevoJuego;

    @FXML
    private Button btnPista;

    @FXML
    private Button btnVerificar;

    @FXML
    private Label lblProgreso;

    private static final int TAMANIO = 6;

    private SudokuBoard tablero;

    private AlertsSudoku alertas;

    @FXML
    public void initialize() {
        alertas = new AlertsSudoku();
        tablero = new SudokuBoard();
        boardContainer.getChildren().add(tablero);
        actualizarProgreso();
    }

    @FXML
    private void handleNuevoJuego() {
        boolean confirma = alertas.confirmarNuevoJuego();
        if (confirma) {
            tablero.generarNuevoTablero();
            actualizarProgreso();
        }
    }

    @FXML
    private void handlePista() {
        tablero.darPista();
        actualizarProgreso();
    }

    @FXML
    private void handleVerificar() {
        tablero.verificarTableroManual();
        actualizarProgreso();
    }

    private void actualizarProgreso() {
        if (lblProgreso != null) {
            int ocupadas = tablero.contarCeldasOcupadas();
            int total    = TAMANIO * TAMANIO;
            lblProgreso.setText("Progreso: " + ocupadas + " / " + total);
        }
    }

    public SudokuBoard getTablero() {
        return tablero;
    }
}
