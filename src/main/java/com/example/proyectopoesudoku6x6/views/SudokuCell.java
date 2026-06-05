package com.example.proyectopoesudoku6x6.views;

import com.example.proyectopoesudoku6x6.controllers.CellChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
        configurarEventosMouse();
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
                if (!valorNuevo.isEmpty()) {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Entrada inválida");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Solo se permiten números del 1 al 6.");
                    alerta.showAndWait();
                }
            }
        });
    }

    private void configurarEventosMouse() {
        setOnMouseClicked(evento -> {
            if (esEditable) {
                requestFocus();
            }
        });

        setOnMouseEntered(evento -> {
            if (esEditable && valor == 0) {
                String estilo = getStyle().replaceAll("-fx-background-color:[^;]+;", "");
                setStyle(estilo + "-fx-background-color: #eaf4fb;");
            }
        });

        setOnMouseExited(evento -> {
            if (esEditable && valor == 0) {
                String estilo = getStyle().replaceAll("-fx-background-color:[^;]+;", "");
                setStyle(estilo + "-fx-background-color: white;");
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
        String estilo = getStyle().replaceAll("-fx-background-color:[^;]+;", "");
        setStyle(estilo + "-fx-background-color: #fffacd;");
    }

    public void setValidacion(boolean valido) {
        if (!esEditable) return;

        String estiloBase = getStyle()
                .replaceAll("-fx-background-color:[^;]+;", "")
                .replaceAll("-fx-border-color:[^;]+;", "");

        if (valor == 0 || valido) {
            setStyle(estiloBase
                    + "-fx-background-color: white;"
                    + "-fx-border-color: #34495e;");
        } else {
            setStyle(estiloBase
                    + "-fx-background-color: #ffe0e0;"
                    + "-fx-border-color: #e74c3c;");
        }
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
