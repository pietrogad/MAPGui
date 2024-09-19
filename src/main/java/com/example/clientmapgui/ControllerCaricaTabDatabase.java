package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerControllo.switchScene;
import static com.example.clientmapgui.ControllerConnServer.client;

/**
 * Controller della pagina che consente di scegliere tra una delle tabelle del database già esistenti.
 */
public class ControllerCaricaTabDatabase implements Initializable {
    /**
     * Lista contenente i nomi delle tabelle dei database.
     */
    @FXML
    private ListView<String> tablelist;
    /**
     * bottone che conferma e invia il nome della tabella selezionata.
     */
    @FXML
    private Button sndbtn;
    /**
     * Label contenente il messaggio di errore.
     */
    @FXML
    private Label errorfield;
    /**
     * ArrayList contenente il nome delle tabelle.
     */
    private ArrayList<String>tables;
    /**
     * messaggio che il client riceve dal server.
     */
    private String message;

    /**
     * Metodo che viene eseguito al caricamento della scena
     * che possiede il controller ControllerCaricaTabDatabase.
     * Si occupa di recuperare il nome delle tabelle e inserirle all'interno della ListView tablelist.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sndbtn.setDisable(true);
        errorfield.setVisible(false);
        try{
            tables = client.getData();
            tablelist.getItems().addAll(tables);
            tablelist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    sndbtn.setDisable(false);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            try {
                switchScene(new ActionEvent(), "ConnessionePersa");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Metodo che invia il nome della tabella selezionata al server.
     * @param event evento che innesca il metodo.
     */
    public void getTable (ActionEvent event) throws IOException {
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(e -> {
            errorfield.setVisible(false);
            Platform.exit();
        });
        String tablename = tablelist.getSelectionModel().getSelectedItem();
        try {
            message = client.loadDataOnServer(tablename);
            if (message.equals("OK")) {
                switchScene(event, "Menu", tablename);
            } else {
                errorfield.setVisible(true);
                errorfield.setText(message);
                pause.play();
            }
        } catch (IOException | ClassNotFoundException e) {
            switchScene(event,"ConnessionePersa");
        }
    }
}
