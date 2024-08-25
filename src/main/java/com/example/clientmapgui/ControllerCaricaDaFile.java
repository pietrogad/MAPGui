package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerConnServer.client;

public class ControllerCaricaDaFile implements Initializable {
    /**
     * Lista selezionabile contenente il nome dei file.
     */
    @FXML
    private ListView<String> listfilename;
    /**
     * Label di messaggio.
     */
    @FXML
    private Label msglabel;
    /**
     * Bottone di invio del file.
     */
    @FXML
    private Button sendfilebtn;
    /**
     * Area contenente i dati ricevuti dal server.
     */
    @FXML
    private TextArea areadati;
    /**
     * Label contenente il titolo della scena
     */
    @FXML
    private Label titlefield;
    /**
     * Scoller della TextArea.
     */
    private ScrollPane scrollpane;
    /**
     * ArrayList contenente i nomi dei file.
     */
    private ArrayList<String> list;
    /**
     * Stringa indicante il nome del file.
     */
    private String nomefile;
    /**
     * Stringa contenente il risultato inviato dal server.
     */
    private String res;
    /**
     * Metodo che viene eseguito al caricamento della scena
     * che possiede il controller ControllerCaricaDaFile.
     * Si occupa di recuperare i nomi dei file e disabilitare sia il bottone di invio file sia il label di errore.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendfilebtn.setDisable(true);
        msglabel.setVisible(false);
        try {
            list = client.getData();
            listfilename.getItems().addAll(list);
            listfilename.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> sendfilebtn.setDisable(false));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Metodo che invia al server il nome del file e
     * recupera i dati inviati come risposta. I dati ricevuti vengono inseriti all'interno di una TextArea.
     * @throws IOException
     */
    public void sendFile () throws IOException {
        try {
            nomefile = listfilename.getSelectionModel().getSelectedItem();
            res = client.loadDedrogramFromFileOnServer(nomefile);
            titlefield.setTextFill(Color.GREEN);
            titlefield.setText("Il file " + nomefile + " è stato scelto \u2192");
            creaScroller();
            areadati.setText(res);
            mostraMess("Il file e' stato caricato correttamente\nProcedere all'altra scheda per visualizzare il risultato");
            spegniComponenti();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Metodo che mostra un messaggio nel label messaggi.
     * @param s messaggio da mostrare.
     */
    public void mostraMess(String s) {
        PauseTransition tempo = new PauseTransition(Duration.seconds(3));
        msglabel.setVisible(true);
        msglabel.setText(s);
        msglabel.setTextFill(Color.GREEN);
        tempo.setOnFinished( e -> msglabel.setVisible(false));
        tempo.play();
    }
    /**
     * Metodo che spegne i componenti della scheda.
     */
    public void spegniComponenti(){
        sendfilebtn.setDisable(true);
        listfilename.setDisable(true);
    }
    /**
     * Metodo che crea uno scroller per la TextArea.
     */
    public void creaScroller() {
        scrollpane = new ScrollPane();
        scrollpane.setContent(areadati);
        scrollpane.setLayoutX(579);
        scrollpane.setLayoutY(136);
    }
}
