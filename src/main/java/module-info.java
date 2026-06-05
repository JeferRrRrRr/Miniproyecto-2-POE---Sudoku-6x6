module com.example.proyectopoesudoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectopoesudoku6x6 to javafx.fxml;
    exports com.example.proyectopoesudoku6x6;
}