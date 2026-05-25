package com.example.practica6.Vista;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ShapeButton extends Button {
    /**
     * Crea un boton el cual tiene una forma elegida por el usuario
     * @param texto Texto que se quiere que tenga el boton
     * @param radio Radio con el que se calculará el boton
     * @param opcion Forma que se quiere para el boton, 0 - Circulo, 1 - Estrella. Por defecto se pone la opción 0.
     */
    public ShapeButton(String texto, double radio, int opcion) {
        super(texto);
        switch (opcion) {
            case 0 -> {
                // Esto definitivamente entra a mi top de perdidas de tiempo, rip
                Circle formaCircular = new Circle(radio);
                setShape(formaCircular);
                setMinSize(radio * 2, radio * 2);
                setMaxSize(radio * 2, radio * 2);

                formaCircular.getStyleClass().add("circulo");
                Label labelTexto = new Label(texto);
                labelTexto.getStyleClass().add("label-shape");


                StackPane pila = new StackPane(formaCircular, labelTexto);
                setGraphic(pila);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 0;");
            }
            case 1 -> {
                // Para las pistas
                Polygon formaEstrella = new Polygon();
                double radioInterno = radio / 2.5;

                for (int i = 0; i < 8; i++) {
                    double angulo = i * Math.PI / 4 - Math.PI / 2;
                    double rActual = (i % 2 == 0) ? radio : radioInterno;
                    formaEstrella.getPoints().addAll(
                            rActual * Math.cos(angulo),
                            rActual * Math.sin(angulo)
                    );
                }

                formaEstrella.getStyleClass().add("estrella");
                Label labelTexto = new Label(texto);
                labelTexto.getStyleClass().add("label-shape");


                StackPane pila = new StackPane(formaEstrella, labelTexto);
                setGraphic(pila);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                // Hacemos de este boton uno que parezca clickeable
                setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 0;");
            }
            default -> {
                Circle formaCircular = new Circle(radio);
                setShape(formaCircular);
                setMinSize(radio * 2, radio * 2);
                setMaxSize(radio * 2, radio * 2);
            }
        }

        getStyleClass().add("button-shape");

    }
}
