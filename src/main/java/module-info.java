module com.example.setgameapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.setgameapp to javafx.fxml;
    exports com.example.setgameapp;
}