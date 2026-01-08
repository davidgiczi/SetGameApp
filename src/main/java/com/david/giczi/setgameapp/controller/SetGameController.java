package com.david.giczi.setgameapp.controller;;
import com.david.giczi.setgameapp.domain.SetGameLogic;
import com.david.giczi.setgameapp.view.SetGamePane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;
;

public class SetGameController {

    private final SetGameLogic gameLogic;
    private final SetGamePane gamePane;
    private Stage primaryStage;

    public SetGameController() {
        this.gameLogic = new SetGameLogic();
        this.gamePane = new SetGamePane(this);
    }

    public SetGameLogic getGameLogic() {
        return gameLogic;
    }

    public SetGamePane getGamePane() {
        return gamePane;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void getInfoWindow(String title, List<String> cardNameList) {
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(600);
        pane.setPrefHeight(200);
        String PATH = "/patterns/";
        double HR_SHIFT = 0;
        for (String cardName : cardNameList) {
            ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream(PATH + cardName))));
            cardImage.setPreserveRatio(true);
            cardImage.setScaleX(0.2);
            cardImage.setScaleY(0.2);
            cardImage.xProperty().bind(stage.widthProperty().divide(10));
            cardImage.yProperty().bind(stage.heightProperty().divide(10));
            pane.getChildren().add(cardImage);
        }
        Scene scene = new Scene(pane);
        stage.getIcons().add(new Image(
                Objects.requireNonNull(getClass()
                        .getResourceAsStream("/icon/diamond.png"))));
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}