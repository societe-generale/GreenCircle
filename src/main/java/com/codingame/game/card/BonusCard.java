package com.codingame.game.card;

//Good Karma Card
public class BonusCard extends Card {
    public BonusCard(int id) {
        bonusActionCount = 1;
        goodActionCount = 0;
        badActionCount = 1;
        cardType = CardType.BONUS;
        this.id = id;
    }
}
