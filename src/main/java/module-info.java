module com.example.clientmapgui {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.example.clientmapgui;


    opens com.example.clientmapgui to javafx.fxml;
}