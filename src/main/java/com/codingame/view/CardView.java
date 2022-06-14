package com.codingame.view;

import com.codingame.game.card.Card;
import com.codingame.game.card.CardType;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.entities.TextBasedEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardView {


    private static class CardViewState {
        CardType cardType;
        boolean visible;

        public CardViewState(CardType cardType, boolean visible ) {
            this.visible = visible;
            this.cardType = cardType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            CardViewState other = (CardViewState) obj;
            if (visible != other.visible) return false;
            if (cardType != other.cardType) return false;
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (visible ? 1231 : 1237);
            result = prime * result + cardType.ordinal();
            return result;
        }
    }

    Group group;

    SpriteAnimation sprite, death;
    TextBasedEntity<?> message;

    SpriteAnimation switchFX, deathFX;

    Group rotationWrapper;
    private Card cardModel;

    public CardView(Card cardModel) {
        this.cardModel = cardModel;
    }

    public Card getCardModel() {
        return cardModel;
    }

    static Map<CardViewState, String[]> stateToImages = new HashMap<>();
    static {
        for (int cardTypeId = 0; cardTypeId < 10; ++cardTypeId) {
            stateToImages.put(new CardViewState(CardType.values()[cardTypeId], true), getCardImages(cardTypeId, true));
            stateToImages.put(new CardViewState(CardType.values()[cardTypeId], false), getCardImages(cardTypeId, false));
       }
    }

    private static String[] getCardImages(int cardType, boolean visible) {
        List<String> cardImages = new ArrayList<>();
        if (visible) {
            cardImages.add("card_" + cardType + ".png");
        } else {
            cardImages.add("card_back.png");
        }
        return cardImages.toArray(new String[0]);
    }

    public void setViewState(CardType cardType, boolean visible) {
        setViewState(cardType, visible, false);
    }

    public void setViewState(CardType cardType, boolean visible, boolean isInHand) {
        double handFactor=1;
        if (isInHand) {
            handFactor *= 1.5;
        }
        sprite.setImages(stateToImages.get(new CardViewState(cardType, visible)));
        if (visible && cardType.equals(CardType.TASK_PRIORITIZATION)) {
            //this card is twice the size of the others
            sprite.setScale(0.5 * handFactor);
        }
        else {
            sprite.setScale(1 * handFactor);
        }
    }

    public void show() { sprite.setVisible(true); }

    public void hide() { sprite.setVisible(false); }
}
