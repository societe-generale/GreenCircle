package com.codingame.game;

import java.util.Properties;
import java.util.function.Function;

public class Config {
    public static int ZONES_COUNT = 8;
    public static int CARDS_TO_DRAW = 4;
    public static int SMALL_APPLICATIONS_COUNT = 0;
    public static int BIG_APPLICATIONS_COUNT = 12;
    public static int APPLICATIONS_TO_WIN = 5;
    public static int CARDS_PER_ZONE = 5;
    public static boolean GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT = true;
    public static boolean CAN_PLAY_SIMPLE_CARDS = true;
    public static boolean LOSE_CARDS_AT_END_OF_CYCLE = true;
    public static boolean CAN_PLAY_COMPLEX_CARDS = true;
    public static int applicationsCount() { return SMALL_APPLICATIONS_COUNT + BIG_APPLICATIONS_COUNT; };

    public static void setDefaultValueByLevel(LeagueRules rules) {
        SMALL_APPLICATIONS_COUNT = rules.smallApplicationsCount;
        BIG_APPLICATIONS_COUNT = rules.bigApplicationsCount;
        GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT = rules.giveCardIfTooCloseToOpponent;
        CAN_PLAY_SIMPLE_CARDS = rules.canPlaySimpleCards;
        LOSE_CARDS_AT_END_OF_CYCLE = rules.loseCardsAtEndOfCycle;
        CAN_PLAY_COMPLEX_CARDS = rules.canPlayComplexCards;
    }

    public static void apply(Properties params) {
        SMALL_APPLICATIONS_COUNT = getFromParams(params, "SMALL_APPLICATIONS_COUNT", SMALL_APPLICATIONS_COUNT);
        BIG_APPLICATIONS_COUNT = getFromParams(params, "BIG_APPLICATIONS_COUNT", BIG_APPLICATIONS_COUNT);
        GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT = getFromParams(params, "GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT", GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT);
        CAN_PLAY_SIMPLE_CARDS = getFromParams(params, "CAN_PLAY_SIMPLE_CARDS", CAN_PLAY_SIMPLE_CARDS);
        LOSE_CARDS_AT_END_OF_CYCLE = getFromParams(params, "LOSE_CARDS_AT_END_OF_CYCLE", LOSE_CARDS_AT_END_OF_CYCLE);
        CAN_PLAY_COMPLEX_CARDS = getFromParams(params, "CAN_PLAY_COMPLEX_CARDS", CAN_PLAY_COMPLEX_CARDS);
    }

    private static int getFromParams(Properties params, String name, int defaultValue) {
        return getFromParams(params, name, defaultValue, Integer::parseInt);
    }

    private static boolean getFromParams(Properties params, String name, boolean defaultValue) {
        return getFromParams(params, name, defaultValue, Boolean::parseBoolean);
    }

    private static <T> T getFromParams(Properties params, String name, T defaultValue, Function<String, T> create) {
        String inputValue = params.getProperty(name);
        if (inputValue != null) {
            try {
                return create.apply(inputValue);
            } catch (NumberFormatException e) {
                // Do naught
            }
        }
        return defaultValue;
    }
}
