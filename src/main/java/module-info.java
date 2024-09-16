/**
 * Modulo contenente le classi necessarie al controllo e al corretto funzionamento della GUI.
 */
module com.example.clientmapgui {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.example.clientmapgui;


    opens com.example.clientmapgui to javafx.fxml;
}