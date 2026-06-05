package com.example.proyectopoesudoku6x6.controllers;


public interface CellChangeListener {

    void onValueChanged(int fila, int col, int nuevoValor);
}
