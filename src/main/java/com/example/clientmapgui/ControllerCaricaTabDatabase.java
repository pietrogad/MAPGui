package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.clientmapgui.ControllerControllo.switchScene;
import static com.example.clientmapgui.ControllerConnServer.client;

public class ControllerCaricaTabDatabase {

    @FXML
    private TextField tablefield;

    public void connectDb(ActionEvent event) {
        String tablename = tablefield.getText();
        try {
            client.loadDataOnServer(tablename);
            switchScene(event, "menu");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
