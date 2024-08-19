package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private Label titlefield;

    private ArrayList<String> list;
    private String nomefile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            list = client.getFilename();
            listfilename.getItems().addAll(list);
            listfilename.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    titlefield.setText(listfilename.getSelectionModel().getSelectedItem());
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendFile (ActionEvent event) throws IOException {
        PauseTransition tempo = new PauseTransition(Duration.seconds(5));
        try {
            nomefile = titlefield.getText();
            client.loadDedrogramFromFileOnServer(nomefile);
            titlefield.setText("file caricato correttamente");
            titlefield.setTextFill(Color.GREEN);
            tempo.setOnFinished( e -> Platform.exit());
            tempo.play();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
