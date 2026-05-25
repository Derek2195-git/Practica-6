package com.example.practica6.Vista;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;



public class SoundButton extends Button {
    private AudioClip sonido;

    /**
     * Crea un boton el cual reproduce un sonido al clickearlo
     * @param texto Texto que queremos que posea el boton
     * @param rutaSonido Ruta al archivo de sonido con terminación .mp3, .wav, .ogg, etc.
     */
    public SoundButton(String texto, String rutaSonido) {
        super(texto);

        try {
            String audioUrl = getClass().getResource(rutaSonido).toExternalForm();
            sonido = new AudioClip(audioUrl);

            addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                if (sonido != null) {
                    sonido.play();
                }
            });

            // Le dejamos esto para que parezca seleccionable
            setStyle("-fx-cursor: hand; -fx-font-weight: bold;");

        } catch (RuntimeException e) {
            System.out.println("No se pudo cargar el audio: " + rutaSonido);
        }
    }

}
