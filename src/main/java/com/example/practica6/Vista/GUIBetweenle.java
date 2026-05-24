package com.example.practica6.Vista;

import com.example.practica6.Modelo.Betweenle;
import com.example.practica6.Modelo.Diccionario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class GUIBetweenle extends Application {
    private Diccionario diccionario;
    private Betweenle juego;
    private HBox cuadrosPalabraAlta;
    private HBox cuadrosPalabraBaja;
    private StackPane labelAproxAlta;
    private StackPane labelAproxBaja;
    private Label labelIntentos;
    private VBox contenedorFilas;
    private String textoActual = "";

    @Override
    public void start(Stage stage) throws IOException {
        diccionario = new Diccionario(false);
        juego = new Betweenle("perro", 14);

        VBox contenedorVertical = new VBox(0);
        contenedorVertical.getStyleClass().add("fondo-betweenle");

        HBox topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setAlignment(Pos.CENTER);

        Label labelTitulo = new Label("BETWEENLE");
        labelTitulo.getStyleClass().add("label-titulo");

        labelIntentos = new Label("Intentos: 14/14");
        labelIntentos.getStyleClass().add("label-intentos-top");

        Region espacio1 = new Region();
        Region espacio2 = new Region();
        HBox.setHgrow(espacio1, Priority.ALWAYS);
        HBox.setHgrow(espacio2, Priority.ALWAYS);

        Button btnMenu = new Button("☰");
        Button btnStats = new Button("📊");

        topBar.getChildren().addAll(btnMenu, espacio1, labelTitulo, espacio2, btnStats);

        labelIntentos.setMaxWidth(Double.MAX_VALUE);
        labelIntentos.setAlignment(Pos.CENTER);

        // Parte del centro
        VBox seccionCentral = new VBox(12);
        seccionCentral.setAlignment(Pos.CENTER);
        seccionCentral.setPadding(new Insets(20, 30, 20, 30));
        VBox.setVgrow(seccionCentral, Priority.ALWAYS);

        HBox bloqueAlto = new HBox(4);
        bloqueAlto.setAlignment(Pos.CENTER);

        cuadrosPalabraAlta = crearFilaLimite(juego.getPalabraAlta());
        cuadrosPalabraAlta.getStyleClass().addAll("palabra-limite", "palabra-alta");

        labelAproxAlta = crearBloqueAprox("-");
        labelAproxAlta.getStyleClass().add("label-approx");

        bloqueAlto.getChildren().addAll(labelAproxAlta, cuadrosPalabraAlta);

        contenedorFilas = new VBox(6);
        contenedorFilas.setAlignment(Pos.CENTER);

        ScrollPane scrollFilas = new ScrollPane(contenedorFilas);
        scrollFilas.setFitToWidth(true);
        scrollFilas.setPrefHeight(200);
        scrollFilas.getStyleClass().add("scroll-historial");


        HBox bloqueBajo = new HBox(4);
        bloqueBajo.setAlignment(Pos.CENTER);

        labelAproxBaja = crearBloqueAprox("-");
        labelAproxBaja.getStyleClass().add("label-approx");

        cuadrosPalabraBaja = crearFilaLimite(juego.getPalabraBaja());
        cuadrosPalabraBaja.getStyleClass().addAll("palabra-limite", "palabra-baja");

        bloqueBajo.getChildren().addAll(labelAproxBaja, cuadrosPalabraBaja);

        HBox filaEscribir = new HBox(6);
        filaEscribir.setAlignment(Pos.CENTER);
        filaEscribir.setPadding(new Insets(10, 0, 10, 0));

        Label espaciador = new Label(" ");
        espaciador.getStyleClass().add("label-approx");
        espaciador.setMinWidth(60);

        HBox filasConEspaciador = new HBox(10);
        filasConEspaciador.setAlignment(Pos.CENTER);

        for (int i = 0; i < juego.getPalabraSecreta().length(); i++) {
            Label celda = new Label(" ");
            celda.getStyleClass().addAll("celda-letra", "celda-escritura");
            filaEscribir.getChildren().add(celda);
        }

        filasConEspaciador.getChildren().addAll(espaciador, filaEscribir);

        HBox seccionBoton = new HBox();
        seccionBoton.setAlignment(Pos.CENTER);
        seccionBoton.getStyleClass().add("seccion-entrada");

        SoundButton btnAdivinar = new SoundButton("Adivinar", "/com/example/practica6/mob.mp3");
        btnAdivinar.getStyleClass().add("btn-adivinar");
        seccionBoton.getChildren().add(btnAdivinar);

        seccionCentral.getChildren().addAll(bloqueBajo, filasConEspaciador, bloqueAlto);

        // Final
        contenedorVertical.getChildren().addAll(topBar, labelIntentos, seccionCentral, seccionBoton);

        Scene scene = new Scene(contenedorVertical, 480, 620);

        try {
            scene.getStylesheets().add(getClass().getResource("/com/example/practica6/estilos.css").toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("No se encontró el archivo estilos.css. Verifica que esté en la carpeta resources correcta.");
        }

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case BACK_SPACE:
                    if (!textoActual.isEmpty()) {
                        textoActual = textoActual.substring(0, textoActual.length() - 1);
                        actualizarFilaEscritura(filaEscribir);
                    }
                    break;
                case ENTER:
                    procesarIntento(filaEscribir, btnAdivinar);
                    break;
                default:
                    String letra = e.getText().toLowerCase();
                    if (letra.matches("[a-záéíóúüñ]") &&
                            textoActual.length() < juego.getPalabraSecreta().length()) {
                        textoActual += letra;
                        actualizarFilaEscritura(filaEscribir);
                    }
                    break;
            }
        });

        btnAdivinar.setOnAction(e -> procesarIntento(filaEscribir, btnAdivinar));

        stage.setTitle("Betweenle");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private StackPane crearBloqueAprox(String texto) {
        Label num = new Label(texto);
        num.getStyleClass().add("approx-texto");

        StackPane bloque = new StackPane(num);
        bloque.getStyleClass().add("approx-bloque");
        bloque.setMinWidth(60);
        bloque.setMinHeight(30);
        return bloque;
    }

    private void mostrarAlerta(String texto, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(null);
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    private HBox crearFilaIntento(String palabra, int resultado) {
        HBox fila = new HBox(6);
        fila.setAlignment(Pos.CENTER);

        String estilo;
        if (resultado == 0)       estilo = "celda-correcto";
        else if (resultado == -1) estilo = "celda-bajo";
        else                      estilo = "celda-alto";

        for (char c : palabra.toUpperCase().toCharArray()) {
            Label celda = new Label(String.valueOf(c));
            celda.getStyleClass().addAll("celda-letra", estilo);
            fila.getChildren().add(celda);
        }
        return fila;
    }

    private HBox crearFilaLimite(String palabra) {
        HBox fila = new HBox(6);
        fila.setAlignment(Pos.CENTER);

        for (char c : palabra.toUpperCase().toCharArray()) {
            Label celda = new Label(String.valueOf(c));
            celda.getStyleClass().addAll("celda-letra", "celda-limite");
            fila.getChildren().add(celda);
        }
        return fila;
    }

    private void procesarIntento(HBox filaEscritura, Button btnAdivinar) {
        String intento = textoActual.toLowerCase();

        if (intento.length() != juego.getPalabraSecreta().length()) {
            mostrarAlerta("La palabra debe tener " + juego.getPalabraSecreta().length() + " letras.", Alert.AlertType.WARNING);
            return;
        }
        if (!diccionario.esUnaPalabraValida(intento)) {
            mostrarAlerta("La palabra '" + intento + "' no está en el diccionario.", Alert.AlertType.WARNING);
            return;
        }

        int resultado = juego.adivinarPalabra(intento);

        if (resultado == 2) {
            mostrarAlerta("La palabra está por debajo del límite inferior.", Alert.AlertType.INFORMATION);
            return;
        }
        if (resultado == 3) {
            mostrarAlerta("La palabra está por encima del límite superior.", Alert.AlertType.INFORMATION);
            return;
        }

        // Congelar fila en historial
        HBox filaCongelada = crearFilaIntento(intento, resultado);
        contenedorFilas.getChildren().add(0, filaCongelada);

        // Limpiar fila de escritura
        if (resultado == 0 || juego.juegoGanado()) {
            // Cambiar color de la fila de escritura a verde
            for (javafx.scene.Node node : filaEscritura.getChildren()) {
                Label celda = (Label) node;
                celda.getStyleClass().removeAll("celda-escritura");
                celda.getStyleClass().add("celda-correcto");
            }
        } else {
            textoActual = "";
            actualizarFilaEscritura(filaEscritura);
        }

        // Actualizar límites y aproximaciones
        cuadrosPalabraAlta.getChildren().setAll(crearFilaLimite(juego.getPalabraAlta()).getChildren());
        cuadrosPalabraBaja.getChildren().setAll(crearFilaLimite(juego.getPalabraBaja()).getChildren());
        labelIntentos.setText("Intentos: " + juego.getIntentosRestantes() + "/" + juego.getIntentosTotales());

        String limiteInicial = "a".repeat(juego.getPalabraSecreta().length());
        String limiteFinal   = "z".repeat(juego.getPalabraSecreta().length());

        if (!juego.getPalabraAlta().equalsIgnoreCase(limiteFinal)) {
            double proxAlta = juego.calcularProximidadLimite(juego.getPalabraAlta(), diccionario);
            ((Label) labelAproxAlta.getChildren().get(0)).setText(String.format("%.2f", proxAlta));
        }
        if (!juego.getPalabraBaja().equalsIgnoreCase(limiteInicial)) {
            double proxBaja = juego.calcularProximidadLimite(juego.getPalabraBaja(), diccionario);
            ((Label) labelAproxBaja.getChildren().get(0)).setText(String.format("%.2f", proxBaja));
        }

        if (resultado == 0 || juego.juegoGanado()) {
            mostrarAlerta("¡Felicidades! La palabra era: " + juego.getPalabraSecreta().toUpperCase(), Alert.AlertType.INFORMATION);
            btnAdivinar.setDisable(true);
        } else if (juego.juegoAcabado()) {
            mostrarAlerta("¡Sin intentos! La palabra era: " + juego.getPalabraSecreta().toUpperCase(), Alert.AlertType.WARNING);
            btnAdivinar.setDisable(true);
        }
    }

    private void actualizarFilaEscritura(HBox fila) {
        for (int i = 0; i < fila.getChildren().size(); i++) {
            Label celda = (Label) fila.getChildren().get(i);
            if (i < textoActual.length()) {
                celda.setText(String.valueOf(textoActual.charAt(i)).toUpperCase());
            } else {
                celda.setText(" ");
            }
        }
    }
}
