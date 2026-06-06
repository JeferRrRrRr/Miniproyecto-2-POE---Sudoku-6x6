package com.example.proyectopoesudoku6x6.utils;

/**
 * @author Juan Diego Quiñones Cometa
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see AlertAdapter
 * @see GameAlertHandler
 */
public class AlertsSudoku extends AlertAdapter {

    /**
     * @return {@code true} si el usuario confirmó iniciar un nuevo juego
     * @see AlertAdapter#mostrarConfirmacion(String, String)
     */
    @Override
    public boolean confirmarNuevoJuego() {
        String titulo  = "Nuevo Juego";
        String mensaje = "¿Deseas iniciar un nuevo Sudoku?\nLos datos actuales se borrarán.";
        return super.mostrarConfirmacion(titulo, mensaje);
    }
}
