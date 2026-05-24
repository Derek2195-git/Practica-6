package com.example.practica6.Vista;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;



public class SoundButton extends Button {
    private AudioClip sonido;

    public SoundButton(String texto, String rutaSonido) {
        super(texto);

        try {
            String audioUrl = getClass().getResource(rutaSonido).toExternalForm();
            sonido = new AudioClip(audioUrl);

            addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                if (sonido != null) {
                    sonido.play();
                }
            });

            setStyle("-fx-cursor: hand; -fx-font-weight: bold;");

        } catch (RuntimeException e) {
            System.out.println("No se pudo cargar el audio: " + rutaSonido);
        }
    }

}
