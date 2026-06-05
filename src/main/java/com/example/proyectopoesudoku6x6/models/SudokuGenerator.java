package com.example.proyectopoesudoku6x6.models;

import java.util.Random;

public class SudokuGenerator {


    private final int size;


    private final int boxRows;


    private final int boxCols;

    private final int[][] board;


    private final Random rand = new Random();

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

    public int[][] getBoard() {
        return board;
    }

    private void generarTablero() {
        rellenarTablero(0, 0);
    }

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
