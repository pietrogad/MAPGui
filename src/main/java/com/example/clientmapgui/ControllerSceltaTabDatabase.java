package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

import static com.example.clientmapgui.ControllerConnServer.client;
import static com.example.clientmapgui.ControllerControllo.switchScene;

/**
 * Controller della pagina che gestisce la scelta dell'utente
 * tra creazione o importazione di una tabella.
 */
public class ControllerSceltaTabDatabase {
    /**
     * RadioButton indicante l'opzioni di creazione della tabella.
     */
    @FXML
    RadioButton radiocrea;
    /**
     * RadioButton indicante l'opzioni di importazione della tabella.
     */
    @FXML
    RadioButton radioimporta;

    /**
     * Metodo che cambia scena in SceltaCreazione.fxml
     * @param event evento (selezione del RadioButton radiocrea) che innesca lo switch
     * @throws IOException
     */
    public void switchCrea(ActionEvent event) throws IOException {
        client.getOut().writeObject(1);
        switchScene(event,"SceltaCreazione");
    }

    /**
     * Metodo che cambia scena in CaricaTabDatabase.fxml
     * @param event evento (selezione del RadioButton radioimporta) che innesca lo switch
     * @throws IOException
     */
    public void switchImporta(ActionEvent event) throws IOException {
        client.getOut().writeObject(2);
        switchScene(event,"CaricaTabDatabase");
    }
}
