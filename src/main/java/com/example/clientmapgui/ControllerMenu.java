package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

import static com.example.clientmapgui.ControllerControllo.switchScene;

public class ControllerMenu {
    @FXML
    RadioButton loaddatabase;
    @FXML
    RadioButton loadfile;

    public void caricaDaDatabase (ActionEvent event) throws IOException {
        switchScene(event,"CaricaDaDatabase");
    }

    public void caricaDaFile (ActionEvent event) throws IOException {
        switchScene(event,"CaricaDaFile");
    }
}
