package com.codingame.game;

public class LeagueRules {
    public int smallApplicationsCount = 12;
    public int bigApplicationsCount = 12;
    public boolean giveCardIfTooCloseToOpponent = true;
    public boolean canPlaySimpleCards = true;
    public boolean loseCardsAtEndOfCycle = true;
    public boolean canPlayComplexCards = true;

    public static LeagueRules fromIndex(int index) {
        LeagueRules rules = new LeagueRules();

        if (index == 1) {
            rules.bigApplicationsCount = 0;
            rules.smallApplicationsCount = 12;
            rules.giveCardIfTooCloseToOpponent = false;
            rules.canPlaySimpleCards = false;
            rules.loseCardsAtEndOfCycle = false;
            rules.canPlayComplexCards = false;
        }
        if (index == 2) {
            rules.bigApplicationsCount = 6;
            rules.smallApplicationsCount = 6;
            rules.giveCardIfTooCloseToOpponent = true;
            rules.canPlaySimpleCards = true;
            rules.loseCardsAtEndOfCycle = false;
            rules.canPlayComplexCards = false;
        }
        if (index >= 3) {
            rules.smallApplicationsCount = 0;
            rules.bigApplicationsCount = 12;
            rules.giveCardIfTooCloseToOpponent = true;
            rules.canPlaySimpleCards = true;
            rules.loseCardsAtEndOfCycle = true;
            rules.canPlayComplexCards = true;
        }

        return rules;
    }
}
