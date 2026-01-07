package com.david.giczi.setgameapp.domain;
import com.david.giczi.setgameapp.utils.Feature;
import java.util.ArrayList;
import java.util.List;

public class SetGameLogic {

    private static final Feature[] NUMBER = {Feature.one, Feature.two, Feature.three};
    private static final Feature[] COLOR = {Feature.red, Feature.green, Feature.purple};
    private static final Feature[] FORM = {Feature.capsule, Feature.cell, Feature.square};
    private static final Feature[] FILLING = {Feature.empty, Feature.filled, Feature.striped};
    private static final int MAX_CARDS = 81;

    public static List<Card> getCards(int numberOfCards){
        List<Card> cards = new ArrayList<>();
        if( numberOfCards > MAX_CARDS ){
            return cards;
        }
        do{
            Card card = new Card();
            card.getFeature().add(NUMBER[(int) (Math.random() * 3)]);
            card.getFeature().add(COLOR[(int) (Math.random() * 3)]);
            card.getFeature().add(FORM[(int) (Math.random() * 3)]);
            card.getFeature().add(FILLING[(int) (Math.random() * 3)]);
            if( cards.contains(card) ){
                continue;
            }
            cards.add(card);
        }while (numberOfCards > cards.size());
        return cards;
    }

    public static boolean isSetState(Card oneCard, Card twoCard, Card threeCard){

        //4-ből 2 (1,2) (1,3) (1,4) (2,3) (2,4) (3,4)
        //4-ből 3 (1,2,3) (1,2,4) (1,3,4) (2,3,4)

    return false;
    }

}
