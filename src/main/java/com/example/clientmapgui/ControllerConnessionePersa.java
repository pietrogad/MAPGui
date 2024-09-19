package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller della pagina che viene mostrata quando il client perde la connessione con il server.
 */
public class ControllerConnessionePersa implements Initializable {
    /**
     * Metodo che viene eseguito al caricamento della scena.
     * Si occupa di mostrare la pagina per 4 secondi e successivamente interrompere l'esecuzione del client.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PauseTransition tempo = new PauseTransition(Duration.seconds(4));
        tempo.setOnFinished(event -> Platform.exit());
        tempo.play();
    }
}
