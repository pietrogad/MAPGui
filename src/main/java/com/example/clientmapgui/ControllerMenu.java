package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

import static com.example.clientmapgui.ControllerConnServer.client;
import static com.example.clientmapgui.ControllerControllo.switchScene;

/**
 * Controller della pagina che consente di scegliere tra caricare da file o da database.
 */
public class ControllerMenu {
    /**
     * RadioButton indicante l'opzioni di calcolo dal database.
     */
    @FXML
    RadioButton loaddatabase;
    /**
     * RadioButton indicante l'opzioni di importazione da file.
     */
    @FXML
    RadioButton loadfile;
    /**
     * Metodo che cambia scena in CaricaDaDatabase.fxml
     * @param event evento (selezione del RadioButton loaddatabase) che innesca lo switch
     * @throws IOException
     */
    public void caricaDaDatabase (ActionEvent event) throws IOException {
        try{
            client.getOut().writeObject(2);
            switchScene(event,"CaricaDaDatabase");
        }catch (IOException e){
            switchScene(event,"ConnessionePersa");
        }
    }
    /**
     * Metodo che cambia scena in CaricaDaFile.fxml
     * @param event evento (selezione del RadioButton loadfile) che innesca lo switch
     * @throws IOException
     */
    public void caricaDaFile (ActionEvent event) throws IOException {
        try {
            client.getOut().writeObject(1);
            switchScene(event,"CaricaDaFile");
        }catch (IOException e){
            switchScene(event,"ConnessionePersa");
        }
    }
}
