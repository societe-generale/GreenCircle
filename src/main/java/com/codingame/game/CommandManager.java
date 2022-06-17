package com.codingame.game;

import com.codingame.game.action.*;
import com.codingame.game.card.CardType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

@Singleton
public class CommandManager {
    @Inject private GameSummaryManager gameSummaryManager;
    @Inject private RandomActionFactory randomActionFactory;

    static final int MaxTurnsDrawingCardsInARow = 5;

    static final Pattern PLAYER_WAIT_PATTERN = Pattern.compile(
            "^WAIT(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_MOVE_PATTERN = Pattern.compile(
            "^MOVE (?<zoneId>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_ADVANCED_MOVE_PATTERN = Pattern.compile(
            "^MOVE (?<zoneToMoveId>\\d+) (?<zoneToTakeCardId>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_GIVE_PATTERN = Pattern.compile(
            "^GIVE (?<cardType>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_THROW_PATTERN = Pattern.compile(
            "^THROW (?<cardType>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_TRAINING_PATTERN = Pattern.compile(
            "^TRAINING(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_CODING_PATTERN = Pattern.compile(
            "^CODING(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_DAILY_ROUTINE_PATTERN = Pattern.compile(
            "^DAILY_ROUTINE(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_TASK_PRIORITIZATION_PATTERN = Pattern.compile(
            "^TASK_PRIORITIZATION (?<cardTypeToThrow>\\d+) (?<cardTypeToTake>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_ARCHITECTURE_STUDY_PATTERN = Pattern.compile(
            "^ARCHITECTURE_STUDY(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_CONTINUOUS_INTEGRATION_PATTERN = Pattern.compile(
            "^CONTINUOUS_INTEGRATION (?<cardType>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_CODE_REVIEW_PATTERN = Pattern.compile(
            "^CODE_REVIEW(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_REFACTORING_PATTERN = Pattern.compile(
            "^REFACTORING(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_RELEASE_PATTERN = Pattern.compile(
            "^RELEASE (?<applicationId>\\d+)(?:\\s+(?<message>.*))?"
    );

    static final Pattern PLAYER_RANDOM_PATTERN = Pattern.compile(
            "^RANDOM(?:\\s+(?<message>.*))?"
    );

    public void parseCommands(Player player, List<String> lines, Game game, GamePhase gamePhase) {
        for (String command : lines) {
            try {
                parseCommand(player, command, game, gamePhase);
            } catch (InvalidInputException e) {
                gameSummaryManager.addPlayerBadCommand(player, e);
                gameSummaryManager.addPlayerDisqualified(player);
                deactivatePlayer(player, e.getMessage());
            } catch (GameRuleException e) {
                gameSummaryManager.addPlayerRuleViolation(player, e);
                gameSummaryManager.addPlayerDisqualified(player);
                deactivatePlayer(player, e.getMessage());
            }
        }
    }

    public void parseCommand(Player player, String command, Game game, GamePhase gamePhase) throws InvalidInputException, GameRuleException {
        Matcher match;

        match = PLAYER_WAIT_PATTERN.matcher(command);
        if (match.matches() && (gamePhase == GamePhase.RELEASE ||gamePhase == GamePhase.PLAY_CARD)) {
            player.setAction(new WaitAction());
            matchMessage(player, match);
            return;
        }

        match = PLAYER_RANDOM_PATTERN.matcher(command);
        if (match.matches()) {
            switch (gamePhase) {
                case MOVE:
                    player.setAction(randomActionFactory.createMoveAction(game, player));
                    break;
                case GIVE_CARD:
                    player.setAction(randomActionFactory.createGiveAction(game, player));
                    break;
                case THROW_CARD:
                    player.setAction(randomActionFactory.createThrowAction(game, player));
                    break;
                case PLAY_CARD:
                    player.setAction(randomActionFactory.createPlayAction(game, player));
                    break;
                case RELEASE:
                    player.setAction(randomActionFactory.createReleaseAction(game, player));
                    break;
            }
            matchMessage(player, match);
            return;
        }

        match = PLAYER_ADVANCED_MOVE_PATTERN.matcher(command);
        int dailyRoutinesCount = player.getPermanentDailyRoutineCardsCount();
        if (match.matches() && gamePhase == GamePhase.MOVE && dailyRoutinesCount>0) {
            int zoneToMoveId = Integer.parseInt(match.group("zoneToMoveId"));
            int zoneToTakeCardId = Integer.parseInt(match.group("zoneToTakeCardId"));
            int distance = abs(zoneToMoveId - zoneToTakeCardId);
            if (distance > dailyRoutinesCount) {
                distance = abs(Config.ZONES_COUNT - distance);
            }
            if (distance <= dailyRoutinesCount) {
                player.setAction(new MoveAction(zoneToMoveId, zoneToTakeCardId));
                matchMessage(player, match);
                return;
            }
        }

        match = PLAYER_MOVE_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.MOVE) {
            int zoneId = Integer.parseInt(match.group("zoneId"));
            if (zoneId < 0 ||zoneId>=Config.ZONES_COUNT) {
                throw new GameRuleException(command, "you can only move to a zone between 0 and 7");
            }
            if (zoneId == player.getZoneId()) {
                throw new GameRuleException(command, String.format("you must move to another desk (you are already in desk %d", zoneId));
            }
            player.setAction(new MoveAction(zoneId, zoneId));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_THROW_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.THROW_CARD) {
            int cardTypeId = Integer.parseInt(match.group("cardType"));
            if (cardTypeId < 0 || cardTypeId > CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you can only throw a card type between 0 and 8");
            }
            if (cardTypeId == CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you cannot throw a technical debt card");
            }
            CardType cardType = CardType.values()[cardTypeId];
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(cardType)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have a card of type %s to throw away", cardType));
            }
            player.setAction(new ThrowAction(cardType));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_GIVE_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.GIVE_CARD) {
            int cardTypeId = Integer.parseInt(match.group("cardType"));
            if (cardTypeId < 0 || cardTypeId > CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you can only give a card type between 0 and 8");
            }
            if (cardTypeId == CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you cannot give a technical debt card");
            }
            CardType cardType = CardType.values()[cardTypeId];
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(cardType)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have a card of type %s to give", cardType));
            }
            player.setAction(new GiveAction(cardType));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_TRAINING_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.TRAINING)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.TRAINING));
            }
            if (player.getDiscardPile().size() == 0 && player.getDrawPile().size()==0) {
                gameSummaryManager.addImpossibleTraining(player); //nothing to draw so useless action
                player.setAction((new WaitAction()));
            }
            else if (player.getLastTurnsSpendDrawingCards() >= MaxTurnsDrawingCardsInARow) {
                gameSummaryManager.addForbiddenToDrawCards(player); //cannot draw more than 5 times in a row to prevent infinite loops. You should play another card in between
                player.setAction((new WaitAction()));
            }
            else {
                player.setAction(new PlayAction(CardType.TRAINING));
            }
            matchMessage(player, match);
            return;
        }

        match = PLAYER_CODING_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.CODING)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.CODING));
            }
            //no check on empty draw/discard since doing 2 actions can be useful
            else if (player.getLastTurnsSpendDrawingCards() >= MaxTurnsDrawingCardsInARow) {
                gameSummaryManager.addForbiddenToDrawCards(player); //cannot draw more than 5 times in a row to prevent infinite loops. You should play another card in between
                player.setAction((new WaitAction()));
            }
            else {
                player.setAction(new PlayAction(CardType.CODING));
            }
            matchMessage(player, match);
            return;
        }

        match = PLAYER_DAILY_ROUTINE_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.DAILY_ROUTINE)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.DAILY_ROUTINE));
            }
            player.setAction(new PlayAction(CardType.DAILY_ROUTINE));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_TASK_PRIORITIZATION_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.TASK_PRIORITIZATION)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.TASK_PRIORITIZATION));
            }
            int cardTypeToThrow = Integer.parseInt(match.group("cardTypeToThrow"));
            if (cardTypeToThrow < 0 || cardTypeToThrow > CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you can only deprioritize a card type between 0 and 8");
            }
            if (cardTypeToThrow == CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you cannot deprioritize a technical debt card");
            }
            CardType cardType = CardType.values()[cardTypeToThrow];
            int cardsCount = (int)player.getCardsInHand().stream().filter(c -> c.getCardType().equals(cardType)).count();
            if (cardsCount==0 || (cardsCount==1 && cardType.equals(CardType.TASK_PRIORITIZATION))) {
                throw new GameRuleException(command, String.format("you do not have a card of type %s to deprioritize", cardType));
            }
            int cardTypeToTake = Integer.parseInt(match.group("cardTypeToTake"));
            if (cardTypeToTake < 0 || cardTypeToTake > CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you can only prioritize a card type between 0 and 8");
            }
            player.setAction(new PlayAction(CardType.TASK_PRIORITIZATION, CardType.values()[cardTypeToThrow], CardType.values()[cardTypeToTake]));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_ARCHITECTURE_STUDY_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.ARCHITECTURE_STUDY)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.ARCHITECTURE_STUDY));
            }
            player.setAction(new PlayAction(CardType.ARCHITECTURE_STUDY));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_CONTINUOUS_INTEGRATION_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.CONTINUOUS_INTEGRATION)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.CONTINUOUS_INTEGRATION));
            }
            int cardTypeId = Integer.parseInt(match.group("cardType"));
            if (cardTypeId < 0 || cardTypeId > CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you can only automate a card type between 0 and 8");
            }
            if (cardTypeId == CardType.TECHNICAL_DEBT.ordinal()) {
                throw new GameRuleException(command, "you cannot automate a technical debt card");
            }
            CardType cardType = CardType.values()[cardTypeId];
            int cardsCount = (int)player.getCardsInHand().stream().filter(c -> c.getCardType().equals(cardType)).count();
            if (cardsCount==0 || (cardsCount==1 && cardType.equals(CardType.CONTINUOUS_INTEGRATION))) {
                throw new GameRuleException(command, String.format("you do not have a card of type %s to automate", cardType));
            }
            player.setAction(new PlayAction(CardType.CONTINUOUS_INTEGRATION, cardType));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_CODE_REVIEW_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.CODE_REVIEW)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.CODE_REVIEW));
            }
            player.setAction(new PlayAction(CardType.CODE_REVIEW));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_REFACTORING_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.PLAY_CARD) {
            if (player.getCardsInHand().stream().filter(c -> c.getCardType().equals(CardType.REFACTORING)).count()==0) {
                throw new GameRuleException(command, String.format("you do not have any %s card in hand", CardType.REFACTORING));
            }
            player.setAction(new PlayAction(CardType.REFACTORING));
            matchMessage(player, match);
            return;
        }

        match = PLAYER_RELEASE_PATTERN.matcher(command);
        if (match.matches() && gamePhase == GamePhase.RELEASE) {
            int applicationId = Integer.parseInt(match.group("applicationId"));
            if (game.getApplications().stream().filter(c -> c.getId()==applicationId).count()==0) {
                throw new GameRuleException(command, String.format("application %d is not in play", applicationId));
            }
            Application application = game.getApplications().stream().filter(obj -> obj.getId()==applicationId).findFirst().get();
            int badActionsUsed = application.canBeReleased(player);
            if (badActionsUsed < 0) {
                //cannot release => invalid input!!!!
                throw new GameRuleException(command, "you do not have enough cards to release this application");
            }
            if (player.getScore()==4 && badActionsUsed>0) {
                //cannot release => invalid input!!!!
                throw new GameRuleException(command, "you do not have enough cards to release this application. The last one must be done without any penalty!");
            }

            player.setAction(new ReleaseAction(applicationId));
            matchMessage(player, match);
            return;
        }

        throw new InvalidInputException(Game.getExpected(gamePhase), command);
    }

    public void deactivatePlayer(Player player, String message) {
        player.deactivate(escapeHTMLEntities(message));
        player.setScore(-1);
    }

    private String escapeHTMLEntities(String message) {
        return message
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }

    private void matchMessage(Player player, Matcher match) {
        String message = match.group("message");
        if (message != null) {
            String trimmed = message.trim();
            if (trimmed.length() > 48) {
                trimmed = trimmed.substring(0, 46) + "...";
            }
            player.setMessage(trimmed);
        }
    }
}
