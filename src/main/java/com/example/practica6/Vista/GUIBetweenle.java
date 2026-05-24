package com.example.practica6.Vista;

import com.example.practica6.Modelo.Betweenle;
import com.example.practica6.Modelo.Diccionario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


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
    private VBox tecladoRangos;
    private static MediaPlayer musicaFondo = null;


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
        stage.setOnShown(event -> {scene.getRoot().requestFocus();});
        stage.show();
    }

    public void crearVentanaConfiguracion(Stage stage) {
        Label labelIdioma = new Label("Idioma:");
        labelIdioma.getStyleClass().add("label-config");

        ToggleGroup grupoIdioma = new ToggleGroup();
        RadioButton rbEspanol = new RadioButton("Español");
        RadioButton rbIngles = new RadioButton("Inglés");
        rbEspanol.setToggleGroup(grupoIdioma);
        rbIngles.setToggleGroup(grupoIdioma);
        rbEspanol.setSelected(true);
        rbEspanol.getStyleClass().add("radio-config");
        rbIngles.getStyleClass().add("radio-config");

        HBox filaIdioma = new HBox(20, rbEspanol, rbIngles);
        filaIdioma.setAlignment(Pos.CENTER);

        Label labelDificultad = new Label("Longitud de la palabra:");
        labelDificultad.getStyleClass().add("label-config");

        ToggleGroup grupoDificultad = new ToggleGroup();
        RadioButton rbFacil = new RadioButton("Fácil (5 letras)");
        RadioButton rbMedio = new RadioButton("Medio (6 letras)");
        RadioButton rbCustom = new RadioButton("Personalizado:");
        rbFacil.setToggleGroup(grupoDificultad);
        rbMedio.setToggleGroup(grupoDificultad);
        rbCustom.setToggleGroup(grupoDificultad);
        rbFacil.setSelected(true);
        rbFacil.getStyleClass().add("radio-config");
        rbMedio.getStyleClass().add("radio-config");
        rbCustom.getStyleClass().add("radio-config");

        Slider sliderLongitud = new Slider(7, 14, 7);
        sliderLongitud.setMajorTickUnit(1);
        sliderLongitud.setMinorTickCount(0);
        sliderLongitud.setSnapToTicks(true);
        sliderLongitud.setShowTickLabels(true);
        sliderLongitud.setPrefWidth(200);
        sliderLongitud.setDisable(true);
        sliderLongitud.setVisible(false);

        rbCustom.setOnAction(e -> {
            sliderLongitud.setDisable(false);
            sliderLongitud.setVisible(true);
        });
        rbFacil.setOnAction(e -> {
            sliderLongitud.setDisable(true);
            sliderLongitud.setVisible(false);
        });
        rbMedio.setOnAction(e -> {
            sliderLongitud.setDisable(true);
            sliderLongitud.setVisible(false);
        });

        VBox filaDificultad = new VBox(10, rbFacil, rbMedio, rbCustom, sliderLongitud);
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
        Button btnJugar = new Button("Iniciar juego");
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
            if (rbFacil.isSelected()) longitud = 5;
            else if (rbMedio.isSelected()) longitud = 6;
            else {
                longitud = (int) sliderLongitud.getValue();
            }

            // Leer intentos
            if (rb14.isSelected()) intentos = 14;
            else if (rb12.isSelected()) intentos = 12;
            else intentos = 10;

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
        //juego = new Betweenle("Awful", intentos);
        juego = new Betweenle(diccionario.getPalabraAleatoria(longitud), intentos);

        String cadenaIntentos = esIngles ? "Attempts: " : "Intento: ";
        String cadenaAdivinar = esIngles ? "Take a guess" : "Adivinar";
        String cadenaPista = esIngles ? "Hint" : "Pista";
        String cadenaMenuConfirm = esIngles ? "Go back to menu? Your current game will be lost."
                : "¿Deseas volver al menú? Tu partida actual se borrará.";
        String cadenaSiNo1 = esIngles ? "Yes" : "Sí";
        String cadenaSiNo2 = "No";
        String errorCSS = esIngles ? "Estilos.css file not found. Check your resources folder."
                : "No se encontró el archivo estilos.css. Verifica que esté en la carpeta resources correcta.";

        VBox contenedorVertical = new VBox(0);
        contenedorVertical.getStyleClass().add("fondo-betweenle");

        HBox topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setAlignment(Pos.CENTER);

        Label labelTitulo = new Label("BETWEENLE");
        labelTitulo.getStyleClass().add("label-titulo");

        labelIntentos = new Label(cadenaIntentos + intentos + "/" + intentos);
        labelIntentos.getStyleClass().add("label-intentos-top");

        Region espacio1 = new Region();
        Region espacio2 = new Region();
        HBox.setHgrow(espacio1, Priority.ALWAYS);
        HBox.setHgrow(espacio2, Priority.ALWAYS);

        ImageButton btnMenu = new ImageButton("/com/example/practica6/menu.png", 64, 64);
        ImageButton btnStats = new ImageButton("/com/example/practica6/estadisticas.png", 64, 64);

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

        tecladoRangos = new VBox(6);
        tecladoRangos.setAlignment(Pos.CENTER);
        tecladoRangos.setPadding(new Insets(8, 0, 8, 0));
        actualizarTeclado();

        HBox seccionBoton = new HBox();
        seccionBoton.setAlignment(Pos.CENTER);
        seccionBoton.getStyleClass().add("seccion-entrada");

        SoundButton btnAdivinar = new SoundButton(cadenaAdivinar, "/com/example/practica6/mob.mp3");
        btnAdivinar.getStyleClass().add("btn-adivinar");

        ImageButton btnPista = new ImageButton(cadenaPista, "/com/example/practica6/prueba.png");
        btnPista.getStyleClass().add("btn-pista");
        seccionBoton.getChildren().addAll(btnAdivinar, btnPista);

        seccionCentral.getChildren().addAll(bloqueBajo, filasConEspaciador, bloqueAlto);

        // Final
        contenedorVertical.getChildren().addAll(topBar, labelIntentos, seccionCentral, tecladoRangos, seccionBoton);

        int anchoCelda = 44;   // ancho de cada celda
        int espaciado = 6;    // spacing del HBox
        int padding = 60;   // padding lateral de la sección central
        int anchoAprox = 70;   // espacio del bloque de aproximación

        int anchoMinimo = 480;
        int anchoCalculado = (anchoCelda * longitud) + (espaciado * (longitud - 1)) + (anchoAprox * 2) + padding;
        int anchoVentana = Math.max(anchoMinimo, anchoCalculado);

        Scene scene = new Scene(contenedorVertical, anchoVentana, 620);

        try {
            scene.getStylesheets().add(getClass().getResource("/com/example/practica6/estilos.css").toExternalForm());
        } catch (NullPointerException e) {
            System.out.println(errorCSS);
        }

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case BACK_SPACE:
                    if (!textoActual.isEmpty()) {
                        textoActual = textoActual.substring(0, textoActual.length() - 1);
                        actualizarFilaEscritura(filaEscribir);
                        actualizarTeclado();
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
                        actualizarTeclado();
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
            confirmacionMenu.setContentText(cadenaMenuConfirm);

            ButtonType btnSi = new ButtonType(cadenaSiNo1);
            ButtonType btnNo = new ButtonType(cadenaSiNo2);
            confirmacionMenu.getButtonTypes().setAll(btnSi, btnNo);

            boolean agregar = confirmacionMenu.showAndWait()
                    .map(tipo -> tipo == btnSi)
                    .orElse(false);

            if (!agregar) return;

            javafx.application.Platform.runLater(() -> stage.getScene().getRoot().requestFocus());
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
        if (resultado == 0) estilo = "celda-correcto";
        else if (resultado == -1) estilo = "celda-bajo";
        else estilo = "celda-alto";

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

        // Cadenas traducidas
        String cadenaTamano      = esIngles ? "The word must have " + juego.getPalabraSecreta().length() + " letters."
                : "La palabra debe tener " + juego.getPalabraSecreta().length() + " letras.";
        String cadenaNoEnDic     = esIngles ? "The word '" + intento + "' is not in the dictionary. Add it?"
                : "La palabra '" + intento + "' no está en el diccionario. ¿Deseas añadirla?";
        String cadenaSi          = esIngles ? "Yes" : "Sí";
        String cadenaDefinicion  = esIngles ? "Write the definition of '" + intento.toUpperCase() + "':"
                : "Escribe la definición de '" + intento.toUpperCase() + "':";
        String cadenaFueraBajo   = esIngles ? "Word out of range. Enter a word after: " + juego.getPalabraBaja().toUpperCase()
                : "Palabra fuera del rango. Introduce una palabra después de: " + juego.getPalabraBaja().toUpperCase();
        String cadenaFueraAlto   = esIngles ? "Word out of range. Enter a word before: " + juego.getPalabraAlta().toUpperCase()
                : "Palabra fuera del rango. Introduce una palabra antes de: " + juego.getPalabraAlta().toUpperCase();
        String cadenaGanaste     = esIngles ? "Congratulations, you won! The word was: " + juego.getPalabraSecreta().toUpperCase()
                : "¡Felicidades, ganaste! La palabra era: " + juego.getPalabraSecreta().toUpperCase();
        String cadenaPerdiste    = esIngles ? "Game over! The secret word was: " + juego.getPalabraSecreta().toUpperCase()
                : "Sin intentos. La palabra secreta era: " + juego.getPalabraSecreta().toUpperCase();
        String cadenaIntentos    = esIngles ? "Attempts: " : "Intentos: ";

        if (intento.length() != juego.getPalabraSecreta().length()) {
            mostrarAlerta(cadenaTamano, Alert.AlertType.WARNING);
            return;
        }
        if (!diccionario.esUnaPalabraValida(intento)) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle(null);
            confirmacion.setHeaderText(null);
            confirmacion.setContentText(cadenaNoEnDic);

            ButtonType btnSi = new ButtonType(cadenaSi);
            ButtonType btnNo = new ButtonType("No");
            confirmacion.getButtonTypes().setAll(btnSi, btnNo);

            boolean agregar = confirmacion.showAndWait()
                    .map(tipo -> tipo == btnSi)
                    .orElse(false);

            if (!agregar) return;

            TextInputDialog inputDefinicion = new TextInputDialog();
            inputDefinicion.setTitle(null);
            inputDefinicion.setHeaderText(null);
            inputDefinicion.setContentText(cadenaDefinicion);

            String definicion = inputDefinicion.showAndWait().orElse("").trim();
            if (definicion.isEmpty()) return;

            diccionario.agregarPalabraArchivo(intento, definicion);
        }

        int resultado = juego.adivinarPalabra(intento);

        if (resultado == 2) {
            mostrarAlerta(cadenaFueraBajo, Alert.AlertType.INFORMATION);
            return;
        }
        if (resultado == 3) {
            mostrarAlerta(cadenaFueraAlto, Alert.AlertType.INFORMATION);
            return;
        }

        // Congelar fila en historial
        HBox filaCongelada = crearFilaIntento(intento, resultado);
        contenedorFilas.getChildren().add(0, filaCongelada);

        // Limpiar fila de escritura
        if (resultado == 0 || juego.juegoGanado()) {
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
        labelIntentos.setText(cadenaIntentos + juego.getIntentosRestantes() + "/" + juego.getIntentosTotales());

        String limiteInicial = "a".repeat(juego.getPalabraSecreta().length());
        String limiteFinal = "z".repeat(juego.getPalabraSecreta().length());

        calcularDistancias(limiteInicial, limiteFinal);
        actualizarTeclado();

        if (resultado == 0 || juego.juegoGanado()) {
            mostrarAlerta(cadenaGanaste, Alert.AlertType.INFORMATION);
            btnAdivinar.setDisable(true);
        } else if (juego.juegoAcabado()) {
            mostrarAlerta(cadenaPerdiste, Alert.AlertType.WARNING);
            btnAdivinar.setDisable(true);
        }
    }

    private void calcularDistancias(String limiteInicial, String limiteFinal) {
        double[] distancias = null;
        if (!juego.getPalabraBaja().equals(limiteInicial) || !juego.getPalabraAlta().equals(limiteFinal)) {
            distancias = new double[]{
                    juego.calcularProximidadLimite(juego.getPalabraBaja(), diccionario),
                    juego.calcularProximidadLimite(juego.getPalabraAlta(), diccionario)
            };
        }
        if (distancias != null) {
            ((Label) labelAproxBaja.getChildren().get(0)).setText(String.valueOf(distancias[0]));
            ((Label) labelAproxAlta.getChildren().get(0)).setText(String.valueOf(distancias[1]));
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

        String cadenaIntentosRealizados = esIngles ? "Attempts made: " : "Intentos realizados: ";
        String cadenaNingunIntento      = esIngles ? "No attempts made yet." : "No se ha realizado ningún intento.";
        String cadenaIngresadas         = esIngles ? "Words entered:\n" : "Palabras ingresadas:\n";
        String cadenaLetrasUsadas       = esIngles ? "\nLetters used:\n  " : "\nLetras usadas:\n  ";

        StringBuilder sb = new StringBuilder();
        sb.append(cadenaIntentosRealizados)
                .append(juego.getIntentosTotales() - juego.getIntentosRestantes())
                .append(" / ").append(juego.getIntentosTotales()).append("\n\n");

        if (historial.isEmpty()) {
            sb.append(cadenaNingunIntento);
        } else {
            sb.append(cadenaIngresadas);
            for (int i = 0; i < historial.size(); i++) {
                sb.append("  #").append(i + 1).append(": ")
                        .append(historial.get(i).toUpperCase()).append("\n");
            }
        }

        sb.append(cadenaLetrasUsadas);
        String letras = juego.getLetrasUsadas().stream()
                .sorted(Character::compareTo)
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(", "));
        sb.append(letras.isEmpty() ? "-" : letras);

        mostrarAlerta(sb.toString(), Alert.AlertType.INFORMATION);
    }

    private void usarPista(Button btnPista) {
        String cadenaGanaste = esIngles ? "Congratulations, you won! The word was: "
                : "Felicidades, Ganaste el juego! La palabra era: ";
        String cadenaPerdiste = esIngles ? "Game over! The secret word was: "
                : "El jugador se quedó sin intentos. La palabra secreta era ";


        String cadenaPistaUsada = esIngles ? "You already used your hint. Take a guess, I believe in you."
                : "Ya usaste tu pista en esta partida.";
        String cadenaEligePista = esIngles ? "Choose your hint:" : "Elige tu pista:";
        String cadenaOpc1 = esIngles ? "Move upper limit" : "Recorrer límite alto";
        String cadenaOpc2 = esIngles ? "Move lower limit" : "Recorrer límite bajo";
        String cadenaOpc3 = esIngles ? "Show first letter" : "Mostrar primera letra";
        String cadenaBajaCerca       = esIngles ? "The lower limit is already very close to the secret word." : "El límite de abajo ya está muy cerca de la palabra secreta.";
        String cadenaNuevoBajo       = esIngles ? "New lower limit: " : "Nuevo límite bajo: ";
        String cadenaSinPistaAun     = esIngles ? "Cannot give a hint yet, limits are still at starting positions." : "No se puede dar una pista aún, los límites siguen siendo los iniciales.";
        String cadenaAltaCerca       = esIngles ? "The upper limit is already very close to the secret word." : "El límite de arriba ya está muy cerca de la palabra secreta.";
        String cadenaNuevoAlto       = esIngles ? "New upper limit: " : "Nuevo límite alto: ";
        String cadenaPrimeraLetra    = esIngles ? "The secret word starts with: " : "La palabra secreta empieza con: ";
        String limiteInicial = "a".repeat(juego.getPalabraSecreta().length());
        String limiteFinal = "z".repeat(juego.getPalabraSecreta().length());

        if (juego.isPistaUsada()) {
            mostrarAlerta(cadenaPistaUsada, Alert.AlertType.WARNING);
            return;
        }

        // Diálogo con las 3 opciones
        ChoiceDialog<String> dialogo = new ChoiceDialog<>(
                cadenaOpc1,
                cadenaOpc1,
                cadenaOpc2,
                cadenaOpc3
        );
        dialogo.setTitle("Pista");
        dialogo.setHeaderText(null);
        dialogo.setContentText(cadenaEligePista);

        dialogo.showAndWait().ifPresent(opcion -> {

            if (opcion.equals(cadenaOpc1)) {
                if (juego.getPalabraBaja().equalsIgnoreCase(limiteInicial)) {
                    mostrarAlerta(cadenaBajaCerca, Alert.AlertType.INFORMATION);
                    return;
                }
                String nuevaBaja = juego.recorrerLimites(diccionario, false);
                if (nuevaBaja.isEmpty()) {
                    mostrarAlerta(cadenaBajaCerca, Alert.AlertType.INFORMATION);
                } else {
                    juego.setPalabraBaja(nuevaBaja);
                    cuadrosPalabraBaja.getChildren().setAll(crearFilaLimite(nuevaBaja).getChildren());
                    juego.setPistaUsada(true);
                    btnPista.setDisable(true);
                    mostrarAlerta(cadenaNuevoBajo + nuevaBaja.toUpperCase(), Alert.AlertType.INFORMATION);
                }

            } else if (opcion.equals(cadenaOpc2)) {
                if (juego.getPalabraAlta().equalsIgnoreCase(limiteFinal)) {
                    mostrarAlerta(cadenaSinPistaAun, Alert.AlertType.INFORMATION);
                    return;
                }
                String nuevaAlta = juego.recorrerLimites(diccionario, true);
                if (nuevaAlta.isEmpty()) {
                    mostrarAlerta(cadenaAltaCerca, Alert.AlertType.INFORMATION);
                } else {
                    juego.setPalabraAlta(nuevaAlta);
                    cuadrosPalabraAlta.getChildren().setAll(crearFilaLimite(nuevaAlta).getChildren());
                    juego.setPistaUsada(true);
                    btnPista.setDisable(true);
                    mostrarAlerta(cadenaNuevoAlto + nuevaAlta.toUpperCase(), Alert.AlertType.INFORMATION);
                }

            } else if (opcion.equals(cadenaOpc3)) {
                char primeraLetra = juego.getPalabraSecreta().charAt(0);
                juego.setPistaUsada(true);
                btnPista.setDisable(true);
                mostrarAlerta(cadenaPrimeraLetra + Character.toUpperCase(primeraLetra), Alert.AlertType.INFORMATION);
            }

            calcularDistancias(limiteInicial, limiteFinal);
            actualizarTeclado();
        });

    }

    private void actualizarTeclado() {
        tecladoRangos.getChildren().clear();

        HBox fila1 = new HBox(6);
        HBox fila2 = new HBox(6);
        fila1.setAlignment(Pos.CENTER);
        fila2.setAlignment(Pos.CENTER);

        int pos = Math.min(textoActual.length(), juego.getPalabraBaja().length() - 1);

        char primeraLetra = 'a';
        char ultimaLetra = 'z';

        // Analizar letra por letra hasta pos para saber en qué caso estamos
        boolean dentroRangoBaja = true;  // el prefijo escrito == palabraBaja hasta aquí
        boolean dentroRangoAlta = true;  // el prefijo escrito == palabraAlta hasta aquí

        for (int i = 0; i < pos; i++) {
            char escrita = i < textoActual.length() ? textoActual.charAt(i) : 0;
            char cBaja = juego.getPalabraBaja().charAt(i);
            char cAlta = juego.getPalabraAlta().charAt(i);

            if (escrita != cBaja) dentroRangoBaja = false;
            if (escrita != cAlta) dentroRangoAlta = false;
        }

        char cBajaPos = juego.getPalabraBaja().charAt(pos);
        char cAltaPos = juego.getPalabraAlta().charAt(pos);

        if (dentroRangoBaja && dentroRangoAlta) {
            primeraLetra = cBajaPos;
            ultimaLetra = cAltaPos;
        } else if (dentroRangoBaja) {
            // caso 2
            primeraLetra = cBajaPos;
            ultimaLetra = 'z';
        } else if (dentroRangoAlta) {
            // caso 3, basicamente es alreves jeje
            primeraLetra = 'a';
            ultimaLetra = cAltaPos;
        } else {
            // En caso de que no se cumplan los 3 primeros casos
            boolean dentroDeRango = textoActual.compareTo(juego.getPalabraBaja()) > 0 &&
                    textoActual.compareTo(juego.getPalabraAlta()) < 0;
            // Si esta de todas formas dentro del rango, no hay problema
            if (dentroDeRango) {
                primeraLetra = 'a';
                ultimaLetra = 'z';
            } else {
                // Si se sale del rango gg
                primeraLetra = (char) ('z' + 1);
                ultimaLetra = (char) ('a' - 1);
            }
        }

        for (char c = 'a'; c <= 'z'; c++) {
            ShapeButton tecla = new ShapeButton(String.valueOf(c).toUpperCase(), 15);
            tecla.getStyleClass().add("tecla-letra");

            if (c >= primeraLetra && c <= ultimaLetra) {
                tecla.getStyleClass().add("tecla-valida");
            } else {
                tecla.getStyleClass().add("tecla-invalida");
            }

            if (c <= 'm') fila1.getChildren().add(tecla);
            else fila2.getChildren().add(tecla);
        }

        tecladoRangos.getChildren().addAll(fila1, fila2);
    }

}