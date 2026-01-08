package com.david.giczi.setgameapp.view;
import com.david.giczi.setgameapp.controller.SetGameController;
import com.david.giczi.setgameapp.domain.Card;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetGamePane extends AnchorPane {

    private final SetGameController controller;
    private final List<String> cardNameList;
    public static final double MILLIMETER = 1000 / 224.0;

    public SetGamePane(SetGameController controller) {
        this.controller = controller;
        this.cardNameList = new ArrayList<>();
        showCards();
    }

    private void showCards(){
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
            cardImage.xProperty().bind(widthProperty().divide(10).subtract(100 * MILLIMETER).add(HR_SHIFT));
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
                  controller.getInfoWindow("SET", cardNameList);
              }
              else{
                  controller.getInfoWindow("Nem SET", cardNameList);
              }
                clearChosenCardsShadow();
                cardNameList.clear();
            }
        }
        else {
            cardImage.setStyle("-fx-effect: null;");
        }
    }

    private boolean isChosenThreeCards(String cardId){
        cardNameList.add(cardId);
        if( 3 > cardNameList.size() ){
            return false;
        }
        return true;
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

}
