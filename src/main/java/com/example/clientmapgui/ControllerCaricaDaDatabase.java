package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerConnServer.client;

public class ControllerCaricaDaDatabase implements Initializable {
    @FXML
    private Slider sliderprofondita;
    @FXML
    private Label labelprofondita;
    @FXML
    private RadioButton radiosingle;
    @FXML
    private RadioButton radioaverage;
    @FXML
    private TextField namefilefield;
    @FXML
    private Label filesave;
    @FXML
    private TextArea dataarea;
    @FXML
    private Button sendbtn;
    @FXML
    private Button chkbutton;

    ScrollPane scrollpane;
    private boolean sentinella = false;
    private int cont = 0;


    public void mostrareProfondita (MouseEvent event){
        labelprofondita.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendbtn.setDisable(true);
        labelprofondita.setVisible(false);
        filesave.setVisible(false);
        sliderprofondita.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                labelprofondita.setText((int)(sliderprofondita.getValue())+"");
            }
        });
    }

    public void setRadioaverage(ActionEvent event) {
        radioaverage.setSelected(false);
    }

    public void setRadiosingle(ActionEvent event) {
        radiosingle.setSelected(false);
    }

    public void send (ActionEvent event) throws IOException, ClassNotFoundException {
        int depth = (int)sliderprofondita.getValue();
        int scelta;
        boolean single = radiosingle.isSelected();
        boolean average = radioaverage.isSelected();
        String filename = namefilefield.getText();
        if (cont > 0) {
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
        }else {
            mostraMessErrore("Controlla l'esistenza del file");
        }

    }

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

    public void eseguiMine (int depth, int scelta, String filename){
        String res = "";
        try {
            res = client.mineDedrogramOnServer(depth,scelta,filename);
            filesave.setText("Il file e' stato salvato correttamente\nProcedere all'altra scheda per visualizzare il risultato");
            filesave.setTextFill(Color.GREEN);
            filesave.setVisible(true);
            creaScroller();
            dataarea.setText(res);
            spegniComponenti();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void creaScroller() {
        scrollpane = new ScrollPane();
        scrollpane.setContent(dataarea);
        scrollpane.setLayoutX(579);
        scrollpane.setLayoutY(136);
    }
    public void spegniComponenti(){
        sliderprofondita.setDisable(true);
        radioaverage.setDisable(true);
        radiosingle.setDisable(true);
        namefilefield.setDisable(true);
        sendbtn.setDisable(true);
        chkbutton.setDisable(true);
    }
    public void controllaFile() throws IOException, ClassNotFoundException {

        cont++;
        String filename = namefilefield.getText();
        client.getOut().writeObject(filename);
        String message = (String) client.getIn().readObject();
        if (message.equals("File non esiste")) {
            sendbtn.setDisable(false);
            sentinella = true;
            chkbutton.setDisable(true);
            namefilefield.setDisable(true);
        }else {
            mostraMessErrore(message);
            sentinella = false;

        }
    }


}
