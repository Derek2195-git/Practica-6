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
    private boolean esIngles = false;
    private int longitud = 5;
    private int intentos = 14;



    @Override
    public void start(Stage stage) {
        Label titulo = new Label("BETWEENLE");
        titulo.getStyleClass().add("label-titulo");

        Button btnJugar = new Button("Jugar");
        btnJugar.getStyleClass().add("btn-menu");

        Button btnSalir = new Button("Salir");
        btnSalir.getStyleClass().addAll("btn-menu", "btn-salir");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("fondo-betweenle");
        root.getChildren().addAll(titulo, btnJugar, btnSalir);

        btnJugar.setOnAction(e -> {
            try {
                crearVentanaConfiguracion(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnSalir.setOnAction(e -> stage.close());

        Scene scene = new Scene(root, 480, 620);
        try {
            scene.getStylesheets().add(
                    getClass().getResource("/com/example/practica6/estilos.css").toExternalForm());
        } catch (NullPointerException ex) {
            System.out.println("No se encontró estilos.css");
        }

        stage.setTitle("Betweenle");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void crearVentanaConfiguracion(Stage stage) {
        Label labelIdioma = new Label("Idioma:");
        labelIdioma.getStyleClass().add("label-config");

        ToggleGroup grupoIdioma = new ToggleGroup();
        RadioButton rbEspanol = new RadioButton("Español");
        RadioButton rbIngles  = new RadioButton("Inglés");
        rbEspanol.setToggleGroup(grupoIdioma);
        rbIngles.setToggleGroup(grupoIdioma);
        rbEspanol.setSelected(true);
        rbEspanol.getStyleClass().add("radio-config");
        rbIngles.getStyleClass().add("radio-config");

        HBox filaIdioma = new HBox(20, rbEspanol, rbIngles);
        filaIdioma.setAlignment(Pos.CENTER);

        // ── OPCIONES DIFICULTAD ───────────────────────────────────
        Label labelDificultad = new Label("Longitud de la palabra:");
        labelDificultad.getStyleClass().add("label-config");

        ToggleGroup grupoDificultad = new ToggleGroup();
        RadioButton rbFacil  = new RadioButton("Fácil (5 letras)");
        RadioButton rbMedio  = new RadioButton("Medio (6 letras)");
        RadioButton rbCustom = new RadioButton("Personalizado:");
        rbFacil.setToggleGroup(grupoDificultad);
        rbMedio.setToggleGroup(grupoDificultad);
        rbCustom.setToggleGroup(grupoDificultad);
        rbFacil.setSelected(true);
        rbFacil.getStyleClass().add("radio-config");
        rbMedio.getStyleClass().add("radio-config");
        rbCustom.getStyleClass().add("radio-config");

        TextField campoPalabra = new TextField();
        campoPalabra.setPromptText("Longitud (3-12)");
        campoPalabra.setPrefWidth(120);
        campoPalabra.getStyleClass().add("campo-texto");
        campoPalabra.setDisable(true);

        rbCustom.setOnAction(e -> campoPalabra.setDisable(false));
        rbFacil.setOnAction(e  -> campoPalabra.setDisable(true));
        rbMedio.setOnAction(e  -> campoPalabra.setDisable(true));

        HBox filaCustom = new HBox(10, rbCustom, campoPalabra);
        filaCustom.setAlignment(Pos.CENTER_LEFT);

        VBox filaDificultad = new VBox(10, rbFacil, rbMedio, filaCustom);
        filaDificultad.setAlignment(Pos.CENTER);

        // ── OPCIONES INTENTOS ─────────────────────────────────────
        Label labelIntentos = new Label("Número de intentos:");
        labelIntentos.getStyleClass().add("label-config");

        ToggleGroup grupoIntentos = new ToggleGroup();
        RadioButton rb14 = new RadioButton("14 intentos");
        RadioButton rb12 = new RadioButton("12 intentos");
        RadioButton rb10 = new RadioButton("10 intentos");
        rb14.setToggleGroup(grupoIntentos);
        rb12.setToggleGroup(grupoIntentos);
        rb10.setToggleGroup(grupoIntentos);
        rb14.setSelected(true);
        rb14.getStyleClass().add("radio-config");
        rb12.getStyleClass().add("radio-config");
        rb10.getStyleClass().add("radio-config");

        HBox filaIntentos = new HBox(20, rb14, rb12, rb10);
        filaIntentos.setAlignment(Pos.CENTER);

        // ── BOTONES ───────────────────────────────────────────────
        Button btnJugar  = new Button("Iniciar juego");
        Button btnVolver = new Button("Volver");
        btnJugar.getStyleClass().add("btn-menu");
        btnVolver.getStyleClass().addAll("btn-menu", "btn-salir");

        // ── LAYOUT ────────────────────────────────────────────────
        Label titulo = new Label("Configuración");
        titulo.getStyleClass().add("label-titulo");

        VBox root = new VBox(24);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("fondo-betweenle");
        root.getChildren().addAll(
                titulo,
                labelIdioma, filaIdioma,
                labelDificultad, filaDificultad,
                labelIntentos, filaIntentos,
                btnJugar, btnVolver
        );

        // ── ACCIONES ──────────────────────────────────────────────
        btnVolver.setOnAction(e -> start(stage));

        btnJugar.setOnAction(e -> {
            // Leer idioma
            esIngles = rbIngles.isSelected();

            // Leer longitud
            if (rbFacil.isSelected())      longitud = 5;
            else if (rbMedio.isSelected()) longitud = 6;
            else {
                try {
                    longitud = Integer.parseInt(campoPalabra.getText().trim());
                    if (longitud < 7 || longitud > 14) {
                        mostrarAlerta("La longitud debe estar entre 7 y 14.", Alert.AlertType.WARNING);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    mostrarAlerta("Ingresa un número válido para la longitud.", Alert.AlertType.WARNING);
                    return;
                }
            }

            // Leer intentos
            if (rb14.isSelected())      intentos = 14;
            else if (rb12.isSelected()) intentos = 12;
            else                        intentos = 10;

            // Arrancar juego con los valores guardados en los campos
            crearVentanaJuego(stage);
        });

        Scene scene = new Scene(root, 480, 620);
        try {
            scene.getStylesheets().add(
                    getClass().getResource("/com/example/practica6/estilos.css").toExternalForm());
        } catch (NullPointerException ex) {
            System.out.println("No se encontró estilos.css");
        }

        stage.setScene(scene);
        stage.show();
    }

    public void crearVentanaJuego(Stage stage) {
        diccionario = new Diccionario(esIngles);
        juego = new Betweenle(diccionario.getPalabraAleatoria(longitud), intentos);

        VBox contenedorVertical = new VBox(0);
        contenedorVertical.getStyleClass().add("fondo-betweenle");

        HBox topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setAlignment(Pos.CENTER);

        Label labelTitulo = new Label("BETWEENLE");
        labelTitulo.getStyleClass().add("label-titulo");

        labelIntentos = new Label("Intentos: " + intentos + "/" + intentos);
        labelIntentos.getStyleClass().add("label-intentos-top");

        Region espacio1 = new Region();
        Region espacio2 = new Region();
        HBox.setHgrow(espacio1, Priority.ALWAYS);
        HBox.setHgrow(espacio2, Priority.ALWAYS);

        ImageButton btnMenu = new ImageButton("/com/example/practica6/menu.png",64,64);
        ImageButton btnStats = new ImageButton( "/com/example/practica6/estadisticas.png",64,64);

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

        ImageButton btnPista = new ImageButton("Pista", "/com/example/practica6/prueba.png");
        btnPista.getStyleClass().add("btn-pista");
        seccionBoton.getChildren().addAll(btnAdivinar, btnPista);

        seccionCentral.getChildren().addAll(bloqueBajo, filasConEspaciador, bloqueAlto);

        // Final
        contenedorVertical.getChildren().addAll(topBar, labelIntentos, seccionCentral, seccionBoton);

        int anchoCelda = 44;   // ancho de cada celda
        int espaciado  = 6;    // spacing del HBox
        int padding    = 60;   // padding lateral de la sección central
        int anchoAprox = 70;   // espacio del bloque de aproximación

        int anchoMinimo  = 480;
        int anchoCalculado = (anchoCelda * longitud) + (espaciado * (longitud - 1)) + (anchoAprox * 2) + padding;
        int anchoVentana = Math.max(anchoMinimo, anchoCalculado);

        Scene scene = new Scene(contenedorVertical, anchoVentana, 620);

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
            scene.getRoot().requestFocus();
        });

        btnAdivinar.setOnAction(e -> {
            procesarIntento(filaEscribir, btnAdivinar);
            scene.getRoot().requestFocus();
        });

        btnMenu.setOnAction(e -> {
            Alert confirmacionMenu = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacionMenu.setTitle(null);
            confirmacionMenu.setHeaderText(null);
            confirmacionMenu.setContentText("¿Deseas volver al menú? Tu partida actual se borrará.");
            confirmacionMenu.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            boolean agregar = confirmacionMenu.showAndWait()
                    .map(tipo -> tipo == ButtonType.YES)
                    .orElse(false);

            scene.getRoot().requestFocus();

            if (!agregar) return;


            start(stage);
        });

        btnStats.setOnAction(e -> {
            mostrarEstadisticas();
            scene.getRoot().requestFocus();
        });

        btnPista.setOnAction(e -> {
            usarPista(btnPista);
            scene.getRoot().requestFocus();
        });



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
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle(null);
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("La palabra '" + intento.toLowerCase() + "' no está en el diccionario. ¿Deseas añadirla?");
            confirmacion.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            boolean agregar = confirmacion.showAndWait()
                    .map(tipo -> tipo == ButtonType.YES)
                    .orElse(false);

            if (!agregar) return;

            // Pedir definición
            TextInputDialog inputDefinicion = new TextInputDialog();
            inputDefinicion.setTitle(null);
            inputDefinicion.setHeaderText(null);
            inputDefinicion.setContentText("Escribe la definición de '" + intento.toUpperCase() + "':");

            String definicion = inputDefinicion.showAndWait().orElse("").trim();
            if (definicion.isEmpty()) return;

            diccionario.agregarPalabraArchivo(intento, definicion);
            // Continuar con el intento normalmente
        }

        int resultado = juego.adivinarPalabra(intento);

        if (resultado == 2) {
            mostrarAlerta("La palabra introducida está fuera del rango actual. " +
                    "Introduce una palabra que se ubique *despues* de " + juego.getPalabraBaja().toUpperCase(),
                    Alert.AlertType.INFORMATION);
            return;
        }
        if (resultado == 3) {
            mostrarAlerta("La palabra introducida está fuera del rango actual. " +
                            "Introduce una palabra que se ubique *antes* de " + juego.getPalabraAlta().toUpperCase(),
                    Alert.AlertType.INFORMATION);
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

        double[] distancias = null;
        if (!juego.getPalabraBaja().equals(limiteInicial) || !juego.getPalabraAlta().equals(limiteFinal)) {
            distancias = new double[]{
                    juego.calcularProximidadLimite(juego.getPalabraBaja(), diccionario),
                    juego.calcularProximidadLimite(juego.getPalabraAlta(), diccionario)
            };
        }

        if (distancias != null) {
            ((Label) labelAproxBaja.getChildren().get(0)).setText(String.format("%.2f", distancias[0]));
            ((Label) labelAproxAlta.getChildren().get(0)).setText(String.format("%.2f", distancias[1]));
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

    private void mostrarEstadisticas() {
        java.util.ArrayList<String> historial = juego.getHistorialPalabras();

        StringBuilder sb = new StringBuilder();
        sb.append("Intentos realizados: ")
                .append(juego.getIntentosTotales() - juego.getIntentosRestantes())
                .append(" / ").append(juego.getIntentosTotales()).append("\n\n");

        if (historial.isEmpty()) {
            sb.append("No se ha realizado ningún intento.");
        } else {
            sb.append("Palabras ingresadas:\n");
            for (int i = 0; i < historial.size(); i++) {
                sb.append("  #").append(i + 1).append(": ")
                        .append(historial.get(i).toUpperCase()).append("\n");
            }
        }

        sb.append("\nLetras usadas:\n  ");
        String letras = juego.getLetrasUsadas().stream()
                .sorted(Character::compareTo)
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(", "));
        sb.append(letras.isEmpty() ? "-" : letras);

        mostrarAlerta(sb.toString(), Alert.AlertType.INFORMATION);
    }

    private void usarPista(Button btnPista) {
        String limiteInicial = "a".repeat(juego.getPalabraSecreta().length());
        String limiteFinal   = "z".repeat(juego.getPalabraSecreta().length());

        if (juego.isPistaUsada()) {
            mostrarAlerta("Ya usaste tu pista en esta partida.", Alert.AlertType.WARNING);
            return;
        }

        // Diálogo con las 3 opciones
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(
                "Recorrer límite alto",
                "Recorrer límite alto",
                "Recorrer límite bajo",
                "Mostrar primera letra"
        );
        dialogo.setTitle("Pista");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Elige tu pista:");

        dialogo.showAndWait().ifPresent(opcion -> {
            switch (opcion) {
                case "Recorrer límite alto":
                    if (juego.getPalabraBaja().equalsIgnoreCase(limiteInicial)) {
                        mostrarAlerta("El límite de abajo ya está muy cerca de la palabra secreta.", Alert.AlertType.INFORMATION);
                        return;
                    }
                    String nuevaBaja = juego.recorrerLimites(diccionario, false);
                    if (nuevaBaja.isEmpty()) {
                        mostrarAlerta("El límite bajo ya está muy cerca de la palabra secreta.", Alert.AlertType.INFORMATION);
                    } else {
                        juego.setPalabraBaja(nuevaBaja);
                        cuadrosPalabraBaja.getChildren().setAll(crearFilaLimite(nuevaBaja).getChildren());
                        juego.setPistaUsada(true);
                        btnPista.setDisable(true);
                        mostrarAlerta("Nuevo límite bajo: " + nuevaBaja.toUpperCase(), Alert.AlertType.INFORMATION);
                    }
                    break;

                case "Recorrer límite bajo":
                    if (juego.getPalabraAlta().equalsIgnoreCase(limiteFinal)) {
                        mostrarAlerta("No se puede dar una pista aún, los límites siguen siendo los iniciales.", Alert.AlertType.INFORMATION);
                        return;
                    }
                    String nuevaAlta = juego.recorrerLimites(diccionario, true);
                    if (nuevaAlta.isEmpty()) {
                        mostrarAlerta("El límite de arriba ya está muy cerca de la palabra secreta.", Alert.AlertType.INFORMATION);
                    } else {
                        juego.setPalabraAlta(nuevaAlta);
                        cuadrosPalabraAlta.getChildren().setAll(crearFilaLimite(nuevaAlta).getChildren());
                        juego.setPistaUsada(true);
                        btnPista.setDisable(true);
                        mostrarAlerta("Nuevo límite alto: " + nuevaAlta.toUpperCase(), Alert.AlertType.INFORMATION);
                    }
                    break;

                case "Mostrar primera letra":
                    char primeraLetra = juego.getPalabraSecreta().charAt(0);
                    juego.setPistaUsada(true);
                    btnPista.setDisable(true);
                    mostrarAlerta("La palabra secreta empieza con: " + Character.toUpperCase(primeraLetra), Alert.AlertType.INFORMATION);
                    break;
            }
        });
    }

}
