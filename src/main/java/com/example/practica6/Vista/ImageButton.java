package com.example.practica6.Vista;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class ImageButton extends Button {
    /**
     * Crea un boton el cual es representado por una imagen
     * @param rutaImagen Ruta a la imagen, sea un archivo .png, .jpg, .jpeg, etc.
     * @param alto Altura de la imagen
     * @param ancho Anchura de la imagen
     */
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
