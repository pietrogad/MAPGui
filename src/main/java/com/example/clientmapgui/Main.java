package com.example.clientmapgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CaricaDaDatabase.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        /*
        //String ip=args[0];
        String ip="127.0.0.1";
        //int port = Integer.parseInt(args[1]);
        int port = 1234;
        Client client = null;
        try{
            client = new Client(ip,port);

            client.loadDataOnServer();
            int scelta=client.menu();
            if(scelta==1)
                client.loadDedrogramFromFileOnServer();
            else
                client.mineDedrogramOnServer();
        }
        catch (IOException e){
            System.out.println(e);
            return;
        }*/
        launch();
    }
}