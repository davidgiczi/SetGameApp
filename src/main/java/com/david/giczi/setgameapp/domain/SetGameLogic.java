package com.david.giczi.setgameapp.domain;
import com.david.giczi.setgameapp.utils.Feature;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SetGameLogic {

    private final HashSet<Card> cardsOfSET = new HashSet<>();
    private final List<Card> actualCardsList = new ArrayList<>();
    private static final Feature[] NUMBER = {Feature.one, Feature.two, Feature.three};
    private static final Feature[] COLOR = {Feature.red, Feature.green, Feature.purple};
    private static final Feature[] FORM = {Feature.capsule, Feature.cell, Feature.square};
    private static final Feature[] FILLING = {Feature.empty, Feature.filled, Feature.striped};
    public static final int MAX_CARDS = 81;


    public List<Card> getActualCardsList() {
        return actualCardsList;
    }

    public HashSet<Card> getCardsOfSET() {
        return cardsOfSET;
    }


    public void collectSETByActualCards(){
        cardsOfSET.clear();
        for(int i = 0; i < actualCardsList.size() - 2; i++){
            for(int j = i + 1; j < actualCardsList.size() - 1; j++ )
                for(int k = i + 2; k < actualCardsList.size(); k++){

                if( isSETState(actualCardsList.get(i), actualCardsList.get(j), actualCardsList.get(k) ) ){
                        cardsOfSET.add(actualCardsList.get(i));
                        cardsOfSET.add(actualCardsList.get(j));
                        cardsOfSET.add(actualCardsList.get(k));
                    }
                }
            }
        }

    public List<Card> getCards(int numberOfCards){
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

    public boolean isSetCards(List<String> chosenCardList){
        Card oneCard = new Card();
        Card twoCard = new Card();
        Card threeCard = new Card();
        for (String cardName : chosenCardList) {
            String[] nameData = cardName.substring(0, cardName.indexOf(".")).split("_");
            switch (chosenCardList.indexOf(cardName)){
                case 0:
                    oneCard.getFeature().add(parseFeature(nameData[0]));
                    oneCard.getFeature().add(parseFeature(nameData[1]));
                    oneCard.getFeature().add(parseFeature(nameData[2]));
                    oneCard.getFeature().add(parseFeature(nameData[3]));
                    break;
                case 1:
                    twoCard.getFeature().add(parseFeature(nameData[0]));
                    twoCard.getFeature().add(parseFeature(nameData[1]));
                    twoCard.getFeature().add(parseFeature(nameData[2]));
                    twoCard.getFeature().add(parseFeature(nameData[3]));
                    break;
                case 2:
                    threeCard.getFeature().add(parseFeature(nameData[0]));
                    threeCard.getFeature().add(parseFeature(nameData[1]));
                    threeCard.getFeature().add(parseFeature(nameData[2]));
                    threeCard.getFeature().add(parseFeature(nameData[3]));
                    break;
            }
        }
        return  isSETState(oneCard, twoCard, threeCard);
    }

    public Feature parseFeature(String feature){
        if( Feature.one.toString().equals(feature) ){
            return Feature.one;
        }
        else if( Feature.two.toString().equals(feature) ){
            return Feature.two;
        }
        else if( Feature.three.toString().equals(feature) ){
            return Feature.three;
        }
        else if( Feature.red.toString().equals(feature) ){
            return Feature.red;
        }
        else if( Feature.green.toString().equals(feature) ){
            return Feature.green;
        }
        else if( Feature.purple.toString().equals(feature) ){
            return Feature.purple;
        }
        else if( Feature.capsule.toString().equals(feature) ){
            return Feature.capsule;
        }
        else if( Feature.cell.toString().equals(feature) ){
            return Feature.cell;
        }
        else if( Feature.square.toString().equals(feature) ){
            return Feature.square;
        }
        else if( Feature.empty.toString().equals(feature) ){
            return Feature.empty;
        }
        else if( Feature.filled.toString().equals(feature) ){
            return Feature.filled;
        }
        return Feature.striped;
    }

    private boolean isSETState(Card oneCard, Card twoCard, Card threeCard){
    return isSetStateWithOneFeature(oneCard, twoCard, threeCard) ||
            isSetStateWithTwoFeature(oneCard, twoCard, threeCard) ||
            isSetStateWithThreeFeature(oneCard, twoCard, threeCard) ||
            isSetStateWithoutFeature(oneCard, twoCard, threeCard);
    }
private boolean isSetStateWithOneFeature(Card oneCard, Card twoCard, Card threeCard){

   if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
        twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
        oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
        twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
        threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
        oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
        twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
        threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
        oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
        twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
        threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
       return true;
   }
    if( oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
            twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
            threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
            oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
            twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
            oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
            twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
            threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
            oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
            twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
            threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
        return true;
    }
    if( oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
            twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
            threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
            oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
            twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
            threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
            oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
            twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
            oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
            twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
            threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
        return true;
    }
    return oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
            twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
            threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
            oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
            twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
            threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
            oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
            twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
            threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
            oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
            twoCard.getFeature().get(3) == threeCard.getFeature().get(3);
}

    private boolean isSetStateWithTwoFeature(Card oneCard, Card twoCard, Card threeCard){
        //4-ből 2 (1,2) (1,3) (1,4) (2,3) (2,4) (3,4)
        if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
                threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
                threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
            return true;
        }
        if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
                threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
                threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
            return true;
        }
        if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
                threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
                threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) == threeCard.getFeature().get(3) ){
            return true;
        }
        if( oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
                threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
                threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
            return true;
        }
        if( oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
                threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
                threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) == threeCard.getFeature().get(3) ){
            return true;
        }
        return oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
                threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
                threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) == threeCard.getFeature().get(3);
    }

    private boolean isSetStateWithThreeFeature(Card oneCard, Card twoCard, Card threeCard){
        //4-ből 3 (1,2,3) (1,2,4) (1,3,4) (2,3,4)
        if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
                threeCard.getFeature().get(3) != oneCard.getFeature().get(3) ){
            return true;
        }
        if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
                threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) == threeCard.getFeature().get(3) ){
            return true;
        }
        if( oneCard.getFeature().get(0) == twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) == threeCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
                threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) == threeCard.getFeature().get(3) ){
            return true;
        }
        return oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
                threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) == twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) == threeCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) == twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) == threeCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) == twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) == threeCard.getFeature().get(3);
    }

    private boolean isSetStateWithoutFeature(Card oneCard, Card twoCard, Card threeCard){

        return oneCard.getFeature().get(0) != twoCard.getFeature().get(0) &&
                twoCard.getFeature().get(0) != threeCard.getFeature().get(0) &&
                threeCard.getFeature().get(0) != oneCard.getFeature().get(0) &&
                oneCard.getFeature().get(1) != twoCard.getFeature().get(1) &&
                twoCard.getFeature().get(1) != threeCard.getFeature().get(1) &&
                threeCard.getFeature().get(1) != oneCard.getFeature().get(1) &&
                oneCard.getFeature().get(2) != twoCard.getFeature().get(2) &&
                twoCard.getFeature().get(2) != threeCard.getFeature().get(2) &&
                threeCard.getFeature().get(2) != oneCard.getFeature().get(2) &&
                oneCard.getFeature().get(3) != twoCard.getFeature().get(3) &&
                twoCard.getFeature().get(3) != threeCard.getFeature().get(3) &&
                threeCard.getFeature().get(3) != oneCard.getFeature().get(3);
    }



}
