package com.example.practica6.Vista;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class ShapeButton extends Button {
    public ShapeButton(String texto) {
        super(texto);

        double radio = 35.0;
        Circle formaCircular = new Circle(radio);

        setShape(formaCircular);
        setMinSize(radio * 2, radio * 2);
        setMaxSize(radio * 2, radio * 2);

        getStyleClass().add("button-shape");

    }
}
