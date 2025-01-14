package com.example.clientmapgui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerControllo.switchScene;

/**
 * Controller della pagina che consente di inserire i dati per il collegamento col server.
 */
public class ControllerConnServer implements Initializable {
    /**
     * inputbox dell'indirizzo IP.
     */
    @FXML
    private TextField ipfield;
    /**
     * inputbox della porta.
     */
    @FXML
    private TextField portfield;
    /**
     * Label del messaggio di errore.
     */
    @FXML
    private Label messlabel;
    /**
     * Bottone di connessione al server.
     */
    @FXML
    Button connectServer;
    /**
     * Pattern che la porta inserita deve rispettare per essere accettata.
     */
    private final String PATTERN_PORT = "\\b(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|[1-5]?[0-9]{1,4})\\b";
    static Client client = null;
    /**
     * Metodo inizializza la connessione col server, instanziando l'oggetto client.
     * @param event evento che innesca il metodo.
     */
    public void connectServer(ActionEvent event) {
            String ip = ipfield.getText();
            int port = Integer.parseInt(portfield.getText());
            if (portfield.getText().matches(PATTERN_PORT)) {
                try {
                    client = new Client(ip,port);
                    switchScene(event, "SceltaTabDatabase");
                } catch (IOException e) {
                    stampaErrore("Connessione rifiutata");
                }
            } else {
                stampaErrore("IP non valido\no porta non valida");
            }

    }
    /**
     * Metodo che viene eseguito al caricamento della scena
     * che possiede il controller ControllerConnServer.
     * Si occupa di nascondere il label di errore.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messlabel.setVisible(false);
        connectServer.setDisable(true);
        ChangeListener<String> textListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean disableButton = ipfield.getText().trim().isEmpty() || portfield.getText().trim().isEmpty();
                connectServer.setDisable(disableButton);
            }
        };
        ipfield.textProperty().addListener(textListener);
        portfield.textProperty().addListener(textListener);
    }

    /**
     * Metodo che imposta un messaggio all'interno del label di errore.
     * @param msg messaggio da mostrare.
     */
    public void stampaErrore(String msg) {
        messlabel.setTextFill(Color.RED);
        messlabel.setText(msg);
        messlabel.setVisible(true);
    }
}