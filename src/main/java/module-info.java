module com.example.proyectopoesudoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.proyectopoesudoku6x6 to javafx.fxml;
    opens com.example.proyectopoesudoku6x6.controllers to javafx.fxml;
    opens com.example.proyectopoesudoku6x6.views to javafx.fxml;
    opens com.example.proyectopoesudoku6x6.models to javafx.fxml;

    exports com.example.proyectopoesudoku6x6;
}
