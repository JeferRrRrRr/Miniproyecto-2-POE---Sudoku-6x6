package com.example.proyectopoesudoku6x6.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author Juan Diego Quiñones Cometa
 * @author Jeferson Gomez Gomez
 * @version 1.0
 * @see GameAlertHandler
 * @see AlertsSudoku
 */

public abstract class AlertAdapter implements GameAlertHandler {

    @Override
    public void onStart() {}

    @Override
    public void onPause() {}

    @Override
    public void onResume() {}

    @Override
    public void onEnd() {}

    /**
     * @param mensajeError mensaje descriptivo del error
     * @see #mostrarError(String, String)
     */
    @Override
    public void onError(String mensajeError) {
        mostrarError("Error", mensajeError);
    }

    /**
     * @param titulo  título de la ventana
     * @param mensaje contenido del mensaje
     * @see #showAlert(Alert.AlertType, String, String)
     */
    @Override
    public void mostrarInfo(String titulo, String mensaje) {
        showAlert(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    /**
     * @param titulo  título de la ventana
     * @param mensaje contenido del mensaje
     * @see #showAlert(Alert.AlertType, String, String)
     */
    @Override
    public void mostrarError(String titulo, String mensaje) {
        showAlert(Alert.AlertType.ERROR, titulo, mensaje);
    }

    /**
     * @param titulo  título de la ventana
     * @param mensaje contenido del mensaje
     * @see #showAlert(Alert.AlertType, String, String)
     */
    @Override
    public void mostrarAdvertencia(String titulo, String mensaje) {
        showAlert(Alert.AlertType.WARNING, titulo, mensaje);
    }

    /**
     * @param titulo  título de la ventana de confirmación
     * @param mensaje pregunta o mensaje a confirmar
     * @return {@code true} si el usuario presionó OK
     */
    @Override
    public boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    /**
     * @param tipo    tipo de alerta (INFORMATION, WARNING, ERROR, CONFIRMATION)
     * @param titulo  título de la ventana
     * @param mensaje contenido del mensaje
     * @see Alert.AlertType
     */
    public void showAlert(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * @return {@code true} si el usuario confirmó iniciar nuevo juego
     */
    public boolean confirmarNuevoJuego() {
        return false;
    }
}
