package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
    @FXML
    private ListView<String> listfilename;
    @FXML
    private Label msglabel;
    @FXML
    private Button sendfilebtn;
    @FXML
    private TextArea areadati;
    @FXML
    private Label titlefield;

    private ScrollPane scrollpane;
    private ArrayList<String> list;
    private String nomefile;
    private String res;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendfilebtn.setDisable(true);
        msglabel.setVisible(false);
        try {
            list = client.getData();
            listfilename.getItems().addAll(list);
            listfilename.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    sendfilebtn.setDisable(false);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendFile (ActionEvent event) throws IOException {
        try {
            nomefile = listfilename.getSelectionModel().getSelectedItem();
            res = client.loadDedrogramFromFileOnServer(nomefile);
            creaScroller();
            areadati.setText(res);
            mostraMess("Il file e' stato caricato correttamente\nProcedere all'altra scheda per visualizzare il risultato");
            spegniComponenti();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void mostraMess(String s) {
        PauseTransition tempo = new PauseTransition(Duration.seconds(3));
        msglabel.setVisible(true);
        msglabel.setText(s);
        msglabel.setTextFill(Color.GREEN);
        titlefield.setTextFill(Color.GREEN);
        titlefield.setText("Il file " + nomefile + " è stato scelto \u2192");
        tempo.setOnFinished( e -> msglabel.setVisible(false));
        tempo.play();
    }
    public void spegniComponenti(){
        sendfilebtn.setDisable(true);
        listfilename.setDisable(true);
    }

    public void creaScroller() {
        scrollpane = new ScrollPane();
        scrollpane.setContent(areadati);
        scrollpane.setLayoutX(579);
        scrollpane.setLayoutY(136);
    }
}
