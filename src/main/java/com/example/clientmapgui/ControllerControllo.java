package com.example.clientmapgui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerControllo {

    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader root;

    public static void switchScene(ActionEvent event , String s) throws IOException {
        root = new FXMLLoader(Main.class.getResource(s + ".fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(ActionEvent event, String s, String s1) throws IOException {
        root = new FXMLLoader(Main.class.getResource(s + ".fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle(s1);
        stage.show();
    }


}
