package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

import static com.example.clientmapgui.ControllerConnServer.client;
import static com.example.clientmapgui.ControllerControllo.switchScene;

public class ControllerMenu {
    @FXML
    RadioButton loaddatabase;
    @FXML
    RadioButton loadfile;

    public void caricaDaDatabase (ActionEvent event) throws IOException {
        client.getOut().writeObject(2);
        switchScene(event,"CaricaDaDatabase");
    }

    public void caricaDaFile (ActionEvent event) throws IOException {
        client.getOut().writeObject(1);
        switchScene(event,"CaricaDaFile");
    }
}
