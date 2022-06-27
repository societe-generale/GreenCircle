package com.codingame.game;

import com.codingame.game.card.Card;
import java.util.Stack;

public class Zone {
    private Stack<Card> cards;
    private int id;

    public Zone(int zoneId) {
        cards = new Stack<>();
        id = zoneId;
    }

    public Card getNextCard() {
        if (cards.size()>0) {
            return cards.pop();
        }
        return Game.getNextBonusCard();
    }

    public int getCardsCount() {
        return cards.size();
    }

    public void returnCard(Card card) {
        cards.push(card);
    }

    public int getId() {
        return id;
    }

    public Stack<Card> getCards() { return cards; }
}
