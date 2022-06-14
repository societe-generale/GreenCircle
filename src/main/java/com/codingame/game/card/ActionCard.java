package com.codingame.game.card;

public class ActionCard extends Card {
    public ActionCard(int id, CardType cardType) {
        this.goodActionCount = 2;
        this.cardType = cardType;
        this.badActionCount = 2;
        bonusActionCount = 0;
        this.id = id;
    }
}
