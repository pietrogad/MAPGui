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


    public void mostrareProfondita (MouseEvent event){
        labelprofondita.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void send (ActionEvent event) {
        int depth = (int)sliderprofondita.getValue();
        int scelta;
        boolean single = radiosingle.isSelected();
        boolean average = radioaverage.isSelected();
        String filename = namefilefield.getText();
        if (single){
            scelta = 1;
            eseguiMine(depth,scelta,filename);

        } else if (average){
            scelta = 2;
            eseguiMine(depth,scelta,filename);
        }
    }

    public void eseguiMine (int depth, int scelta, String filename){
        PauseTransition tempo = new PauseTransition(Duration.seconds(5));
        try {
            client.mineDedrogramOnServer(depth,scelta,filename);
            filesave.setText("Il file e' stato salvato correttamente");
            filesave.setVisible(true);
            tempo.setOnFinished( e -> Platform.exit());
            tempo.play();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
