package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerConnServer.client;

/**
 * Controller della pagina che consente
 * di scegliere le impostrazioni per il calcolo del dendogramma.
 */
public class ControllerCaricaDaDatabase implements Initializable {
    /**
     * Slider indicante la profondità da calcolare.
     */
    @FXML
    private Slider sliderprofondita;
    /**
     * Label contenente la profondità selezionata.
     */
    @FXML
    private Label labelprofondita;
    /**
     * RadioButton indicante la scelta di SingleLinkDistance.
     */
    @FXML
    private RadioButton radiosingle;
    /**
     * RadioButton indicante la scelta di AverageLinkDistance.
     */
    @FXML
    private RadioButton radioaverage;
    /**
     * InputBox del nome del file.
     */
    @FXML
    private TextField namefilefield;
    /**
     * Label contenente il messaggio di avvenuta accettazione e salvataggio.
     */
    @FXML
    private Label filesave;
    /**
     * TextArea contenete la risposta del server.
     */
    @FXML
    private TextArea dataarea;
    /**
     * Button di invio dei dati.
     */
    @FXML
    private Button sendbtn;
    /**
     * button di controllo del nome del file.
     */
    @FXML
    private Button chkbutton;
    /**
     * Label contenente il titolo della scena.
     */
    @FXML
    private Label titlefield;
    /**
     * Scroll della TextArea.
     */
    private ScrollPane scrollpane;
    /**
     * booleano indicante la disponibilità del filename inserito.
     */
    private boolean sentinella = false;
    /**
     * Pattern che il nome del file deve rispettare per essere accettato.
     */
    private final String PATTERN_FILE = "\\b\\w+\\.dat\\b";
    /**
     * Metodo che rende visibile il Label contenente la profondità selezionata.
     */
    public void mostrareProfondita (){
        labelprofondita.setVisible(true);
    }
    /**
     * Metodo che viene eseguito al caricamento della scena
     * che possiede il controller ControllerCaricaDaDatabase.
     * Si occupa di disabilitare il bottone di invio e i vari label di informazione,
     * inoltre si occupa di mostrare la profondità selezionata nel label profondità.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendbtn.setDisable(true);
        labelprofondita.setVisible(false);
        filesave.setVisible(false);
        sliderprofondita.valueProperty().addListener((arg, oldVal, newVal) -> labelprofondita.setText((int)(sliderprofondita.getValue())+""));
    }
    /**
     * Metodo che imposta il RadioButton radioaverage a false.
     */
    public void setRadioaverage() {
        radioaverage.setSelected(false);
    }
    /**
     * Metodo che imposta il RadioButton radiosingle a false.
     */
    public void setRadiosingle() {
        radiosingle.setSelected(false);
    }
    /**
     * Metodo che invia i dati di profondità, distanza da calcolare e nome file al server,
     * inoltre provvede a ricevere i dati calcolati e a mostrarli in una TextArea.
     */
    public void send (){
        int depth = (int)sliderprofondita.getValue();
        int scelta;
        boolean single = radiosingle.isSelected();
        boolean average = radioaverage.isSelected();
        String filename = namefilefield.getText();
        if (sentinella) {
            if (single){
                scelta = 1;
                eseguiMine(depth,scelta,filename);

            } else if (average){
                scelta = 2;
                eseguiMine(depth,scelta,filename);
            }
        }else {
            mostraMessErrore("Il file esiste già");
        }

    }
    /**
     * Metodo che si occupa di mostrare un messaggio di errore personalizzato nel Label di errore.
     * @param messErrore messaggio di errore.
     */
    public void mostraMessErrore(String messErrore){
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            filesave.setTextFill(Color.GREEN);
            filesave.setVisible(false);
        });
        filesave.setText(messErrore);
        filesave.setTextFill(Color.RED);
        filesave.setVisible(true);
        pause.play();
    }
    /**
     * Metodo che invia i dati di profondità, scelta di distanza da calcolare e nome file al server.
     * @param depth profondità del dendogramma.
     * @param scelta scelta della distanza da calcolare
     * @param filename nome del file scelto.
     */
    public void eseguiMine (int depth, int scelta, String filename){
        String res = "";
        try {
            res = client.mineDendrogramOnServer(depth,scelta,filename);
            mostraMess("Il file e' stato salvato correttamente\nProcedere all'altra scheda per visualizzare il risultato");
            titlefield.setTextFill(Color.GREEN);
            titlefield.setText("I dati calcolati sono disponibili nella scheda successiva \u2192");
            creaScroller();
            dataarea.setText(res);
            spegniComponenti();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Metodo che crea lo scroller della TextArea.
     */
    public void creaScroller() {
        scrollpane = new ScrollPane();
        scrollpane.setContent(dataarea);
        scrollpane.setLayoutX(579);
        scrollpane.setLayoutY(136);
    }
    /**
     * Metodo che spegne tutti i componenti della scena.
     */
    public void spegniComponenti(){
        sliderprofondita.setDisable(true);
        radioaverage.setDisable(true);
        radiosingle.setDisable(true);
        namefilefield.setDisable(true);
        sendbtn.setDisable(true);
        chkbutton.setDisable(true);
    }
    /**
     * Metodo che mostra un messaggio di informazione nel label di informazione.
     * @param s messaggio da mostrare.
     */
    public void mostraMess(String s) {
        PauseTransition tempo = new PauseTransition(Duration.seconds(3));
        filesave.setText(s);
        filesave.setTextFill(Color.GREEN);
        filesave.setVisible(true);
        tempo.setOnFinished( e -> filesave.setVisible(false));
        tempo.play();
    }
    /**
     * Metodo che controlla se il nome del file inserito è gia presente.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void controllaFile() throws IOException, ClassNotFoundException {

        String filename = namefilefield.getText();
        System.out.println(filename);
        if (filename.length()<=10 && filename.matches(PATTERN_FILE)) {
            client.getOut().writeObject(filename);
            String message = (String) client.getIn().readObject();
            if (message.equals("File non esiste")) {
                sendbtn.setDisable(false);
                sentinella = true;
                chkbutton.setDisable(true);
                namefilefield.setDisable(true);
                mostraMess("il nome file è stato accettato");
            }else {
                mostraMessErrore(message);
                sentinella = false;

            }
        } else {
            mostraMessErrore("la lunghezza del nome è maggiore di 10\no non è nel formato corretto (filename.dat)");
            sentinella = false;
        }
    }
}
