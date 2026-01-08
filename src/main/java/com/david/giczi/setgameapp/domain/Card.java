package com.david.giczi.setgameapp.domain;

import com.david.giczi.setgameapp.utils.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Card {

    private final List<Feature> feature;

    public Card() {
        this.feature = new ArrayList<>();
    }

    public List<Feature> getFeature() {
        return feature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return feature.get(0) == card.feature.get(0) &&
                feature.get(1) == card.feature.get(1) &&
                feature.get(2) == card.feature.get(2) &&
                feature.get(3) == card.feature.get(3);
    }
    @Override
    public int hashCode() {
        return Objects.hash(feature);
    }

    @Override
    public String toString() {
        return feature.get(0) + "_" + feature.get(1) + "_" + feature.get(2) + "_" + feature.get(3) + ".png";
    }
}
