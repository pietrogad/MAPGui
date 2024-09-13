package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerControllo.switchScene;

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
     * Pattern che l'indirizzo IP inserito deve rispettare per essere accettato.
     */
    private final String PATTERN_IP = "\\b((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\b";
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
            if (ip.matches(PATTERN_IP) && portfield.getText().matches(PATTERN_PORT)) {
                try {
                    client = new Client(ip,port);
                    switchScene(event, "SceltaTabDatabase");
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else {
                messlabel.setTextFill(Color.RED);
                messlabel.setText("IP non valido\no porta non valida");
                messlabel.setVisible(true);
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
    }
}