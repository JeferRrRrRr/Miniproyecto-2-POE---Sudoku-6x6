package com.example.proyectopoesudoku6x6.models;

import com.example.proyectopoesudoku6x6.controllers.CellChangeListener;
import com.example.proyectopoesudoku6x6.views.SudokuCell;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class SudokuBoard extends GridPane implements CellChangeListener {

    private static final int SIZE = 6;

    private static final int MAX_PISTAS_PERMITIDAS = 2;


    private SudokuCell[][] celdas;

    private int[][] solucion;

    private int[][] tableroVisible;


    public SudokuBoard() {
        celdas = new SudokuCell[SIZE][SIZE];
        generarTablero();
    }

    public void generarTablero() {
        this.getChildren().clear();

        // 1. Generar solución completa
        SudokuGenerator generador = new SudokuGenerator(SIZE);
        solucion        = generador.getBoard();
        tableroVisible  = copiarTablero(solucion);

        // 2. Ocultar celdas (dejar 2 visibles por bloque)
        ocultarCeldasAleatorias(tableroVisible);

        // 3. Construir celdas visuales
        for (int fila = 0; fila < SIZE; fila++) {
            for (int col = 0; col < SIZE; col++) {
                int valor    = tableroVisible[fila][col];
                boolean editable = (valor == 0);

                SudokuCell celda = new SudokuCell(fila, col, editable, valor);
                aplicarEstiloCelda(celda, fila, col, editable);
                celda.setChangeListener(this);

                celdas[fila][col] = celda;
                this.add(celda, col, fila);
            }
        }
    }

    public static void ocultarCeldasAleatorias(int[][] tablero) {
        Random rand = new Random();
        boolean[][] mantenerVisible = new boolean[SIZE][SIZE];

        // Por cada bloque 2x3, seleccionar 2 posiciones al azar
        for (int inicioFila = 0; inicioFila < SIZE; inicioFila += 2) {
            for (int inicioCol = 0; inicioCol < SIZE; inicioCol += 3) {
                int visibles = 0;
                while (visibles < 2) {
                    int f = inicioFila + rand.nextInt(2);
                    int c = inicioCol  + rand.nextInt(3);
                    if (!mantenerVisible[f][c]) {
                        mantenerVisible[f][c] = true;
                        visibles++;
                    }
                }
            }
        }

        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                if (!mantenerVisible[f][c]) {
                    tablero[f][c] = 0;
                }
            }
        }
    }

    private void aplicarEstiloCelda(SudokuCell celda, int fila, int col, boolean editable) {
        String colorFondo = editable ? "white" : "#dce8f5";

        // Grosor de bordes: 2 px en límites de bloque, 1 px entre celdas
        String top    = (fila % 2 == 0 && fila != 0) ? "2" : "1";
        String left   = (col  % 3 == 0 && col  != 0) ? "2" : "1";
        String bottom = (fila == SIZE - 1) ? "2" : "1";
        String right  = (col  == SIZE - 1) ? "2" : "1";

        celda.setStyle(String.format(
                "-fx-background-color: %s;" +
                "-fx-border-color: #34495e;" +
                "-fx-border-width: %spx %spx %spx %spx;" +
                "-fx-border-style: solid;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;",
                colorFondo, top, right, bottom, left
        ));
    }

    private int[][] copiarTablero(int[][] tablero) {
        int[][] copia = new int[tablero.length][tablero[0].length];
        for (int i = 0; i < tablero.length; i++) {
            System.arraycopy(tablero[i], 0, copia[i], 0, tablero[i].length);
        }
        return copia;
    }

    public int contarCeldasOcupadas() {
        int ocupadas = 0;
        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                if (celdas[f][c].getValue() != 0) ocupadas++;
            }
        }
        return ocupadas;
    }

    public boolean esMovimientoValido(int fila, int col, int valor) {
        // Revisar fila y columna
        for (int i = 0; i < SIZE; i++) {
            if (i != col && celdas[fila][i].getValue() == valor) return false;
            if (i != fila && celdas[i][col].getValue() == valor) return false;
        }

        // Revisar bloque 2×3
        int inicioFila = (fila / 2) * 2;
        int inicioCol  = (col  / 3) * 3;
        for (int f = inicioFila; f < inicioFila + 2; f++) {
            for (int c = inicioCol; c < inicioCol + 3; c++) {
                if ((f != fila || c != col) && celdas[f][c].getValue() == valor)
                    return false;
            }
        }
        return true;
    }

    public void generarNuevoTablero() {
        generarTablero();
    }

    public int[][] getSolucion() {
        return solucion;
    }

    public SudokuCell[][] getCeldas() {
        return celdas;
    }

    public static int getSize() {
        return SIZE;
    }

    @Override
    public void onValueChanged(int fila, int col, int nuevoValor) {
        // Implementación en paso 2
    }
}
