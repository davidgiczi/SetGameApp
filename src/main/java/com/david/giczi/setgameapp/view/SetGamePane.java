package com.david.giczi.setgameapp.view;

import com.david.giczi.setgameapp.controller.SetGameController;
import com.david.giczi.setgameapp.domain.Card;
import com.david.giczi.setgameapp.domain.SetGameLogic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetGamePane extends AnchorPane {

    private final SetGameController controller;
    private int cardIndex;
    private int sec;
    private int setStateValue;
    private int notSetStateValue;
    private boolean isAdded4MoreCards;
    private Text timerText;
    public List<Card> cardList;
    private final Timeline timeline;
    public final List<String> cardNameList;
    private final String PATH = "/patterns/";
    public static final double MILLIMETER = 1000 / 224.0;

    public SetGamePane(SetGameController controller) {
        this.controller = controller;
        this.cardList = controller.getGameLogic().getCards(81);
        this.cardNameList = new ArrayList<>();
        this.setStateValue = 0;
        this.notSetStateValue = 0;
        setStyle("-fx-background-color: white;");
        showCards(12);
        setTimerText();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                e -> { timerText.setText(getTimeFormat(sec++));
                    if( isEndOfTheGame() ){
                        controller.getEndOfGameProcess();
                    }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public int getSetStateValue() {
        return setStateValue;
    }

    public int getNotSetStateValue() {
        return notSetStateValue;
    }
    public int getSec() {
        return sec;
    }

    public void showCards(int pcs){
        double HR_SHIFT = 0;
        double VR_SHIFT = 0;
        int rowIndex = 0;
        int lastCards = pcs;
        if( isLastCards(pcs) ){
            lastCards = SetGameLogic.MAX_CARDS - cardIndex;
        }
        for (int i = cardIndex; i < cardIndex + lastCards; i++) {
            ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream(PATH + cardList.get(i).toString()))));
            cardImage.setId(cardList.get(i).toString());
            cardImage.setOnMouseClicked(c -> onClickCardProcess(cardImage));
            cardImage.setPreserveRatio(true);
            cardImage.setScaleX(0.2);
            cardImage.setScaleY(0.2);
            cardImage.setCursor(Cursor.HAND);
            cardImage.xProperty().bind(widthProperty().divide(10).subtract(70 * MILLIMETER).add(HR_SHIFT));
            cardImage.yProperty().bind(heightProperty().divide(10).subtract(50 * MILLIMETER).add(VR_SHIFT));
            getChildren().add(cardImage);
            HR_SHIFT += 60 * MILLIMETER;
           if( rowIndex % 4 == 3 ){
                HR_SHIFT = 0;
                VR_SHIFT += 40 * MILLIMETER;
                rowIndex = 0;
                continue;
            }
            rowIndex++;
        }
        if( isLastCards(pcs) ){
            cardIndex = SetGameLogic.MAX_CARDS;
        }
        else{
            cardIndex += 12;
        }
        isAdded4MoreCards = false;
    }

    private void onClickCardProcess(ImageView cardImage){
        if( cardImage.getEffect() == null ){

            cardImage.setStyle("-fx-effect: dropshadow(gaussian, gray, 50, 0.4, 0, 0);");
            if( isChosenThreeCards(cardImage.getId()) ) {

              if( controller.getGameLogic().isSetCards(cardNameList) ){
                  getInfoWindow("SET");
                  this.setStateValue++;
                  clearChosenCardsShadow();
                  if( isAdded4MoreCards || SetGameLogic.MAX_CARDS == cardIndex ){
                      delete3Cards();
                  }
                  else{
                      show3NewCards();
                  }
                  controller.setTitle();
              }
              else{
                  getInfoWindow("Not SET");
                  this.notSetStateValue++;
                  controller.setTitle();
                  clearChosenCardsShadow();
              }
                cardNameList.clear();
            }
        }
        else {
            cardImage.setStyle("-fx-effect: null;");
            cardNameList.remove(cardImage.getId());
        }
    }

    private boolean isChosenThreeCards(String cardId){
        cardNameList.add(cardId);
        return 3 <= cardNameList.size();
    }

    private void clearChosenCardsShadow(){
        for (String cardId : cardNameList) {
            for (Node card : getChildren()) {
                if( card.getId().equals(cardId) ){
                    card.setStyle("-fx-effect: null;");
                }
            }
        }
    }

    private void show3NewCards(){
        int lastCards = 3;
        if( isLastCards(3) ){
            lastCards = SetGameLogic.MAX_CARDS - cardIndex;
        }
        for (int i = 0; i < lastCards; i++) {
            for (Node card : getChildren()) {
                if(card.getId().equals(cardNameList.get(i)) ){
                   ImageView cardImage = (ImageView) getChildren().get(getChildren().indexOf(card));
                   cardImage.setImage(new Image(Objects.requireNonNull(getClass()
                            .getResourceAsStream(PATH + cardList.get(cardIndex).toString()))));
                   cardImage.setId(cardList.get(cardIndex).toString());
                   getChildren().set(getChildren().indexOf(card), cardImage);
                }
            }
            cardIndex++;
        }
    }

    public void add4MoreCards(){
        if( isAdded4MoreCards ){
            return;
        }
        int lastCards = 4;
        if( isLastCards(4) ){
            lastCards = SetGameLogic.MAX_CARDS - cardIndex;
        }
        double HR_SHIFT = 0;
        for (int i = cardIndex; i < cardIndex + lastCards; i++) {
            ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream(PATH + cardList.get(i).toString()))));
            cardImage.setId(cardList.get(i).toString());
            cardImage.setOnMouseClicked(c -> onClickCardProcess(cardImage));
            cardImage.setPreserveRatio(true);
            cardImage.setScaleX(0.2);
            cardImage.setScaleY(0.2);
            cardImage.setCursor(Cursor.HAND);
            cardImage.xProperty().bind(widthProperty().divide(10).subtract(70 * MILLIMETER).add(HR_SHIFT));
            cardImage.yProperty().bind(heightProperty().divide(10).add(70 * MILLIMETER));
            getChildren().add(cardImage);
            HR_SHIFT += 60 * MILLIMETER;
        }
        if( isLastCards(4) ){
            cardIndex = SetGameLogic.MAX_CARDS;
        }
        else{
            cardIndex += 4;
        }
        isAdded4MoreCards = true;
        controller.setTitle();
    }

    private void delete3Cards(){
        for (String cardId : cardNameList) {
            getChildren().removeIf(card -> card.getId().equals(cardId));
        }
    }

    private void getInfoWindow(String title) {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(
                Objects.requireNonNull(getClass()
                        .getResourceAsStream("/icon/diamond.png"))));
        stage.initOwner(controller.getPrimaryStage());
        stage.setWidth(520);
        stage.setHeight(270);
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white;");
        pane.setPrefWidth(520);
        pane.setPrefHeight(270);
        String PATH = "/patterns/";
        double HR_SHIFT = 0;
        for (String cardName : cardNameList) {
            ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream(PATH + cardName))));
            cardImage.setPreserveRatio(true);
            cardImage.setScaleX(0.2);
            cardImage.setScaleY(0.2);
            cardImage.setX(-100 * MILLIMETER + HR_SHIFT);
            cardImage.setY(-40 * MILLIMETER);
            cardImage.setRotate(90);
            pane.getChildren().add(cardImage);
            HR_SHIFT += 35 * MILLIMETER;
        }
        Scene scene = new Scene(pane);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public void setTimerText(){
        timerText = new Text(getTimeFormat(sec));
        timerText.setId("timer");
        timerText.setFont(Font.font("Book Antiqua", FontWeight.BOLD, FontPosture.REGULAR, 64));
        timerText.setFill(Color.ORANGE);
        timerText.xProperty().bind(widthProperty().divide(11).multiply(5));
        timerText.yProperty().bind(heightProperty().divide(15));
        getChildren().add(timerText);
    }

    private String getTimeFormat(int secValue){
        int sec = secValue % 60;
        int min = secValue / 60;
        int hour = secValue / 3600;
        if( hour == 0 ){
            return (10 > min ? "0" + min : min) + ":" + (10 > sec ? "0" + sec : sec);
        }
        return hour + ":" + (10 > min ? "0" + min : min) + ":" + (10 > sec ? "0" + sec : sec);
    }

    public boolean isLastCards(int pcs){
        return  0 >= SetGameLogic.MAX_CARDS - cardIndex - pcs;
    }
    public boolean isEndOfTheGame(){
        return  SetGameLogic.MAX_CARDS == cardIndex && 4 > getChildren().size();
    }
    public void initGamePane(){
        this.sec = 0;
        this.cardIndex = 0;
        this.setStateValue = 0;
        this.notSetStateValue = 0;
        isAdded4MoreCards = false;
        cardNameList.clear();
        cardList = controller.getGameLogic().getCards(81);
        getChildren().clear();
        setTimerText();
        showCards(12);
        getTimeline().play();
    }

}
