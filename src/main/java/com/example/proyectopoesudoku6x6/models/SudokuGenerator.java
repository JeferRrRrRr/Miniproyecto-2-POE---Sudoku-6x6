package com.example.proyectopoesudoku6x6.models;

import java.util.Random;

/**
 * @author Juan Diego Quiñones Cometa
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see SudokuBoard
 */
public class SudokuGenerator {


    private final int size;

    private final int boxRows;

    private final int boxCols;

    private final int[][] board;

    private final Random rand = new Random();

    /**
     * @param size tamaño del tablero (para este proyecto siempre 6)
     */
    public SudokuGenerator(int size) {
        this.size = size;

        switch (size) {
            case 4  -> { boxRows = 2; boxCols = 2; }
            case 6  -> { boxRows = 2; boxCols = 3; }
            case 9  -> { boxRows = 3; boxCols = 3; }
            default -> {
                int sqrt = (int) Math.sqrt(size);
                boxRows = sqrt;
                boxCols = sqrt;
            }
        }

        board = new int[size][size];
        generarTablero();
    }

    /**
     * @return arreglo 2D con el tablero Sudoku generado
     */
    public int[][] getBoard() {
        return board;
    }

    private void generarTablero() {
        rellenarTablero(0, 0);
    }

    /**
     * @param fila fila actual en el recorrido
     * @param col  columna actual en el recorrido
     * @return {@code true} si el tablero quedó completamente relleno
     */
    private boolean rellenarTablero(int fila, int col) {
        if (fila == size) return true;

        int siguienteFila = (col == size - 1) ? fila + 1 : fila;
        int siguienteCol  = (col + 1) % size;

        int[] numeros = generarNumerosAleatorios(size);
        for (int num : numeros) {
            if (esSeguro(fila, col, num)) {
                board[fila][col] = num;
                if (rellenarTablero(siguienteFila, siguienteCol))
                    return true;
                board[fila][col] = 0;
            }
        }
        return false;
    }

    /**
     * @param fila fila donde se quiere colocar el número
     * @param col  columna donde se quiere colocar el número
     * @param num  número a validar (1-6)
     * @return {@code true} si el número no viola ninguna regla
     */
    private boolean esSeguro(int fila, int col, int num) {
        for (int i = 0; i < size; i++) {
            if (board[fila][i] == num || board[i][col] == num)
                return false;
        }

        int inicioFila = (fila / boxRows) * boxRows;
        int inicioCol  = (col  / boxCols) * boxCols;
        for (int r = 0; r < boxRows; r++) {
            for (int c = 0; c < boxCols; c++) {
                if (board[inicioFila + r][inicioCol + c] == num)
                    return false;
            }
        }
        return true;
    }

    /**
     * @param n cantidad de números a generar
     * @return arreglo mezclado con valores del 1 al n
     */
    private int[] generarNumerosAleatorios(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        for (int i = 0; i < n; i++) {
            int j = rand.nextInt(n);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }
}
