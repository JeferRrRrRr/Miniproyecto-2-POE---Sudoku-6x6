package com.example.proyectopoesudoku6x6.models;

import com.example.proyectopoesudoku6x6.controllers.CellChangeListener;
import com.example.proyectopoesudoku6x6.utils.AlertsSudoku;
import com.example.proyectopoesudoku6x6.views.SudokuCell;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import java.util.Random;

/**
 * @author Juan Diego Quiñones Cometa
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see SudokuGenerator
 * @see SudokuCell
 * @see CellChangeListener
 */

public class SudokuBoard extends GridPane implements CellChangeListener {

    /** @see #esMovimientoValido(int, int, int) */
    private static final int SIZE = 6;

    /** @see #darPista() */
    private static final int MIN_CELDAS_LIBRES_PARA_PISTA = 2;

    /** @see #getCeldas() */
    private SudokuCell[][] celdas;

    /** @see #getSolucion() */
    private int[][] solucion;

    /** @see #ocultarCeldasAleatorias(int[][]) */
    private int[][] tableroVisible;

    /** @see #generarTablero() */
    public SudokuBoard() {
        celdas = new SudokuCell[SIZE][SIZE];
        generarTablero();
    }

    /**
     * @see SudokuGenerator
     * @see #ocultarCeldasAleatorias(int[][])
     * @see #aplicarEstiloCelda(SudokuCell, int, int, boolean)
     */
    public void generarTablero() {
        this.getChildren().clear();

        SudokuGenerator generador = new SudokuGenerator(SIZE);
        solucion       = generador.getBoard();
        tableroVisible = copiarTablero(solucion);

        ocultarCeldasAleatorias(tableroVisible);

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

    /** @see #generarTablero() */
    public void generarNuevoTablero() {
        generarTablero();
    }

    /**
     * @param tablero tablero a modificar; las celdas ocultas quedan en 0
     */
    public static void ocultarCeldasAleatorias(int[][] tablero) {
        Random rand = new Random();
        boolean[][] mantenerVisible = new boolean[SIZE][SIZE];

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

    /**
     * @param celda    celda a la que se aplica el estilo
     * @param fila     fila de la celda (0-5)
     * @param col      columna de la celda (0-5)
     * @param editable {@code true} si el usuario puede editar la celda
     */
    private void aplicarEstiloCelda(SudokuCell celda, int fila, int col, boolean editable) {
        String colorFondo = editable ? "white" : "#dce8f5";

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

    /**
     * @param celda celda a modificar
     * @param color color CSS a aplicar (nombre o hexadecimal)
     */
    private void establecerFondoCelda(SudokuCell celda, String color) {
        String estilo = celda.getStyle()
                .replaceAll("-fx-background-color:[^;]+;", "");
        celda.setStyle(estilo + "-fx-background-color: " + color + ";");
    }

    /**
     * @param fila       fila de la celda modificada (0-5)
     * @param col        columna de la celda modificada (0-5)
     * @param nuevoValor nuevo valor introducido (0 si fue borrada)
     * @see #actualizarColoresCeldas()
     * @see #verificarCompletado()
     */
    @Override
    public void onValueChanged(int fila, int col, int nuevoValor) {
        actualizarColoresCeldas();
        verificarCompletado();
    }

    /**
     * @see SudokuCell#setValidacion(boolean)
     * @see #esMovimientoValido(int, int, int)
     */
    private void actualizarColoresCeldas() {
        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                SudokuCell celda = celdas[f][c];
                if (!celda.esEditable()) continue;

                int val = celda.getValue();
                if (val == 0) {
                    celda.setValidacion(true);  // vacía = sin error
                } else {
                    celda.setValidacion(esMovimientoValido(f, c, val));
                }
            }
        }
    }

    /**
     * @param fila  fila de la celda (0-5)
     * @param col   columna de la celda (0-5)
     * @param valor valor a validar (1-6)
     * @return {@code true} si el valor no viola ninguna regla del Sudoku
     */
    public boolean esMovimientoValido(int fila, int col, int valor) {
        for (int i = 0; i < SIZE; i++) {
            if (i != col  && celdas[fila][i].getValue() == valor) return false;
            if (i != fila && celdas[i][col].getValue()  == valor) return false;
        }
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

    /**
     * @see AlertsSudoku#showAlert(Alert.AlertType, String, String)
     * @see SudokuCell#setEditable(boolean)
     */
    private void verificarCompletado() {
        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                int val = celdas[f][c].getValue();
                if (val == 0 || !esMovimientoValido(f, c, val)) return;
            }
        }

        AlertsSudoku alerta = new AlertsSudoku();
        alerta.showAlert(Alert.AlertType.INFORMATION,
                "¡Felicidades!",
                "¡Has completado el Sudoku correctamente! 🎉");

        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                celdas[f][c].setEditable(false);
            }
        }
    }

    /**
     * @see #actualizarColoresCeldas()
     * @see #verificarCompletado()
     * @see AlertsSudoku#showAlert(Alert.AlertType, String, String)
     */
    public void verificarTableroManual() {
        boolean hayVacias  = false;
        boolean hayErrores = false;

        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                int val = celdas[f][c].getValue();
                if (val == 0) {
                    hayVacias = true;
                } else if (!esMovimientoValido(f, c, val)) {
                    hayErrores = true;
                }
            }
        }

        AlertsSudoku alerta = new AlertsSudoku();

        if (hayErrores) {
            alerta.showAlert(Alert.AlertType.ERROR,
                    "Tablero incorrecto",
                    "Hay errores en el tablero. Revisa los números con borde rojo.");
        } else if (hayVacias) {
            alerta.showAlert(Alert.AlertType.WARNING,
                    "Tablero incompleto",
                    "Aún hay celdas vacías. ¡Sigue intentando!");
        } else {
            verificarCompletado();
        }

        actualizarColoresCeldas();
    }

    /**
     * @see SudokuCell#mostrarPista(int)
     * @see #MIN_CELDAS_LIBRES_PARA_PISTA
     */
    public void darPista() {
        int celdasLibres = (SIZE * SIZE) - contarCeldasOcupadas();

        if (celdasLibres <= MIN_CELDAS_LIBRES_PARA_PISTA) {
            AlertsSudoku alerta = new AlertsSudoku();
            alerta.showAlert(Alert.AlertType.WARNING,
                    "Sin pistas disponibles",
                    "Ya no hay pistas disponibles. ¡Estás muy cerca de terminar!");
            return;
        }

        Random rand = new Random();
        boolean encontrada = false;
        while (!encontrada) {
            int f = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);
            SudokuCell celda = celdas[f][c];
            if (celda.getValue() == 0) {
                celda.mostrarPista(solucion[f][c]);
                encontrada = true;
            }
        }
        actualizarColoresCeldas();
    }

    /**
     * @return número de celdas con valor distinto de 0
     */
    public int contarCeldasOcupadas() {
        int ocupadas = 0;
        for (int f = 0; f < SIZE; f++) {
            for (int c = 0; c < SIZE; c++) {
                if (celdas[f][c].getValue() != 0) ocupadas++;
            }
        }
        return ocupadas;
    }

    /**
     * @param tablero tablero original a copiar
     * @return nueva instancia con los mismos valores
     */
    private int[][] copiarTablero(int[][] tablero) {
        int[][] copia = new int[tablero.length][tablero[0].length];
        for (int i = 0; i < tablero.length; i++) {
            System.arraycopy(tablero[i], 0, copia[i], 0, tablero[i].length);
        }
        return copia;
    }

    /**
     * @return matriz con la solución completa del tablero actual
     */
    public int[][] getSolucion() {
        return solucion;
    }

    /**
     * @return matriz de celdas visuales del tablero
     */
    public SudokuCell[][] getCeldas() {
        return celdas;
    }

    /**
     * @return tamaño del tablero (siempre 6)
     */
    public static int getSize() {
        return SIZE;
    }
}
