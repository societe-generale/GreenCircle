package com.codingame.game.action;

import com.codingame.game.card.CardType;

public class PlayAction extends Action {
    protected CardType actionCardType;
    protected CardType secondaryCardType;
    protected CardType thirdCardType;

    public PlayAction(CardType cardType) {
        this.actionCardType = cardType;
    }

    public PlayAction(CardType cardType, CardType secondaryCardType) {
        this.actionCardType = cardType;
        this.secondaryCardType = secondaryCardType;
    }

    public PlayAction(CardType cardType, CardType secondaryCardType, CardType thirdCardType) {
        this.actionCardType = cardType;
        this.secondaryCardType = secondaryCardType;
        this.thirdCardType = thirdCardType;
    }

    @Override
    public boolean isPlay() {
        return true;
    }

    public CardType getActionCardType() {
        return actionCardType;
    }
    public CardType getSecondaryCardType() { return secondaryCardType; }
    public CardType getThirdCardType() { return thirdCardType; }
}
