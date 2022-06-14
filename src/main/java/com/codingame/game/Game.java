package com.codingame.game;

import com.codingame.game.action.*;

import java.util.*;
import java.util.stream.Collectors;

import com.codingame.game.card.*;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.view.View;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static java.lang.Math.abs;

@Singleton
public class Game {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GameSummaryManager gameSummaryManager;
    @Inject private Game game;
    @Inject private View view;
    @Inject private ApplicationFactory applicationFactory;
    private Random random;
    private Zone[] zones;
    private List<Application> applications;
    private boolean lastTurn;
    private static int nextCardId = 0;
    private static Stack<BonusCard> bonusCardsPool;
    private static Stack<TechnicalDebtCard> technicalDebtCardsPool;
    public static int getNextCardId() { return nextCardId++; }

    public ActionCard CreateNewActionCard(CardType cardType) {
        ActionCard card = new ActionCard(Game.getNextCardId(), cardType);
        view.addCard(card);
        return card;
    }

    public BonusCard CreateNewBonusCard() {
        BonusCard card = new BonusCard(Game.getNextCardId());
        view.addCard(card);
        return card;
    }

    public TechnicalDebtCard CreateNewTechnicalDebtCard() {
        TechnicalDebtCard card = new TechnicalDebtCard(Game.getNextCardId());
        view.addCard(card);
        return card;
    }

    public Stack<BonusCard> getBonusCardsPool() {
        return bonusCardsPool;
    }

    public Stack<TechnicalDebtCard> getTechnicalDebtCardsPool() {
        return technicalDebtCardsPool;
    }

    public Zone[] getZones() {
        return zones;
    }

    public void init(long seed) {
        String state = gameManager.getGameParameters().getProperty("state");
        if (state != null && System.getProperty("allow.config.override") != null) {
            initGameFromState(state);
        }

        lastTurn = false;
        random = new Random(seed);
        //create board & the action cards
        zones = new Zone[Config.ZONES_COUNT];
        for (int zoneId=0;zoneId<zones.length;++zoneId) {
            zones[zoneId] = new Zone(zoneId);
            for(int i=0;i<Config.CARDS_PER_ZONE;++i) {
                zones[zoneId].returnCard(CreateNewActionCard(CardType.values()[zoneId]));
            }
        }

        //create applications
        applications = new ArrayList<>();
        for (int i=0;i<Config.SMALL_APPLICATIONS_COUNT;++i) {
            applications.add(applicationFactory.createSmallApplication(random));
        }
        for (int i=0;i<Config.BIG_APPLICATIONS_COUNT;++i) {
            applications.add(applicationFactory.createBigApplication(random));
        }

        bonusCardsPool = new Stack<>();
        technicalDebtCardsPool = new Stack<>();
        for (int i=0;i<36;++i) {
            bonusCardsPool.push(CreateNewBonusCard());
        }
        for (int i=0;i<100;++i) {
            technicalDebtCardsPool.push(CreateNewTechnicalDebtCard());
        }

        //initialize players (and their cards)
        for(Player player : gameManager.getActivePlayers()) {
            player.init(random);
        }

        view.init();
        view.refreshCards(this);
        view.commitAll(0);
    }

    public Random getRandom() {
        return random;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }

    private void initGameFromState(String state) {
        //TODO: understand what it does and code it if we need it
    }

    public static BonusCard getNextBonusCard() {
        if (bonusCardsPool.empty()) {
            return null;
        }
        return bonusCardsPool.pop();
    }

    public static TechnicalDebtCard getNextTechnicalDebtCard() {
        if (technicalDebtCardsPool.empty()) {
            return null;
        }
        return technicalDebtCardsPool.pop();
    }

    public static String getExpected(GamePhase gamePhase) {
        switch(gamePhase) {
            case MOVE:
                return "MOVE <zoneId> | MOVE <zoneToMoveId> <zoneToTakeCardId> | RANDOM ";
            case GIVE_CARD:
                return "GIVE <cardtype> | RANDOM ";
            case THROW_CARD:
                return "THROW <cardtype> | RANDOM ";
            case PLAY_CARD:
                return "TRAINING | CODING | DAILY_ROUTINE | TASK_PRIORITIZATION <cardTypeToThrow> <cardTypeToTake> | ARCHITECTURE_STUDY | CONTINUOUS_INTEGRATION <cardtype> | CODE_REVIEW | REFACTORING | WAIT | RANDOM ";
            case RELEASE:
                return "RELEASE <applicationId> | WAIT | RANDOM ";
        }
        return "UNKNOWN. You should not be here";
    }

    //Sends input data to the player bot for the initialization phase
    public List<String> getGlobalInfoFor(Player player) {
        List<String> lines = new ArrayList<>();

        return lines;
    }

    public boolean isGameOver() {
        // one player is deactivated
        List<Player> activePlayers = gameManager.getActivePlayers();
        if (activePlayers.size() <= 1) {
            return true;
        }

        return false;
        //TODO: the game isn't over if a player can still improve its rank
        //return gameManager.getActivePlayers().stream().noneMatch(this::canImproveRanking);
    }

    public void performGameOver() {
        // one player deactivated
        List<Player> activePlayers = gameManager.getActivePlayers();
        if (activePlayers.size() <= 1) {
            if (activePlayers.size() == 1) {
                gameManager.addToGameSummary(
                        String.format(
                                "Only %s is still playing!",
                                activePlayers.get(0).getNicknameToken()
                        )
                );
            } else {
                gameManager.addToGameSummary("No player remaining!");
            }
            return;
        }
    }

    public void performGameUpdate(Player player) {

        view.startOfTurn();
        view.setPlayerMessage(player);
        if (player.getAction().isMove()) {
            move(player, (MoveAction) player.getAction());
        }
        else if (player.getAction().isGive()) {
            giveCard(player, (GiveAction) player.getAction());
        }
        else if (player.getAction().isPlay()) {
            playCard(player, (PlayAction) player.getAction());
        }
        else if (player.getAction().isThrow()) {
            throwCard(player, (ThrowAction) player.getAction());
        }
        else if (player.getAction().isRelease()) {
            release(player, (ReleaseAction) player.getAction());
        }
        else if (player.getAction().isWait()) {
            gameSummaryManager.addWait(player);
            player.setPlaysLeft(0);
        }

        // update view
        view.endOfTurn();
    }

    private void playCard(Player player, PlayAction action) {
        switch (action.getActionCardType()) {
            case TRAINING:
                train(player);
                break;
            case CODING:
                coding(player);
                break;
            case DAILY_ROUTINE:
                dailyRoutine(player);
                break;
            case TASK_PRIORITIZATION:
                taskPrioritization(player, action);
                break;
            case ARCHITECTURE_STUDY:
                architectureStudy(player);
                break;
            case CONTINUOUS_INTEGRATION:
                continuousIntegration(player, action);
                break;
            case CODE_REVIEW:
                codeReview(player);
                break;
            case REFACTORING:
                refactoring(player);
                break;
        }
        //TODO: remove one action?
    }

    private void train(Player player) {
        player.discardCardFromHand(CardType.TRAINING);
        player.drawCards(2, random, view);
        player.addMorePlays(1);
        gameSummaryManager.addTraining(player);
    }

    private void coding(Player player) {
        player.discardCardFromHand(CardType.CODING);
        player.drawCards(1, random, view);
        player.addMorePlays(2);
        gameSummaryManager.addCoding(player);
    }

    private void dailyRoutine(Player player) {
        player.playPermanentSkillCardFromHand(CardType.DAILY_ROUTINE);
        gameSummaryManager.addDailyRoutine(player);
    }

    private void taskPrioritization(Player player, PlayAction action) {
        player.discardCardFromHand(CardType.TASK_PRIORITIZATION);
        Card thrownCard = player.removeCardInHand(action.getSecondaryCardType());
        if (thrownCard == null) {
            gameSummaryManager.addUselessTaskPrioritization(player, action.getSecondaryCardType());
            return;
        }
        if (thrownCard.isTechnicalDebt()) {
            gameSummaryManager.addUselessTaskPrioritization(player, action.getSecondaryCardType());
            player.addNewCardInHand(thrownCard);
            return;
        }
        Card cardTaken = null;
        if (action.getThirdCardType().equals(CardType.BONUS)) {
            if (!bonusCardsPool.empty()) {
                cardTaken = bonusCardsPool.pop();
            }
        } else {
            cardTaken = zones[action.getThirdCardType().ordinal()].getNextCard();
        }
        if (cardTaken==null) {
            gameSummaryManager.addReallyUselessTaskPrioritization(player, action.getThirdCardType());
            return;
        }
        if (thrownCard.isActionCard()) {
            zones[thrownCard.getCardType().ordinal()].returnCard(thrownCard);
        } else { //bonus card
            bonusCardsPool.push((BonusCard) thrownCard);
        }
        player.addNewCardInHand(cardTaken);
        if (cardTaken.isGoodActionBonus() && action.getThirdCardType() != CardType.BONUS) {
            gameSummaryManager.addSemiUselessTaskPrioritization(player, action.getThirdCardType());
        } else {
            gameSummaryManager.addUsefulTaskPrioritization(player, action.getSecondaryCardType(), cardTaken.getCardType());
        }
    }

    private void architectureStudy(Player player) {
        player.playPermanentSkillCardFromHand(CardType.ARCHITECTURE_STUDY);
        gameSummaryManager.addArchitectureStudy(player);
    }

    private void continuousIntegration(Player player, PlayAction action) {
        player.discardCardFromHand(CardType.CONTINUOUS_INTEGRATION);
        Card cardToAutomate = player.removeCardInHand(action.getSecondaryCardType());
        if (cardToAutomate == null) {
            gameSummaryManager.addUselessContinuousIntegration(player, action.getSecondaryCardType());
        }
        else if (cardToAutomate.isTechnicalDebt()) {
            player.addNewCardInHand(cardToAutomate);
            gameSummaryManager.addUselessContinuousIntegration(player, action.getSecondaryCardType());
        } else {
            gameSummaryManager.addUsefulContinuousIntegration(player, action.getSecondaryCardType());
            player.addAutomatedCard(cardToAutomate);
        }
    }

    private void codeReview(Player player) {
        player.discardCardFromHand(CardType.CODE_REVIEW);
        BonusCard card1 = getNextBonusCard();
        BonusCard card2 = getNextBonusCard();
        if (card1 == null) {
            gameSummaryManager.addUselessCodeReview(player);
        }
        else {
            player.addNewCardInDiscardPile(card1);
            view.addCardInDiscardPile(card1, player);
            if (card2 == null) {
                gameSummaryManager.addSemiUsefulCodeReview(player);
            }
            else {
                player.addNewCardInDiscardPile(card2);
                view.addCardInDiscardPile(card2, player);
                gameSummaryManager.addUsefulCodeReview(player);
            }
        }
    }

    private void refactoring(Player player) {
        player.discardCardFromHand(CardType.REFACTORING);
        Card thrownCard = player.removeCardInHand(CardType.TECHNICAL_DEBT);
        if (thrownCard == null) {
            gameSummaryManager.addUselessRefactoring(player);
        }
        else {
            technicalDebtCardsPool.push((TechnicalDebtCard) thrownCard);
            view.playerThrowsCard(0, thrownCard);
            gameSummaryManager.addRefactoring(player);
        }
        view.adaptTechnicalDebtBar(player, 0);
    }

    private void throwCard(Player player, ThrowAction action) {
        Card thrownCard = player.removeCardInHand(action.getCardType());
        if (thrownCard.isGoodActionBonus()) {
            bonusCardsPool.push((BonusCard)thrownCard);
        }
        else {
            zones[thrownCard.getCardType().ordinal()].returnCard(thrownCard);
        }
        player.setNumberOfCardsToThrow(player.getNumberOfCardsToThrow()-1);
        gameSummaryManager.addThrowCard(player, thrownCard);
        view.playerThrowsCard(0, thrownCard);
        if (player.getNumberOfCardsToThrow()==0) {
            view.movePlayer(player, -1, player.getZoneId());
            gameSummaryManager.addMove(player);

            //check distance
            Player opponentPlayer = gameManager.getActivePlayers().get((player.getIndex() + 1) % 2);
            if (Config.GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT
                    && opponentPlayer.getZoneId()>=0
                    && ((abs(opponentPlayer.getZoneId() - player.getZoneId())<=1)
                        || (abs(opponentPlayer.getZoneId() - player.getZoneId())==7))) {
                //too close, must give one card
                gameSummaryManager.addMustGiveCard(player);
                List<Card> giveableCards = player.getNonTechnicalDebtCardsInHand();
                if (giveableCards.isEmpty()) {
                    //automatic technical debt
                    int technicalDebtToTake = 2;
                    int technicalDebtTaken = 0;
                    for(int i=0;i<technicalDebtToTake;++i) {
                        Card technicalDebt = getNextTechnicalDebtCard();
                        if (technicalDebt!=null) {
                            player.addNewCardInDiscardPile(technicalDebt);
                            view.addCardInDiscardPile(technicalDebt, player);
                            technicalDebtTaken++;
                        }
                    }
                    if (technicalDebtTaken == technicalDebtToTake) {
                        gameSummaryManager.addGetTechnicalDebt(player, technicalDebtToTake);
                    } else {
                        gameSummaryManager.addGetLessTechnicalDebtThanExpected(player, technicalDebtToTake, technicalDebtTaken);
                    }
                    view.adaptTechnicalDebtBar(player, 0);
                }
                else if (giveableCards.size() == 1 || giveableCards.stream().allMatch(card -> card.getCardType().equals(giveableCards.get(0).getCardType()))) {
                    //automatic give, but no technical debt
                    Card givenCard = giveableCards.get(0);
                    player.removeCardInHand(givenCard);
                    opponentPlayer.addNewCardInHand(givenCard);
                    gameSummaryManager.addGiveCard(player, givenCard);
                }
                else {
                    player.setMustGiveCard(true);
                    return;
                }
            }

            takeCard(player);
        }
    }

    private void giveCard(Player player, GiveAction action) {
        Card givenCard = player.removeCardInHand(action.getCardType());
        Player opponentPlayer = gameManager.getActivePlayers().get((player.getIndex() + 1) % 2);
        opponentPlayer.addNewCardInHand(givenCard);
        player.hasGivenCard();
        gameSummaryManager.addGiveCard(player, givenCard);
        if (player.getNumberOfCardsToThrow()==0) {
            takeCard(player);
        }
    }

    private void release(Player player, ReleaseAction action) {
        Application application = applications.stream().filter(obj -> obj.getId()==action.getApplicationId()).findFirst().get();
        int badActionsUsed = application.canBeReleased(player);
        applications.remove(application);
        gameSummaryManager.addRelease(player, application);
        gameManager.addTooltip(
                player, String.format(
                        "%s released an objective",
                        player.getNicknameToken()
                )
        );
        player.setScore(player.getScore()+1);
        player.discardPermanentSkills();
        int technicalDebtTaken = 0;
        for(int i=0;i<badActionsUsed;++i) {
            Card technicalDebt = getNextTechnicalDebtCard();
            if (technicalDebt!=null) {
                player.addNewCardInDiscardPile(technicalDebt);
                view.addCardInDiscardPile(technicalDebt, player);
                technicalDebtTaken++;
            }
        }
        if (badActionsUsed>0) {

            if (technicalDebtTaken == badActionsUsed) {
                gameSummaryManager.addGetTechnicalDebt(player, badActionsUsed);
            } else {
                gameSummaryManager.addGetLessTechnicalDebtThanExpected(player, badActionsUsed, technicalDebtTaken);
            }
            view.adaptTechnicalDebtBar(player, 0);
            view.animateBadRelease(player);
        }
        else {
            view.animateGoodRelease(player);
        }
        if (player.getScore()>=Config.APPLICATIONS_TO_WIN) {
            lastTurn = true;
            gameSummaryManager.addEndGameTriggered(player);
        }

        view.animateScore(player, player.getScore());
    }

    private void move(Player player, MoveAction action) {
        if (Config.LOSE_CARDS_AT_END_OF_CYCLE && action.getZoneToMoveId() < player.getZoneId()) {
            //End of cycle => technical debt
            gameSummaryManager.addFinishCycle(player);
            List<Card> throwableCards = player.getNonTechnicalDebtCardsInHand();
            if (throwableCards.size() <= 2) {
                //automatic technical debt
                int technicalDebtToTake = 2-throwableCards.size();
                gameSummaryManager.addThrowAllCards(player);
                for (Card thrownCard : throwableCards) {
                    player.removeCardInHand(thrownCard);
                    if (thrownCard.isGoodActionBonus()) {
                        bonusCardsPool.push((BonusCard) thrownCard);
                    } else {
                        zones[thrownCard.getCardType().ordinal()].returnCard(thrownCard);
                    }
                    gameSummaryManager.addThrowCard(player, thrownCard);
                    view.playerThrowsCard(0.5, thrownCard);
                }
                int technicalDebtTaken = 0;
                for(int i=0;i<technicalDebtToTake;++i) {
                    Card technicalDebt = getNextTechnicalDebtCard();
                    if (technicalDebt!=null) {
                        player.addNewCardInDiscardPile(technicalDebt);
                        view.addCardInDiscardPile(technicalDebt, player);
                        technicalDebtTaken++;
                    }
                }
                if (technicalDebtToTake>0) {

                    if (technicalDebtTaken == technicalDebtToTake) {
                        gameSummaryManager.addGetTechnicalDebt(player, technicalDebtToTake);
                    } else {
                        gameSummaryManager.addGetLessTechnicalDebtThanExpected(player, technicalDebtToTake, technicalDebtTaken);
                    }
                    view.adaptTechnicalDebtBar(player, 0);
                }
            }
            else if (throwableCards.stream().allMatch(card -> card.getCardType().equals(throwableCards.get(0).getCardType()))) {
                //only one card type => automatic throw
                for (int i=0;i<2;++i) {
                    Card thrownCard = throwableCards.get(0);
                    player.removeCardInHand(thrownCard);
                    if (thrownCard.isGoodActionBonus()) {
                        bonusCardsPool.push((BonusCard) thrownCard);
                    } else {
                        zones[thrownCard.getCardType().ordinal()].returnCard(thrownCard);
                    }
                    gameSummaryManager.addThrowCard(player, thrownCard);
                    view.playerThrowsCard(0.5, thrownCard);
                }
            }
            else {
                player.setNumberOfCardsToThrow(2);
            }
        }
        if (player.getNumberOfCardsToThrow()>0) {
            view.movePlayer(player, player.getZoneId(), -1);
            player.setZoneId(action.getZoneToMoveId());
            player.setMustTakeCard(action.getZoneToTakeCardId());
            return;
        }
        view.movePlayer(player, player.getZoneId(), action.getZoneToMoveId());
        player.setZoneId(action.getZoneToMoveId());
        gameSummaryManager.addMove(player);

        //check distance
        Player opponentPlayer = gameManager.getActivePlayers().get((player.getIndex() + 1) % 2);
        if (Config.GIVE_CARD_IF_TOO_CLOSE_TO_OPPONENT
                && opponentPlayer.getZoneId()>=0
                && ((abs(opponentPlayer.getZoneId() - player.getZoneId())<=1)
                    || (abs(opponentPlayer.getZoneId() - player.getZoneId())==7))) {
            //too close, must give one card
            gameSummaryManager.addMustGiveCard(player);
            List<Card> giveableCards = player.getNonTechnicalDebtCardsInHand();
            if (giveableCards.isEmpty()) {
                //automatic technical debt
                int technicalDebtToTake = 2;
                int technicalDebtTaken = 0;
                for(int i=0;i<technicalDebtToTake;++i) {
                    Card technicalDebt = getNextTechnicalDebtCard();
                    if (technicalDebt!=null) {
                        player.addNewCardInDiscardPile(technicalDebt);
                        view.addCardInDiscardPile(technicalDebt, player);
                        technicalDebtTaken++;
                    }
                }
                if (technicalDebtTaken == technicalDebtToTake) {
                    gameSummaryManager.addGetTechnicalDebt(player, technicalDebtToTake);
                } else {
                    gameSummaryManager.addGetLessTechnicalDebtThanExpected(player, technicalDebtToTake, technicalDebtTaken);
                }
                view.adaptTechnicalDebtBar(player, 0);
            }
            else if (giveableCards.size() == 1 || giveableCards.stream().allMatch(card -> card.getCardType().equals(giveableCards.get(0).getCardType()))) {
                //automatic give, but no technical debt
                Card givenCard = giveableCards.get(0);
                player.removeCardInHand(givenCard);
                opponentPlayer.addNewCardInHand(givenCard);
                gameSummaryManager.addGiveCard(player, givenCard);
            }
            else {
                player.setMustGiveCard(true);
            }
        }

        player.setMustTakeCard(action.getZoneToTakeCardId());
        if (!player.mustGiveCard()) {
            takeCard(player);
        }
    }

    public void takeCard(Player player) {
        Card cardTaken = zones[player.getZoneToTakeCardId()].getNextCard();
        if (cardTaken==null) {
            gameSummaryManager.addUselessTakeCard(player, player.getZoneToTakeCardId());
            return;
        }
        player.addNewCardInHand(cardTaken);
        if (cardTaken.isGoodActionBonus()) {
            gameSummaryManager.addSemiUselessTakeCard(player, player.getZoneToTakeCardId());
        } else {
            gameSummaryManager.addTakeCard(player, cardTaken);
        }
        view.playerTakesCardInHand(cardTaken);
        player.setMustTakeCard(-1);
    }

    public void resetGameTurnData() {
        gameSummaryManager.clear();
        for (Player player : gameManager.getActivePlayers()) {
            player.setMessage(null);
            view.setPlayerMessage(player);
        }
    }

    //Sends input data to the player bot for one turn
    public List<String> getCurrentFrameInfoFor(Player player, GamePhase gamePhase) {
        List<String> lines = new ArrayList<>();
        //objectives data
        lines.add(Integer.toString(applications.size()));
        for(Application application : applications) {
            lines.add(application.toString());
        }
        //Player information, receiving player first
        Player opponentPlayer = gameManager.getActivePlayers().get((player.getIndex() + 1) % 2);
        lines.add(String.format("%d %d %d %d", player.getZoneId(), player.getScore(), player.getPermanentDailyRoutineCardsCount(), player.getPermanentArchitectureStudyCardsCount()));
        lines.add(String.format("%d %d %d %d", opponentPlayer.getZoneId(), opponentPlayer.getScore(), opponentPlayer.getPermanentDailyRoutineCardsCount(), opponentPlayer.getPermanentArchitectureStudyCardsCount()));
        List<String> cardLocations = new ArrayList<>();
        cardLocations.addAll(player.getCardsInfo());
        cardLocations.add("OPPONENT_" + opponentPlayer.getGlobalCardsInfo());
        if (opponentPlayer.getAutomatedCards().size() > 0) {
            cardLocations.add("OPPONENT_" + opponentPlayer.getAutomatedCardsInfo());
        }
        lines.add(Integer.toString(cardLocations.size()));
        lines.addAll(cardLocations);

        //possible moves
        List<String> possibleActions = getPossibleActions(player, gamePhase);
        lines.add(String.valueOf(possibleActions.size()));
        possibleActions
                .stream()
                .forEach(lines::add);
        return lines;
    }

    private List<String> getPossibleActions(Player player, GamePhase gamePhase) {
        switch (gamePhase) {
            case MOVE:
                return getPossibleMoves(player);
            case GIVE_CARD:
                return getPossibleGives(player);
            case THROW_CARD:
                return getPossibleThrows(player);
            case PLAY_CARD:
                return getPossiblePlays(player);
            case RELEASE:
                return getPossibleReleases(player);
            default:
                return null;
        }
    }

    private List<String> getPossibleMoves(Player player) {
        List<String> moves = new ArrayList<>();
        int zoneMaxShift = player.getPermanentDailyRoutineCardsCount();
        if (zoneMaxShift>0) {
            for (int i=1;i<Config.ZONES_COUNT;++i) {
                int moveTarget = (player.getZoneId() + i) % Config.ZONES_COUNT;
                for(int j=-zoneMaxShift;j<=zoneMaxShift;++j) {
                    int zone = (moveTarget + j +Config.ZONES_COUNT)%Config.ZONES_COUNT;
                    if (zone != moveTarget) {
                        moves.add(String.format("MOVE %d %d", moveTarget, zone));
                    }
                    else {
                        moves.add(String.format("MOVE %d", moveTarget));
                    }
                }
            }
        }
        else {
            for (int i=1;i<Config.ZONES_COUNT;++i) {
                moves.add(String.format("MOVE %d", (player.getZoneId() + i)%Config.ZONES_COUNT));
            }
        }
        if (player.getZoneId()<0) {
            //1st turn, can go to 8 places
            moves.add(String.format("MOVE %d", (player.getZoneId() + Config.ZONES_COUNT)%Config.ZONES_COUNT));
        }
        moves.add("RANDOM");
        return moves;
    }

    private List<String> getPossibleGives(Player player) {
        List<String> gives = new ArrayList<>();
        for (Card card : player.getNonTechnicalDebtCardsInHand()) {
            String giveCard = String.format("GIVE %d", card.getCardType().ordinal());
            if (!gives.contains(giveCard)) {
                gives.add(giveCard);
            }
        }
        gives.add("RANDOM");
        return gives;
    }

    private List<String> getPossibleThrows(Player player) {
        List<String> throwCards = new ArrayList<>();
        for (Card card : player.getNonTechnicalDebtCardsInHand()) {
            String throwCard = String.format("THROW %d", card.getCardType().ordinal());
            if (!throwCards.contains(throwCard)) {
                throwCards.add(throwCard);
            }
        }
        throwCards.add("RANDOM");
        return throwCards;
    }

    private List<String> getPossiblePlays(Player player) {
        List<String> cardPlays = new ArrayList<>();
        if (!Config.CAN_PLAY_COMPLEX_CARDS) {
            for (Card card : player.getSimpleActionCardsInHand()) {
                String cardPlay = card.getCardType().toString();
                if (!cardPlays.contains(cardPlay)) {
                    cardPlays.add(cardPlay);
                }
            }
        }
        else {
            for (Card card : player.getActionCardsInHand()) {
                if (card.getCardType() == CardType.TASK_PRIORITIZATION) {
                    for (Card cardToReplace : player.getNonTechnicalDebtCardsInHand()) {
                        if (cardToReplace.getId() != card.getId()) {
                            for (int zoneId = 0; zoneId < Config.ZONES_COUNT; ++zoneId) {
                                String cardPlay = String.format("%s %d %d", card.getCardType().toString(), cardToReplace.getCardType().ordinal(), zoneId);
                                if (!cardPlays.contains(cardPlay)) {
                                    cardPlays.add(cardPlay);
                                }
                            }
                        }
                    }
                }
                else if (card.getCardType() == CardType.CONTINUOUS_INTEGRATION) {
                    for (Card cardToAutomate : player.getNonTechnicalDebtCardsInHand()) {
                        if (cardToAutomate.getId() != card.getId()) {
                            String cardPlay = String.format("%s %d", card.getCardType().toString(), cardToAutomate.getCardType().ordinal());
                            if (!cardPlays.contains(cardPlay)) {
                                cardPlays.add(cardPlay);
                            }
                        }
                    }
                }
                else {
                    String cardPlay = card.getCardType().toString();
                    if (!cardPlays.contains(cardPlay)) {
                        cardPlays.add(cardPlay);
                    }
                }
            }
        }
        cardPlays.add("RANDOM");
        cardPlays.add("WAIT");
        return cardPlays;
    }

    private List<String> getPossibleReleases(Player player) {
        List<String> releases = new ArrayList<>();
        for (Application application : getReleasableApplications(player)) {
            releases.add(String.format("RELEASE %d", application.getId()));
        }
        releases.add("RANDOM");
        releases.add("WAIT");
        return releases;
    }

    private String getZonesInfo() {
        return String.join(" ", Arrays.stream(zones).map(zone -> String.valueOf(zone.getCardsCount())).collect(Collectors.toList()));
    }

    public boolean canReleaseApplication(Player player) {
        for (Application application : applications) {
            if (application.canBeReleased(player)>=0) {
                return true;
            }
        }
        return false;
    }

    public List<Application> getReleasableApplications(Player player) {
        List<Application> releasable = new ArrayList<>();
        for (Application application : applications) {
            if (application.canBeReleased(player)>=0) {
                releasable.add(application);
            }
        }
        return releasable;
    }

    public List<Application> getApplications() {
        return applications;
    }
}
