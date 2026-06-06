package com.example.proyectopoesudoku6x6.controllers;


/**
 * @author Juan Diego
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see com.example.proyectopoesudoku6x6.models.SudokuBoard
 * @see com.example.proyectopoesudoku6x6.views.SudokuCell
 */
public interface CellChangeListener {

    /**
     * @param fila       fila de la celda modificada (0-5)
     * @param col        columna de la celda modificada (0-5)
     * @param nuevoValor nuevo valor de la celda (0 si fue borrada)
     */
    void onValueChanged(int fila, int col, int nuevoValor);
}
