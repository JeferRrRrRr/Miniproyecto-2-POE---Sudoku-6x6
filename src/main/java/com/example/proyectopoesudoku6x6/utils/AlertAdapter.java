package com.example.proyectopoesudoku6x6.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class AlertAdapter implements GameAlertHandler {

    @Override
    public void onStart() {}

    @Override
    public void onPause() {}

    @Override
    public void onResume() {}

    @Override
    public void onEnd() {}

    @Override
    public void onError(String mensajeError) {
        mostrarError("Error", mensajeError);
    }

    @Override
    public void mostrarInfo(String titulo, String mensaje) {
        showAlert(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    @Override
    public void mostrarError(String titulo, String mensaje) {
        showAlert(Alert.AlertType.ERROR, titulo, mensaje);
    }

    @Override
    public void mostrarAdvertencia(String titulo, String mensaje) {
        showAlert(Alert.AlertType.WARNING, titulo, mensaje);
    }

    @Override
    public boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    public void showAlert(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public boolean confirmarNuevoJuego() {
        return false;
    }
}
