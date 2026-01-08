module com.david.giczi.setgameapp {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.david.giczi.setgameapp.controller;
    opens com.david.giczi.setgameapp.controller to javafx.fxml;
    exports com.david.giczi.setgameapp.app;
    opens com.david.giczi.setgameapp.app to javafx.fxml;
}