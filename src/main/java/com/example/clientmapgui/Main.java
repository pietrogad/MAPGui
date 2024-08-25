package com.example.clientmapgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     * Metodo che effettua il setup della finestra sulla base di
     * scena ottenuta da file fxml.
     * @param stage finestra in cui mostrare la scena.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ConnessioneServer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Entrypoint dell'applicazione
     * @param args argomenti presi da cli
     */
    public static void main(String[] args) {
        launch();
    }
}