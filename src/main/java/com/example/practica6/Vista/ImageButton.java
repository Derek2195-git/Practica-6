package com.example.practica6.Vista;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ImageButton extends Button {
    public ImageButton(String rutaImagen, int alto, int ancho) {
        super();
        try {
            Image img = new Image(getClass().getResource(rutaImagen).toExternalForm());
            ImageView iconView = new ImageView(img);

            iconView.setFitHeight(alto);
            iconView.setFitWidth(ancho);
            iconView.setPreserveRatio(true);

            setBackground(Background.fill(Color.TRANSPARENT));
            setGraphic(iconView);
            setStyle("-fx-cursor: hand");
        } catch (RuntimeException e) {
            System.out.println("No se pudo cargar la imagen con esta ruta:" + rutaImagen);
        }

    }
    public ImageButton(String texto, String rutaImagen) {
        super(texto);
        try {
            Image img = new Image(getClass().getResource(rutaImagen).toExternalForm());
            ImageView iconView = new ImageView(img);

            iconView.setFitHeight(20);
            iconView.setFitWidth(20);
            iconView.setPreserveRatio(true);

            setBackground(Background.fill(Color.TRANSPARENT));
            setGraphic(iconView);
            setStyle("-fx-cursor: hand");
        } catch (RuntimeException e) {
            System.out.println("No se pudo cargar la imagen con esta ruta:" + rutaImagen);
        }

    }
}
