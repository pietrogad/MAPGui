package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerConnServer.client;
import static com.example.clientmapgui.ControllerControllo.switchScene;

public class ControllerSceltaCreazione implements Initializable {
    /**
     * inputbox dell'elemento da aggiungere alla colonna.
     */
    @FXML
    TextField elmtable;
    /**
     * bottone che aggiunge elementi sia all'anteprima.
     */
    @FXML
    Button addelement;
    /**
     * bottone che termina l'inserimento.
     */
    @FXML
    Button stop;
    /**
     * inputbox del numero di colonne.
     */
    @FXML
    TextField nrocolumn;
    /**
     * bottone che imposta il numero di colonne di una tabella.
     */
    @FXML
    Button impostabtn;
    /**
     * label del messaggio di errore.
     */
    @FXML
    Label err;
    /**
     * bottone che cambia la colonna di destinazione dei dati.
     */
    @FXML
    Button btncolumn;
    /**
     * colonna di anteprima contenente i dati inseriti.
     */
    @FXML
    TableColumn<Dati,String> preview;
    /**
     * tabella contenente la colonna di anteprima.
     */
    @FXML
    TableView<Dati> tabpreview;
    /**
     * label contenente il titolo della scena.
     */
    @FXML
    Label titlefield;
    /**
     * Costante che indica il range massimo per la creazioni di un numero casuale.
     */
    final int MAX_BOUND = 100000;
    /**
     * Pattern regex che, l'input del TextField elmtable, deve rispettare.
     */
    final String PATTERN_INPUT_DATI = "-?\\d+(\\.\\d+)?";
    /**
     * Pattern regex che, l'input del TextField nrocolumn, deve rispettare.
     */
    final String PATTERN_NUM_COLONNE = "\\d+";
    /**
     * sentinella utile ad attivare e disattivare il bottone di invio della query.
     */
    Boolean sentinella = false;
    /**
     * arraylist contenente observablelist che contengono i valori delle colonne.
     */
    private ArrayList<ObservableList<Dati>> valoritab = new ArrayList<>();
    /**
     * arraylist contenente le grandezze delle colonne.
     */
    private ArrayList<Integer> grandezze = new ArrayList<>();

    /**
     * Metodo che viene eseguito al caricamento della scena
     * che possiede il controller ControllerSceltaCreazione.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preview.setCellValueFactory(new PropertyValueFactory<>("dato"));
        btncolumn.setVisible(false);
        err.setVisible(false);
        elmtable.setDisable(true);
        addelement.setDisable(true);
        stop.setDisable(true);
    }

    /**
     * Metodo che aggiunge gli elementi inseriti in elmtable, all'interno lista situata in valoritab
     * l'indice della lista contentenuta in valoritab viene ottenuto dal numero che viene esposto all'interno di btncolumn
     * ciò che viene inserito viene mostrato nella tabella tabpreview.
     */
    public void aggDatoColonna(){
        ObservableList<Dati> temp;
        int indicecolonna = Integer.parseInt(btncolumn.getText().substring(1)); //mi da il numero di colonna
        System.out.println("Indice colonna :" + indicecolonna);
        if (elmtable.getText().matches(PATTERN_INPUT_DATI)){
            Dati valore = new Dati(Double.parseDouble(elmtable.getText()));
            temp = valoritab.get(indicecolonna);
            temp.add(valore);
            grandezze.set(indicecolonna, temp.size());
            tabpreview.setItems(temp);
            for(ObservableList<Dati> list : valoritab){
                System.out.println("Lista: " + list.toString());
            }
            for(Integer gr : grandezze){
                System.out.println("Grandezza:" + gr.toString());
            }
            controllaGrandezza();
            impostaStopBtn();
        } else {
            mostraMessErrore("Errore nell'input del dato");
        }
    }

    /**
     * Metodo che imposta il numero di colonne della tabella
     * e di conseguenza il numero di liste in cui memorizzare i dati.
     */
    public void setColumn(){
        if (nrocolumn.getText().matches(PATTERN_NUM_COLONNE)){
            int number = Integer.parseInt(nrocolumn.getText());
            for (int i = 0; i < number; i++) {
                valoritab.add(FXCollections.observableArrayList());
                grandezze.add(0);
            }
            preview.setText("C0");
            btncolumn.setText("C0");
            btncolumn.setVisible(true);
            elmtable.setDisable(false);
            addelement.setDisable(false);
            nrocolumn.setDisable(true);
            impostabtn.setDisable(true);
        } else {
            mostraMessErrore("Errore nell'inserimento");
        }
    }

    /**
     * Metodo che cambia sia l'indice della colonna nella tabella di preview
     * sia ciò che viene mostrato all'interno del bottone.
     */
    public void setBtncolumn() {
        String oldnomecolonna = btncolumn.getText();
        int oldindicecolonna = Integer.parseInt(oldnomecolonna.substring(1)); //
        int newindicecolonna = oldindicecolonna + 1;
        if(oldindicecolonna == (Integer.parseInt(nrocolumn.getText())-1)){
            newindicecolonna = 0;
        }
        btncolumn.setText("C"+ (newindicecolonna));
        tabpreview.setItems(valoritab.get(newindicecolonna));
        preview.setText("C"+ (newindicecolonna));
    }

    /**
     * Metodo che abilita/disabilita il bottone di terminazione.
     */
    public void impostaStopBtn(){
        if(sentinella){
            stop.setDisable(false);
        }else {
            stop.setDisable(true);
        }
    }

    /**
     * Metodo che imposta la sentinella a true se tutte le colonne hanno gli stessi elementi, false altrimenti.
     */
    public void controllaGrandezza() {
        ArrayList<Boolean> verifica = new ArrayList<>();
        for (int i = 0; i < grandezze.size(); i++) {
            for (int j = 0; j < grandezze.size(); j++) {
                if (grandezze.get(i).equals(grandezze.get(j))) {
                    verifica.add(true);
                }else {
                    verifica.add(false);
                }
            }
        }
        sentinella = !verifica.contains(false);
    }
    /**
     * Metodo che crea una nuova tabella e, sulla base di ciò che è stato inserito nelle colonne, la popola.
     * @param event evento che innesca il metodo.
     */
    public void sendQuery(ActionEvent event) {
        Random rand  = new Random();
        int n = rand.nextInt(MAX_BOUND);
        int numRows = valoritab.getFirst().size();
        String msg;
        String tablename = "table" + n;
        ModificaScena(tablename);
        StringBuilder query1 = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tablename).append(" (");
        StringBuilder query2 = new StringBuilder("INSERT INTO ").append(tablename).append(" VALUES ");
        for (int i = 0; i < valoritab.size(); i++) {
            query1.append("C").append(i).append(" DOUBLE");
            if (i < valoritab.size() - 1) {
                query1.append(", ");
            }
        }
        query1.append(");");

        for (int i = 0; i < numRows; i++) {
            query2.append("(");
            for (int j = 0; j < valoritab.size(); j++) {
                query2.append(valoritab.get(j).get(i));
                if (j < valoritab.size() - 1) {
                    query2.append(", ");
                }
            }
            query2.append(")");
            if (i < numRows - 1) {
                query2.append(", ");
            }
        }
        query2.append(";");

        /*System.out.println(query1);
        System.out.println(query2);
        System.out.println(tablename);*/

        try {
            msg = client.createTable(query1.toString(),query2.toString(),tablename);
            if (msg.equals("OK")) {
                /*client.getOut().writeObject(0);
                switchScene(event,"menu");*/
                finalizza(event, tablename);
            } else {
                mostraMessErrore(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    /**
     * Metodo che imposta un messaggio all'interno del label di errore.
     * @param messErrore messaggio da mostrare.
     */
    public void mostraMessErrore(String messErrore){
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            err.setTextFill(Color.GREEN);
            err.setVisible(false);
        });
        err.setText(messErrore);
        err.setTextFill(Color.RED);
        err.setVisible(true);
        pause.play();
    }
    /**
     * Metodo che invia le query ai client e
     * cambia la scena impostando il nome della tabella come nome della finestra.
     * @param event evento che innesca il metodo.
     * @param s nome della tabella da passare allo switch
     */
    public void finalizza(ActionEvent event, String s) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            try {
                client.getOut().writeObject(0);
                switchScene(event,"menu", s);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        pause.play();
    }
    /**
     * Metodo che imposta la tabella all'interno del Label di titolo
     * e disabilita tutti i componenti interagibili escluso btncolumn.
     * @param s nome della tabella.
     */
    public void ModificaScena(String s) {
        titlefield.setTextFill(Color.GREEN);
        titlefield.setText("Hai creato la tabella: " + s);
        nrocolumn.setDisable(true);
        impostabtn.setDisable(true);
        elmtable.setDisable(true);
        addelement.setDisable(true);
        stop.setDisable(true);
    }
}
