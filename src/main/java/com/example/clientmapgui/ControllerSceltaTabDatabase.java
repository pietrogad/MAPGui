package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

import static com.example.clientmapgui.ControllerConnServer.client;
import static com.example.clientmapgui.ControllerControllo.switchScene;

public class ControllerSceltaTabDatabase {
    @FXML
    RadioButton radiocrea;
    @FXML
    RadioButton radioimporta;

    public void switchCrea(ActionEvent event) throws IOException {
        client.getOut().writeObject(1);
        switchScene(event,"SceltaCreazione");
    }

    public void switchImporta(ActionEvent event) throws IOException {
        client.getOut().writeObject(2);
        switchScene(event,"CaricaTabDatabase");
    }
}
