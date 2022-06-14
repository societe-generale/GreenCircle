package com.codingame.game.card;

//Bad Karma Card
public class TechnicalDebtCard extends Card {
    public TechnicalDebtCard(int id) {
        bonusActionCount = 0;
        goodActionCount = 0;
        badActionCount = 0;
        cardType = CardType.TECHNICAL_DEBT;
        this.id = id;
    }
}
