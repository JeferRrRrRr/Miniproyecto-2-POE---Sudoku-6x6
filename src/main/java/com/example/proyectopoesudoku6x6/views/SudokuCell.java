package com.example.proyectopoesudoku6x6.views;

import com.example.proyectopoesudoku6x6.controllers.CellChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class SudokuCell extends TextField {


    private final int fila;

    private final int col;

    private boolean esEditable;

    private int valor;

    private CellChangeListener listener;

    public SudokuCell(int fila, int col, boolean esEditable, int valor) {
        this.fila       = fila;
        this.col        = col;
        this.esEditable = esEditable;
        this.valor      = valor;

        setPrefSize(60, 60);
        setFont(Font.font("Arial", 20));
        setAlignment(Pos.CENTER);
        setFocusTraversable(true);

        if (valor != 0) {

            setText(String.valueOf(valor));
            setEditable(false);
        } else {
            setEditable(esEditable);
        }

        configurarListenerTexto();
    }

    private void configurarListenerTexto() {
        textProperty().addListener((observable, valorAnterior, valorNuevo) -> {

            if (valorNuevo.isEmpty()) {
                this.valor = 0;
                if (listener != null) listener.onValueChanged(fila, col, 0);
                return;
            }

            if (valorNuevo.matches("[1-6]")) {
                this.valor = Integer.parseInt(valorNuevo);
                if (listener != null) listener.onValueChanged(fila, col, this.valor);
            } else {
                setText(valorAnterior);
            }
        });
    }

    public int getValue() {
        return valor;
    }

    public void setValue(int nuevoValor) {
        this.valor = nuevoValor;
        setText(nuevoValor != 0 ? String.valueOf(nuevoValor) : "");
    }

    public void mostrarPista(int num) {
        setValue(num);
        setEditable(false);
        esEditable = false;
        setStyle(getStyle()
                .replaceAll("-fx-background-color:[^;]+;", "")
                + "-fx-background-color: #fffacd;");
    }

    public void setValidacion(boolean valido) {
        if (!esEditable) return; // No modificar celdas fijas ni pistas
        String colorFondo = valor == 0 ? "white" : (valido ? "#c8f7c5" : "#f7c5c5");
        String estilo = getStyle().replaceAll("-fx-background-color:[^;]+;", "");
        setStyle(estilo + "-fx-background-color: " + colorFondo + ";");
    }

    public void setChangeListener(CellChangeListener listener) {
        this.listener = listener;
    }

    public boolean esEditable() {
        return esEditable;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return col;
    }
}
