package com.codingame.game;

import com.codingame.game.card.Card;
import com.codingame.game.card.CardType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.geom.Point2D;
import java.util.List;

public class Application {
    private int[] resourcesNeeded;
    private int id;
    static String[] resourcesDescription = new String[Config.ZONES_COUNT];
    static {
        resourcesDescription[0]= "Training";
        resourcesDescription[1]= "Coding";
        resourcesDescription[2]= "Daily";
        resourcesDescription[3]= "Task";
        resourcesDescription[4]= "Archi";
        resourcesDescription[5]= "CI";
        resourcesDescription[6]= "Review";
        resourcesDescription[7]= "Refactoring";
    }

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

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%d:", id));
        for (int i=0;i<Config.ZONES_COUNT;++i) {
            int count = resourcesNeeded[i];
            if (count>0) {
                sb.append(String.format(" %d %s,", count, resourcesDescription[i]));
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


}
