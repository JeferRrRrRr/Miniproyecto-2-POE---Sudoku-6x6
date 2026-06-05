package com.example.proyectopoesudoku6x6.utils;


public interface GameAlertHandler {

    void onStart();

    void onPause();

    void onResume();

    void onEnd();

    void onError(String mensajeError);

    void mostrarInfo(String titulo, String mensaje);

    void mostrarError(String titulo, String mensaje);

    void mostrarAdvertencia(String titulo, String mensaje);

    boolean mostrarConfirmacion(String titulo, String mensaje);
}
