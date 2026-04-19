package com.david.giczi.setgameapp.app;

import com.david.giczi.setgameapp.controller.SetGameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.Objects;

public class SetGameApplication extends Application {

    private final SetGameController controller;

    public SetGameApplication() {
        this.controller = new SetGameController();
    }

    @Override
    public void start(Stage stage) {
        stage.setOnCloseRequest(e -> {
            controller.getGamePane().getTimeline().stop();
            if(controller.getConfirmationAlert("Close SET Game", "Would you like to exit?") ){
                System.exit(0);
                return;
            }
            controller.getGamePane().getTimeline().play();
           e.consume();
        });
        controller.setPrimaryStage(stage);
        controller.getGamePane().setOnMouseClicked(mouseEvent -> {
            if( mouseEvent.getButton() == MouseButton.SECONDARY){
                controller.add3NewCards();
                controller.setTitle(false);
            }
            else if( mouseEvent.getButton() == MouseButton.MIDDLE ){
                    controller.collectActualCards();
                    controller.getGameLogic().collectSETByActualCards();
                    controller.setTitle(true);
                    controller.getGamePane().showSETCards();
            }
            else if( mouseEvent.getButton() == MouseButton.PRIMARY &&
                    mouseEvent.getClickCount() == 2 ){
                controller.getEndOfGameProcess();
            }
        });
        Scene scene = new Scene(controller.getGamePane());
        stage.getIcons().add(new Image(
                Objects.requireNonNull(getClass()
                        .getResourceAsStream("/icon/diamond.png"))));
        stage.setTitle("Let's play SET!");
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch();
    }
}