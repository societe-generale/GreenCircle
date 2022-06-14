package com.codingame.game.action;

import com.codingame.game.card.CardType;

public class ThrowAction extends Action {
    protected CardType cardType;

    public ThrowAction(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean isThrow() {
        return true;
    }

    public CardType getCardType() {
        return cardType;
    }
}
