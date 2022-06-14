package com.codingame.game.card;

import static com.codingame.game.card.CardType.BONUS;
import static com.codingame.game.card.CardType.TECHNICAL_DEBT;

public abstract class Card {
    protected int bonusActionCount;
    protected int goodActionCount;
    protected CardType cardType;
    protected int badActionCount;
    protected  int id;

    public int getId() { return id; }

    public int getBonusActionCount() {
        return bonusActionCount;
    }

    public int getGoodActionCount() {
        return goodActionCount;
    }

    public int getBadActionCount() {
        return badActionCount;
    }

    public CardType getCardType() {
        return cardType;
    }

    public boolean isTechnicalDebt() {
        return cardType == TECHNICAL_DEBT;
    }
    public boolean isGoodActionBonus() { return cardType == BONUS; }
    public boolean isActionCard() { return !isTechnicalDebt() && !isGoodActionBonus(); }


    public String getDescription() {
        return String.format("%s (%d)", cardType.toString(), cardType.ordinal());
    }

    public String getTooltipText() {
        return String.format("%s (%d)", cardType.toString(), cardType.ordinal());
    }
}
