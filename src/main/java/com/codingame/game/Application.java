package com.codingame.game;

import com.codingame.game.card.Card;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Application {
    private int[] resourcesNeeded;
    private int id;

    public Application(int id, int[] resources) {
        this.id = id;
        resourcesNeeded = resources;
    }

    public int getId() {
        return id;
    }

    //returns the number of technical debt cards to draw (-1 = cannot release)
    public int canBeReleased(Player player) {
        List<Card> playerHand = player.getCardsInHand();
        int technicalDebtUsed = 0;
        int missingResources = 0;
        int availableBadActions = 0;
        int availableBonuses = 0;
        int[] availableResources = new int[Config.ZONES_COUNT];
        for (Card card : playerHand) {
            availableBadActions += card.getBadActionCount();
            availableBonuses += card.getBonusActionCount();
            if (card.isActionCard()) {
                availableResources[card.getCardType().ordinal()] += card.getGoodActionCount();
            }
        }

        //use also automated cards
        for (Card card : player.getAutomatedCards()) {
            availableBadActions += card.getBadActionCount();
            availableBonuses += card.getBonusActionCount();
            if (card.isActionCard()) {
                availableResources[card.getCardType().ordinal()] += card.getGoodActionCount();
            }
        }

        //check resources
        for (int i=0;i<availableResources.length;++i) {
            if (resourcesNeeded[i]>availableResources[i]) {
                missingResources += resourcesNeeded[i] - availableResources[i];
            }
        }
        missingResources -= availableBonuses;

        if (missingResources>0) {
            if (player.getScore()<Config.APPLICATIONS_TO_WIN-1 && missingResources<=availableBadActions) {
                    technicalDebtUsed = missingResources;
            }
            else {
                //need to pay with only good actions
                technicalDebtUsed = -1;
            }
        }
        return technicalDebtUsed;
    }

    @Override
    public String toString() {
        return "APPLICATION " + id + " " + StringUtils.join(ArrayUtils.toObject(resourcesNeeded), " ");
    }
}
