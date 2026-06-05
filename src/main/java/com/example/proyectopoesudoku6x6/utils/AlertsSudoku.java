package com.example.proyectopoesudoku6x6.utils;

public class AlertsSudoku extends AlertAdapter {

    @Override
    public boolean confirmarNuevoJuego() {
        String titulo  = "Nuevo Juego";
        String mensaje = "¿Deseas iniciar un nuevo Sudoku?\nLos datos actuales se borrarán.";
        return super.mostrarConfirmacion(titulo, mensaje);
    }
}
