package com.david.giczi.setgameapp.view;

import com.david.giczi.setgameapp.controller.SetGameController;
import com.david.giczi.setgameapp.domain.Card;
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
    private int sec;
    private int SET;
    private int notSET;
    private Text SETText;
    private Text notSETText;
    private Text timerText;
    private final Timeline timeline;
    public final List<String> cardNameList;
    public static final double MILLIMETER = 1000 / 224.0;

    public SetGamePane(SetGameController controller) {
        this.controller = controller;
        this.cardNameList = new ArrayList<>();
        this.SET = 0;
        this.notSET = 0;
        showCards();
        setTimerText();
        setSETResult();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> timerText.setText(getTimeFormat(sec++))));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void showCards(){
        List<Card> cards = controller.getGameLogic().getCards(12);
        String PATH = "/patterns/";
        double HR_SHIFT = 0;
        double VR_SHIFT = 0;
        for (int i = 1; i <= cards.size(); i++) {
            ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream(PATH + cards.get(i - 1).toString()))));
            cardImage.setId(cards.get(i - 1).toString());
            cardImage.setOnMouseClicked(c -> setShadowForCardImage(cardImage));
            cardImage.setPreserveRatio(true);
            cardImage.setScaleX(0.2);
            cardImage.setScaleY(0.2);
            cardImage.setCursor(Cursor.HAND);
            cardImage.xProperty().bind(widthProperty().divide(10).subtract(70 * MILLIMETER).add(HR_SHIFT));
            cardImage.yProperty().bind(heightProperty().divide(10).subtract(50 * MILLIMETER).add(VR_SHIFT));
            getChildren().add(cardImage);
            HR_SHIFT += 60 * MILLIMETER;
            if(i % 4 == 0){
                HR_SHIFT = 0;
                VR_SHIFT += 40 * MILLIMETER;
            }
        }
    }

    private void setShadowForCardImage(ImageView cardImage){
        if( cardImage.getEffect() == null ){

            cardImage.setStyle("-fx-effect: dropshadow(gaussian, gray, 50, 0.4, 0, 0);");
            if( isChosenThreeCards(cardImage.getId()) ) {

              if( controller.getGameLogic().isSetCards(cardNameList) ){
                  getInfoWindow("SET");
                  this.SET++;
                  SETText.setText("SET: " + this.SET);
                  deleteSetStateCards();
              }
              else{
                  getInfoWindow("Not SET");
                  this.notSET++;
                  notSETText.setText("Not SET: " + this.notSET);
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

    private void deleteSetStateCards(){
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

    public void setSETResult(){
        SETText = new Text("SET: " + this.SET);
        SETText.setFont(Font.font("Book Antiqua", FontWeight.BOLD, FontPosture.REGULAR, 24));
        SETText.setId("set");
        SETText.xProperty().bind(widthProperty().divide(11).multiply(5));
        SETText.yProperty().bind(heightProperty().divide(15).multiply(13));
        notSETText = new Text("Not SET: " + this.notSET);
        notSETText.setFont(Font.font("Book Antiqua", FontWeight.BOLD, FontPosture.REGULAR, 24));
        notSETText.setId("not-set");
        notSETText.xProperty().bind(widthProperty().divide(11).multiply(5));
        notSETText.yProperty().bind(heightProperty().divide(15).multiply(14));
        getChildren().addAll(SETText, notSETText);
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

}
