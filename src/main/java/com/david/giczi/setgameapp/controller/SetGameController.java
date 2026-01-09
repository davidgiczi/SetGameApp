package com.david.giczi.setgameapp.controller;
import com.david.giczi.setgameapp.domain.SetGameLogic;
import com.david.giczi.setgameapp.view.SetGamePane;
import javafx.stage.Stage;

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showNewCards(){
        gamePane.getChildren().clear();
        gamePane.setTimerText();
        gamePane.setSETResult();
        gamePane.showCards();
        gamePane.cardNameList.clear();
    }

}