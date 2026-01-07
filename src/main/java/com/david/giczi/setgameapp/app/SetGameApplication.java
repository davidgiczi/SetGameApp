package com.david.giczi.setgameapp.app;
;
import com.david.giczi.setgameapp.view.SetGamePane;
import javafx.application.Application;;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class SetGameApplication extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new SetGamePane());
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