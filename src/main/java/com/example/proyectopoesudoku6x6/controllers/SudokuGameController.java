package com.example.proyectopoesudoku6x6.controllers;

import com.example.proyectopoesudoku6x6.models.SudokuBoard;
import com.example.proyectopoesudoku6x6.utils.AlertsSudoku;
import com.example.proyectopoesudoku6x6.views.SudokuCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private static final int MAX_PISTAS = 3;

    private SudokuBoard tablero;
    private AlertsSudoku alertas;
    private int pistasRestantes;

    @FXML
    public void initialize() {
        alertas = new AlertsSudoku();
        tablero = new SudokuBoard();

        if (boardContainer != null) {
            boardContainer.getChildren().add(tablero);
        }

        pistasRestantes = MAX_PISTAS;

        tablero.addEventFilter(KeyEvent.KEY_PRESSED, this::manejarNavegacionTeclado);

        tablero.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            Platform.runLater(this::actualizarProgreso);
        });

        actualizarProgreso();
    }

    @FXML
    private void handleNuevoJuego() {
        boolean confirma = alertas.confirmarNuevoJuego();
        if (confirma) {
            tablero.generarNuevoTablero();
            pistasRestantes = MAX_PISTAS;

            if (btnPista != null) btnPista.setDisable(false);
            if (btnVerificar != null) btnVerificar.setDisable(false);

            actualizarProgreso();
        }
    }

    @FXML
    private void handlePista() {
        if (pistasRestantes > 0) {
            int ocupadasAntes = tablero.contarCeldasOcupadas();
            tablero.darPista();
            int ocupadasDespues = tablero.contarCeldasOcupadas();

            if (ocupadasDespues > ocupadasAntes) {
                pistasRestantes--;
                actualizarProgreso();
            }

            if (pistasRestantes <= 0) {
                if (btnPista != null) btnPista.setDisable(true);
                alertas.mostrarInfo("Ayuda agotada ", "Has utilizado todas tus pistas (" + MAX_PISTAS + ") permitidas.");
            }
        }
    }

    @FXML
    private void handleVerificar() {
        tablero.verificarTableroManual();
        actualizarProgreso();

        if (tablero.contarCeldasOcupadas() == TAMANIO * TAMANIO) {
            if (btnPista != null) btnPista.setDisable(true);
            if (btnVerificar != null) btnVerificar.setDisable(true);
        }
    }

    private void actualizarProgreso() {
        if (lblProgreso != null) {
            int ocupadas = tablero.contarCeldasOcupadas();
            int total    = TAMANIO * TAMANIO;
            lblProgreso.setText(String.format("Progreso: %d / %d | Pistas restantes: %d", ocupadas, total, pistasRestantes));

            if (ocupadas == total) {
                if (btnPista != null) btnPista.setDisable(true);
            }
        }
    }

    private void manejarNavegacionTeclado(KeyEvent event) {
        if (event.getTarget() instanceof SudokuCell) {
            SudokuCell celdaActual = (SudokuCell) event.getTarget();
            int fila = celdaActual.getFila();
            int col = celdaActual.getColumna();
            boolean movido = false;

            switch (event.getCode()) {
                case UP:    fila = (fila > 0) ? fila - 1 : TAMANIO - 1; movido = true; break;
                case DOWN:  fila = (fila < TAMANIO - 1) ? fila + 1 : 0; movido = true; break;
                case LEFT:  col = (col > 0) ? col - 1 : TAMANIO - 1; movido = true; break;
                case RIGHT: col = (col < TAMANIO - 1) ? col + 1 : 0; movido = true; break;
                default: break;
            }

            if (movido) {
                SudokuCell[][] celdas = tablero.getCeldas();
                celdas[fila][col].requestFocus();
                event.consume();
            }
        }
    }

    public SudokuBoard getTablero() {
        return tablero;
    }
}