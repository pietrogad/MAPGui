package com.example.clientmapgui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConnessionePersa implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PauseTransition tempo = new PauseTransition(Duration.seconds(4));
        tempo.setOnFinished(event -> Platform.exit());
        tempo.play();
    }
}
