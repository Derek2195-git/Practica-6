module com.example.practica6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jdk.xml.dom;
    requires java.desktop;
    requires javafx.media;


    opens com.example.practica6 to javafx.fxml;
    exports com.example.practica6;
    exports com.example.practica6.Controlador;
    opens com.example.practica6.Controlador to javafx.fxml;
    exports com.example.practica6.Vista;
    opens com.example.practica6.Vista to javafx.fxml;
}