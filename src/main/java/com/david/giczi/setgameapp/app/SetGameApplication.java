package com.david.giczi.setgameapp.app;
import com.david.giczi.setgameapp.controller.SetGameController;;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class SetGameApplication extends Application {

    private final SetGameController controller;

    public SetGameApplication() {
        this.controller = new SetGameController();
    }

    @Override
    public void start(Stage stage) {
        controller.setPrimaryStage(stage);
        Scene scene = new Scene(controller.getGamePane());
        stage.getIcons().add(new Image(
                Objects.requireNonNull(getClass()
                        .getResourceAsStream("/icon/diamond.png"))));
        stage.setTitle("Let's play Set!");
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch();
    }
}