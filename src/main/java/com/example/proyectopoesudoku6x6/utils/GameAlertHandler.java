package com.example.proyectopoesudoku6x6.utils;


/**
 * @author Juan Diego Quiñones Cometa
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see AlertAdapter
 * @see AlertsSudoku
 */

public interface GameAlertHandler {

    void onStart();

    void onPause();

    void onResume();

    void onEnd();

    /**
     * @param mensajeError mensaje descriptivo del error ocurrido
     */
    void onError(String mensajeError);

    /**
     * @param titulo  título de la ventana de información
     * @param mensaje contenido del mensaje informativo
     */
    void mostrarInfo(String titulo, String mensaje);

    /**
     * @param titulo  título de la ventana de error
     * @param mensaje contenido del mensaje de error
     */
    void mostrarError(String titulo, String mensaje);

    /**
     * @param titulo  título de la ventana de advertencia
     * @param mensaje contenido del mensaje de advertencia
     */
    void mostrarAdvertencia(String titulo, String mensaje);

    /**
     * @param titulo  título de la ventana de confirmación
     * @param mensaje pregunta o mensaje a confirmar
     * @return {@code true} si el usuario presionó OK
     */
    boolean mostrarConfirmacion(String titulo, String mensaje);
}
