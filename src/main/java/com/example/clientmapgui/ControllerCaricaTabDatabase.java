package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerControllo.switchScene;
import static com.example.clientmapgui.ControllerConnServer.client;

public class ControllerCaricaTabDatabase implements Initializable {

    @FXML
    private TextField tablefield;
    @FXML
    Label errorlabel;

    String message;

    public void connectDb(ActionEvent event) {
        String tablename = tablefield.getText();
        try {
            message = client.loadDataOnServer(tablename);
            if (message.equals("OK")) {
                switchScene(event, "menu");
            }else {
                errorlabel.setVisible(true);
                errorlabel.setText(message);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorlabel.setVisible(false);
    }
}
