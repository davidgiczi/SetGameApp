package com.david.giczi.setgameapp.controller;
import com.david.giczi.setgameapp.domain.SetGameLogic;
import com.david.giczi.setgameapp.view.SetGamePane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

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
        if( gamePane.isEndOfTheGame() ){
            getEndOfGameProcess();
            return;
        }
        gamePane.getChildren().clear();
        gamePane.setTimerText();
        gamePane.showCards(12);
        setTitle();
        gamePane.cardNameList.clear();
    }

    private boolean getConfirmationAlert(String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(
                Objects.requireNonNull(getClass()
                        .getResourceAsStream("/icon/diamond.png"))));
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText("Would you like to play a new SET game?");
        Optional<ButtonType> option = alert.showAndWait();
        return option.get() == ButtonType.OK;
    }

    public void setTitle(){
            primaryStage.setTitle("SET: " + gamePane.getSetStateValue() +
                    ", Not SET: " + gamePane.getNotSetStateValue() +
                    ", Cards: " + (SetGameLogic.MAX_CARDS - gamePane.getCardIndex()));
    }

    public void getEndOfGameProcess(){
      getGamePane().getTimeline().stop();
      int score = 0 >= gamePane.getSetStateValue() - gamePane.getNotSetStateValue() ? 0 :
            1000 * (gamePane.getSetStateValue() - gamePane.getNotSetStateValue() ) / gamePane.getSec();
      if( getConfirmationAlert("Your score: " + score) ){
            gamePane.initGame();
            primaryStage.setTitle("Let's play SET!");
      }
      else {
          System.exit(0);
      }
    }

}