package com.example.clientmapgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.clientmapgui.ControllerControllo.switchScene;

public class ControllerConnServer {
    @FXML
    private TextField ipfield;
    @FXML
    private TextField portfield;

    static Client client = null;



    public void connectServer(ActionEvent event) {
            String ip = ipfield.getText();
            int port = Integer.parseInt(portfield.getText());
        try {
            client = new Client(ip,port);
            switchScene(event, "CaricaTabDatabase");
        } catch (IOException e) {
            System.out.println(e);
        }

    }






}