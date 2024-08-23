package com.example.clientmapgui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.clientmapgui.ControllerControllo.switchScene;
import static com.example.clientmapgui.ControllerConnServer.client;


public class ControllerCaricaTabDatabase implements Initializable {
    @FXML
    private Label titlefield;
    @FXML
    private ListView<String> tablelist;
    @FXML
    private Button sndbtn;
    @FXML
    private Label errorfield;

    private ArrayList<String>tables;
    private String message;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sndbtn.setDisable(true);
        errorfield.setVisible(false);
        try{
            tables = client.getData();
            tablelist.getItems().addAll(tables);
            tablelist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    sndbtn.setDisable(false);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void getTable (ActionEvent event) {
        String tablename = tablelist.getSelectionModel().getSelectedItem();
        try {
            message = client.loadDataOnServer(tablename);
            if (message.equals("OK")) {
                switchScene(event, "menu", tablename);
            } else {
                errorfield.setVisible(true);
                errorfield.setText(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
