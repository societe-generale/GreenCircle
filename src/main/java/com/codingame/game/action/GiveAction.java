package com.codingame.game.action;

import com.codingame.game.card.CardType;

public class GiveAction extends Action {
    protected CardType cardType;

    public GiveAction(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean isGive() {
        return true;
    }

    public CardType getCardType() {
        return cardType;
    }
}
