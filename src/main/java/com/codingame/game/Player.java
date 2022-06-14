package com.codingame.game;
import com.codingame.game.card.Card;
import com.codingame.game.card.CardType;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.game.action.Action;
import com.codingame.view.View;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

// Uncomment the line below and comment the line under it to create a Solo Game
// public class Player extends AbstractSoloPlayer {
public class Player extends AbstractMultiplayerPlayer {
    private String message;
    private Action action;
    private int zoneId;
    private int playsLeft;
    private List<Card> cardsInHand;
    private List<Card> automatedCards;
    private List<Card> permanentSkillCards;
    private List<Card> cardsDiscardPile;
    private Stack<Card> cardsDrawPile;
    private int numberOfCardsToThrow;
    private boolean mustGiveCard;
    private int zoneToTakeCardId = -1;
    private String scoreDescription;

    public String getTooltipText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Player %d with score %d\n-------------\nPermanent Skills:\n%d Daily Routine\n%d Architecture Study\n-------------\nAutomated:",
                getIndex(), getScore(), getPermanentDailyRoutineCardsCount(), getPermanentArchitectureStudyCardsCount()));
        for (int i=0;i<9;++i) {
            int finalI = i;
            int count = (int) automatedCards.stream().filter(card -> card.getCardType().ordinal() == finalI).count();
            if (count>0) {
                sb.append(String.format("\n%d %s", count, CardType.values()[i]));
            }
        }

        return sb.toString();
    }

    public String getDrawPileTooltipText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Draw Pile (%d cards):",
                cardsDrawPile.size()));
        for (int i=0;i<=9;++i) {
            int finalI = i;
            int count = (int) cardsDrawPile.stream().filter(card -> card.getCardType().ordinal() == finalI).count();
            if (count>0) {
                sb.append(String.format("\n%d %s", count, CardType.values()[i]));
            }
        }

        return sb.toString();
    }

    public String getDiscardPileTooltipText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Discard Pile (%d cards):",
                cardsDiscardPile.size()));
        for (int i=0;i<=9;++i) {
            int finalI = i;
            int count = (int) cardsDiscardPile.stream().filter(card -> card.getCardType().ordinal() == finalI).count();
            if (count>0) {
                sb.append(String.format("\n%d %s", count, CardType.values()[i]));
            }
        }

        return sb.toString();
    }

    public void setScoreDescription(String scoreDescription) {
        this.scoreDescription = scoreDescription;
    }

    public String getScoreDescription() { return scoreDescription; }

    public String getMessage() {
        return message;
    }

    public int getPlaysLeft() { return playsLeft; };

    public void setPlaysLeft(int playsLeft) { this.playsLeft = playsLeft; };

    public void addMorePlays(int plays) { playsLeft += plays; };

    public void removeOnePlay() { playsLeft--; };

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public boolean mustGiveCard() {
        return mustGiveCard;
    }

    public void setMustGiveCard(boolean mustGiveCard) {
        this.mustGiveCard = mustGiveCard;
    }

    public void setMustTakeCard(int zoneToTakeCardId) {
        this.zoneToTakeCardId = zoneToTakeCardId;
    }

    public int getZoneToTakeCardId() { return zoneToTakeCardId; }

    public int getDrawPileSize() { return cardsDrawPile.size(); }

    public int getDiscardPileSize() { return cardsDiscardPile.size(); }

    public int getNumberOfCardsToThrow() { return numberOfCardsToThrow; }

    public void setNumberOfCardsToThrow(int numberOfCardsToThrow) {
        this.numberOfCardsToThrow = numberOfCardsToThrow;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public void init(Random random) {
        zoneId = -1; //start outside of the board
        cardsInHand = new ArrayList<>();
        cardsDiscardPile = new ArrayList<>();
        automatedCards = new ArrayList<>();
        permanentSkillCards = new ArrayList<>();
        cardsDrawPile = new Stack<>();
        mustGiveCard = false;
        numberOfCardsToThrow = 0;

        //initial draw pile is 4 good and 4 bad cards
        int bonusCardsToCreate = 4;
        int technicalDebtCardsToCreate = 4;
        while (bonusCardsToCreate + technicalDebtCardsToCreate > 0) {
            Card newCard;
            if (bonusCardsToCreate>0 && technicalDebtCardsToCreate>0) {
                if (random.nextBoolean()) {
                    newCard = Game.getNextBonusCard();
                    bonusCardsToCreate--;
                }
                else {
                    newCard = Game.getNextTechnicalDebtCard();
                    technicalDebtCardsToCreate--;
                }
            }
            else if (bonusCardsToCreate>0) {
                if (bonusCardsToCreate==4) {
                    //error: we do not want our first hand with only bonus cards
                    newCard = cardsDrawPile.pop();
                    cardsDrawPile.push(Game.getNextBonusCard());
                }
                else {
                    newCard = Game.getNextBonusCard();
                }
                bonusCardsToCreate--;
            }
            else {
                if (technicalDebtCardsToCreate==4) {
                    //error: we do not want our first hand with only technical debt cards
                    newCard = cardsDrawPile.pop();
                    cardsDrawPile.push(Game.getNextTechnicalDebtCard());
                }
                else {
                    newCard = Game.getNextTechnicalDebtCard();
                }
                technicalDebtCardsToCreate--;
            }
            cardsDrawPile.push(newCard);
        }

        drawCards(Config.CARDS_TO_DRAW, random, null);
    }

    @Override
    public int getExpectedOutputLines() {
        // Returns the number of expected lines of outputs for a player
        return 1;
    }

    public String getGlobalCardsInfo() {
        int[] cardsCount = new int[Config.ZONES_COUNT + 2];
        for(Card card : cardsInHand) {
            cardsCount[card.getCardType().ordinal()]++;
        }
        for(Card card : cardsDiscardPile) {
            cardsCount[card.getCardType().ordinal()]++;
        }
        for(Card card : cardsDrawPile) {
            cardsCount[card.getCardType().ordinal()]++;
        }
        return "CARDS " + StringUtils.join(ArrayUtils.toObject(cardsCount), " ");
    }

    private String getCardsCount(List<Card> cards) {
        int[] count = new int[Config.ZONES_COUNT+2];
        //hand
        for(Card card : cards) {
            count[card.getCardType().ordinal()]++;
        }
        return StringUtils.join(ArrayUtils.toObject(count), " ");
    }

    public List<String> getCardsInfo() {
        List<String> info = new ArrayList<>();

        //hand
        if (!cardsInHand.isEmpty()) {
            info.add("HAND " + getCardsCount(cardsInHand));
        }

        //draw
        if (!cardsDrawPile.empty()) {
            info.add("DRAW " + getCardsCount(cardsDrawPile));
        }

        //discard
        if (!cardsDiscardPile.isEmpty()) {
            info.add("DISCARD " + getCardsCount(cardsDiscardPile));
        }

        //automated
        if (!automatedCards.isEmpty()) {
            info.add("AUTOMATED " + getCardsCount(automatedCards));
        }

        return info;
    }

    public String getAutomatedCardsInfo() {
        return "AUTOMATED " + getCardsCount(automatedCards);
    }

    public void addNewCardInHand(Card newCard) {
        cardsInHand.add(newCard);
    }

    public void addNewCardInDiscardPile(Card newCard) {
        cardsDiscardPile.add(newCard);
    }

    public void discardAndRedrawCards(Random random, View view) {
        for(Card card : cardsInHand) {
            cardsDiscardPile.add(card);
        }
        cardsInHand.clear();
        int moreCardsToDraw = getPermanentArchitectureStudyCardsCount();
        drawCards(Config.CARDS_TO_DRAW + moreCardsToDraw, random, view);
    }

    public int getPermanentDailyRoutineCardsCount() {
        return (int)permanentSkillCards.stream().filter(card -> card.getCardType() == CardType.DAILY_ROUTINE).count();
    }

    public List<Card> getPermanentSkillCards() {
        return permanentSkillCards;
    }

    public int getPermanentArchitectureStudyCardsCount() {
        return (int)permanentSkillCards.stream().filter(card -> card.getCardType() == CardType.ARCHITECTURE_STUDY).count();
    }

    public void drawCards(int cardsCount, Random random, View view) {
        for (int i=0;i<cardsCount;++i) {
            if (cardsDrawPile.empty() && cardsDiscardPile.isEmpty()) {
                return;
            }
            if (cardsDrawPile.empty()) {
                //reshuffle
                while(!cardsDiscardPile.isEmpty()) {
                    int cardId = random.nextInt(cardsDiscardPile.size());
                    cardsDrawPile.push(cardsDiscardPile.get(cardId));
                    cardsDiscardPile.remove(cardId);
                }
            }
            Card cardTaken = cardsDrawPile.pop();
            cardsInHand.add(cardTaken);
            if (view!=null) {
                view.playerTakesCardInHand(cardTaken);
            }
        }
    }

    public List<Card> getNonTechnicalDebtCardsInHand() {
        List<Card> nonTechnicalDebtCards = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (!card.isTechnicalDebt()) {
                nonTechnicalDebtCards.add(card);
            }
        }
        return nonTechnicalDebtCards;
    }

    public List<Card> getActionCardsInHand() {
        List<Card> actionCards = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card.isActionCard()) {
                actionCards.add(card);
            }
        }
        return actionCards;
    }

    public List<Card> getAutomatedCards() {
        return automatedCards;
    }

    public void addAutomatedCard(Card card) {
        automatedCards.add(card);
    }

    public void addPermanentSkillCard(Card card) {
        permanentSkillCards.add(card);
    }

    public Card removeCardInHand(CardType cardType) {
        for(Card card : cardsInHand) {
            if (card.getCardType() == cardType) {
                cardsInHand.remove(card);
                return card;
            }
        }
        return null;
    }

    public void hasGivenCard() {
        mustGiveCard = false;
    }

    public void removeCardInHand(Card card) {
        cardsInHand.remove(card);
    }

    public Stack<Card> getDrawPile() {
        return cardsDrawPile;
    }

    public List<Card> getDiscardPile() { return cardsDiscardPile; }

    public void discardCardFromHand(CardType cardType) {
        Card cardToDiscard = removeCardInHand(cardType);
        cardsDiscardPile.add(cardToDiscard);
    }

    public void playPermanentSkillCardFromHand(CardType cardType) {
        Card permanentSkillCard = removeCardInHand(cardType);
        addPermanentSkillCard(permanentSkillCard);
    }

    public void discardPermanentSkills() {
        for (Card card : permanentSkillCards) {
            cardsDiscardPile.add(card);
        }
        permanentSkillCards.clear();
    }

    public boolean canPlayCard() {
        if (canPlaySimpleCard()) {
            return true;
        }
        int numberOfNonTechnicalDebtCardsInHand = getNonTechnicalDebtCardsInHand().size();
        for (Card card : cardsInHand) {
            if (card.isActionCard()
                    && (card.getCardType() == CardType.CODING
                    || card.getCardType() == CardType.DAILY_ROUTINE)) {
                return true;
            }
            if (card.isActionCard() && numberOfNonTechnicalDebtCardsInHand > 1
                    && (card.getCardType() == CardType.TASK_PRIORITIZATION
                    || card.getCardType() == CardType.CONTINUOUS_INTEGRATION)) {
                return true;
            }
        }
        return false;
    }

    public boolean canPlaySimpleCard() {
        return getSimpleActionCardsInHand().size() > 0;
    }

    public List<Card> getSimpleActionCardsInHand() {
        List<Card> actionCards = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card.isActionCard()
                    && (card.getCardType() == CardType.TRAINING
                    || card.getCardType() == CardType.ARCHITECTURE_STUDY
                    || card.getCardType() == CardType.CODE_REVIEW
                    || card.getCardType() == CardType.REFACTORING)) {
                actionCards.add(card);
            }
        }
        return actionCards;
    }

    public int getTechnicalDebtCardsCount() {
        int technicalDebtCardsCount = 0;
        for(Card card : cardsInHand) {
            if (card.isTechnicalDebt()) {
                technicalDebtCardsCount++;
            }
        }
        for(Card card : cardsDiscardPile) {
            if (card.isTechnicalDebt()) {
                technicalDebtCardsCount++;
            }
        }
        for(Card card : cardsDrawPile) {
            if (card.isTechnicalDebt()) {
                technicalDebtCardsCount++;
            }
        }
        return technicalDebtCardsCount;
    }
}
