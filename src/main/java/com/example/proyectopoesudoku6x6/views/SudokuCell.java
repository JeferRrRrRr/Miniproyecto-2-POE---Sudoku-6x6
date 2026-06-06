package com.example.proyectopoesudoku6x6.views;

import com.example.proyectopoesudoku6x6.controllers.CellChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.UnaryOperator;

/**
 * @author Juan Diego Quiñones Cometa
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see CellChangeListener
 * @see com.example.proyectopoesudoku6x6.models.SudokuBoard
 */

public class SudokuCell extends TextField {


    private final int fila;
    private final int col;
    private boolean esEditable;
    private int valor;
    private CellChangeListener listener;

    /**
     * @param fila       fila de la celda en el tablero (0-5)
     * @param col        columna de la celda en el tablero (0-5)
     * @param esEditable {@code true} si el usuario puede ingresar un número
     * @param valor      valor inicial (0 si la celda comienza vacía)
     */
    public SudokuCell(int fila, int col, boolean esEditable, int valor) {
        this.fila       = fila;
        this.col        = col;
        this.esEditable = esEditable;
        this.valor      = valor;

        setPrefSize(60, 60);
        setFont(Font.font("Arial", FontWeight.BOLD, 22));
        setAlignment(Pos.CENTER);
        setFocusTraversable(true);

        if (valor != 0) {
            setText(String.valueOf(valor));
            setEditable(false);
        } else {
            setEditable(esEditable);
        }

        configurarFiltroEntrada();
        configurarEventosMouse();
    }

    private void configurarFiltroEntrada() {
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String nuevoTexto = cambio.getControlNewText();
            if (nuevoTexto.isEmpty() || nuevoTexto.matches("[1-6]")) {
                return cambio;
            }
            return null;
        };

        TextFormatter<String> formatter = new TextFormatter<>(filtro);
        this.setTextFormatter(formatter);


        this.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            if (valorNuevo.isEmpty()) {
                this.valor = 0;
            } else {
                this.valor = Integer.parseInt(valorNuevo);
            }
            if (listener != null) {
                listener.onValueChanged(fila, col, this.valor);
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
                modificarEstilo("-fx-background-color", "#eaf4fb");
            }
        });

        setOnMouseExited(evento -> {
            if (esEditable && valor == 0) {
                modificarEstilo("-fx-background-color", "white");
            }
        });
    }

    /**
     * @return valor entre 1 y 6, o 0 si la celda está vacía
     */
    public int getValue() {
        return valor;
    }

    /**
     * @param nuevoValor nuevo valor (1-6); pasar 0 para dejar la celda vacía
     */
    public void setValue(int nuevoValor) {
        this.valor = nuevoValor;
        setText(nuevoValor != 0 ? String.valueOf(nuevoValor) : "");
    }

    /**
     * @param num número correcto de la solución a mostrar como pista
     * @see #setValue(int)
     * @see #modificarEstilo(String, String)
     */
    public void mostrarPista(int num) {
        setValue(num);
        setEditable(false);
        esEditable = false;
        modificarEstilo("-fx-background-color", "#fffacd");
        modificarEstilo("-fx-text-fill", "#d35400");
    }

    /**
     * @param valido {@code true} si el número cumple las reglas del Sudoku
     * @see #modificarEstilo(String, String)
     */
    public void setValidacion(boolean valido) {
        if (!esEditable) return;

        if (valor == 0 || valido) {
            modificarEstilo("-fx-background-color", "white");
            modificarEstilo("-fx-text-fill", "#2c3e50");
        } else {
            modificarEstilo("-fx-background-color", "#ffe0e0");
            modificarEstilo("-fx-text-fill", "#c0392b");
        }
    }

    /**
     * @param propiedad  propiedad CSS a modificar (ej: {@code -fx-background-color})
     * @param nuevoValor nuevo valor CSS a asignar (ej: {@code #ffe0e0})
     */
    private void modificarEstilo(String propiedad, String nuevoValor) {
        String estiloActual = getStyle();
        if (estiloActual == null) estiloActual = "";

        if (estiloActual.contains(propiedad)) {
            estiloActual = estiloActual.replaceAll(propiedad + "\\s*:[^;]+;", propiedad + ": " + nuevoValor + ";");
        } else {
            estiloActual += propiedad + ": " + nuevoValor + ";";
        }
        setStyle(estiloActual);
    }

    /**
     * @param listener objeto que implementa {@link CellChangeListener}
     */
    public void setChangeListener(CellChangeListener listener) {
        this.listener = listener;
    }

    /**
     * @return {@code true} si la celda puede ser modificada por el usuario
     */
    public boolean esEditable() {
        return esEditable;
    }

    /**
     * @return índice de fila (0-5)
     */
    public int getFila() {
        return fila;
    }

    /**
     * @return índice de columna (0-5)
     */
    public int getColumna() {
        return col;
    }
}