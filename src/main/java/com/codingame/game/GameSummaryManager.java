package com.codingame.game;

import com.codingame.game.card.ActionCard;
import com.codingame.game.card.Card;
import com.codingame.game.card.CardType;
import com.codingame.gameengine.core.GameManager;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class GameSummaryManager {
    private List<String> lines;

    public GameSummaryManager() {
        this.lines = new ArrayList<>();
    }

    public String getSummary() {
        return toString();
    }

    public void clear() {
        this.lines.clear();
    }

    public void addPlayerBadCommand(Player player, InvalidInputException invalidInputException) {
        lines.add(
                GameManager.formatErrorMessage(
                        String.format(
                                "%s provided invalid input. Expected '%s'\nGot '%s'",
                                player.getNicknameToken(),
                                invalidInputException.getExpected(),
                                invalidInputException.getGot()
                        )
                )
        );
    }

    public void addPlayerRuleViolation(Player player, GameRuleException gameRuleException) {
        lines.add(
                GameManager.formatErrorMessage(
                        String.format(
                                "%s provided incorrect input. %s",
                                player.getNicknameToken(),
                                gameRuleException.getErrorMessage()
                        )
                )
        );
    }

    public void addPlayerTimeout(Player player) {
        lines.add(
                GameManager.formatErrorMessage(
                        String.format(
                                "%s has not provided an action in time.",
                                player.getNicknameToken()
                        )
                )
        );
    }

    public void addPlayerDisqualified(Player player) {
        lines.add(
                String.format(
                        "%s was disqualified.",
                        player.getNicknameToken()
                )
        );
    }

    public void addRelease(Player player, Application application) {
        lines.add(
                String.format(
                        "%s has released application #%d. They discard all their permanent skills.",
                        player.getNicknameToken(),
                        application.getId()
                )
        );
    }

    public void addMove(Player player) {
        lines.add(
                String.format(
                        "%s has moved to %s (%d) desk",
                        player.getNicknameToken(),
                        CardType.values()[player.getZoneId()].toString(),
                        player.getZoneId()
                )
        );
    }

    public void addTakeCard(Player player, Card cardTaken) {
        lines.add(
                String.format(
                        "%s took a %s",
                        player.getNicknameToken(),
                        cardTaken.getDescription()
                )
        );
    }

    public void addSemiUselessTakeCard(Player player, int zoneToTakeCardId) {
        lines.add(
                String.format(
                        "%s wanted to take a %s, but there is no left. So they took a bonus card instead.",
                        player.getNicknameToken(),
                        CardType.values()[zoneToTakeCardId].toString()
                )
        );
    }

    public void addUselessTakeCard(Player player, int zoneToTakeCardId) {
        lines.add(
                String.format(
                        "%s wanted to take a %s, but there is no left and no bonus card left either. So their action is cancelled.",
                        player.getNicknameToken(),
                        CardType.values()[zoneToTakeCardId].toString()
                )
        );
    }

    @Override
    public String toString() {
        return lines.stream().collect(Collectors.joining("\n"));
    }

    public void addFinishCycle(Player player) {
        lines.add(
            String.format(
                    "%s stopped for administrative tasks and must throw 2 competence cards",
                    player.getNicknameToken()
            )
        );
    }

    public void addWait(Player player) {
        lines.add(
                String.format(
                        "%s chose to do nothing",
                        player.getNicknameToken()
                )
        );
    }

    public void addEndGameTriggered(Player player) {
        lines.add(
                String.format(
                        "%s got 5 objectives. This triggers the end of the game",
                        player.getNicknameToken()
                )
        );
    }

    public void addPlayCard(Player player, ActionCard card) {
        lines.add(
                String.format(
                        "%s played a %s",
                        player.getNicknameToken(),
                        card.getDescription()
                )
        );
    }

    public void addMustGiveCard(Player player) {
        lines.add(
                String.format(
                        "%s is too close to the opponent. They must give them one competence",
                        player.getNicknameToken()
                )
        );
    }

    public void addGiveCard(Player player, Card card) {
        lines.add(
                String.format(
                        "%s gave a %s to their opponent",
                        player.getNicknameToken(),
                        card.getDescription()
                )
        );
    }

    public void addThrowCard(Player player, Card card) {
        lines.add(
                String.format(
                        "%s threw away a %s",
                        player.getNicknameToken(),
                        card.getDescription()
                )
        );
    }

    public void addThrowAllCards(Player player) {
        lines.add(
                String.format(
                        "%s must throw all their competence cards",
                        player.getNicknameToken()
                )
        );
    }

    public void addGetTechnicalDebt(Player player, int technicalDebt) {
        lines.add(
                String.format(
                        "%s got %d Technical Debt card(s)",
                        player.getNicknameToken(),
                        technicalDebt
                )
        );
    }

    public void addGetLessTechnicalDebtThanExpected(Player player, int technicalDebtToTake, int technicalDebtTaken) {
        lines.add(
                String.format(
                        "%s should have gotten %d Technical Debt card(s). there were not enough so they took only %d Technical Debt card(s).",
                        player.getNicknameToken(),
                        technicalDebtToTake,
                        technicalDebtTaken
                )
        );
    }

    private void addStartMovePhase(Player player) {
        lines.add(
                String.format(
                        "%s must move",
                        player.getNicknameToken()
                )
        );
    }

    private void addStartReleasePhase(Player player) {
        lines.add(
                String.format(
                        "%s can release an application",
                        player.getNicknameToken()
                )
        );
    }

    private void addStartGivePhase(Player player) {
        lines.add(
                String.format(
                        "%s must give a card to the opponent",
                        player.getNicknameToken()
                )
        );
    }

    private void addStartThrowPhase(Player player) {
        lines.add(
                String.format(
                        "%s must throw away %d card(s)",
                        player.getNicknameToken(),
                        player.getNumberOfCardsToThrow()
                )
        );
    }

    private void addStartPlayCardPhase(Player player) {
        lines.add(
                String.format(
                        "%s can play a card/perform an action",
                        player.getNicknameToken()
                )
        );
    }

    public void addStartPhase(Player player, GamePhase gamePhase) {
        switch (gamePhase) {
            case MOVE:
                addStartMovePhase(player);
                break;
            case GIVE_CARD:
                addStartGivePhase(player);
                break;
            case THROW_CARD:
                addStartThrowPhase(player);
                break;
            case PLAY_CARD:
                addStartPlayCardPhase(player);
                break;
            case RELEASE:
                addStartReleasePhase(player);
                break;
            default:
                lines.add(
                        String.format(
                                "WARNING: unknown gamePhase : %s",
                                gamePhase.toString()
                        )
                );
        }
    }

    public void addDiscardCards(Player player) {
        lines.add(
                String.format(
                        "%s discards their hand at the end of their turn",
                        player.getNicknameToken()
                )
        );
    }

    public void addCardsInHand(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append(player.getNicknameToken());
        sb.append(" has in hand: ");
        for (int i=0;i<player.getCardsInHand().size();++i) {
            sb.append(player.getCardsInHand().get(i).getDescription());
            if (i<player.getCardsInHand().size()-1) {
                sb.append(", ");
            }
        }
        lines.add(sb.toString());
    }

    public void addUselessRefactoring(Player player) {
        lines.add(
            String.format(
                    "%s refactors their code, but does not have any technical debt card to discard. So their action is cancelled.",
                    player.getNicknameToken()
            )
        );
    }

    public void addRefactoring(Player player) {
        lines.add(
                String.format(
                        "%s refactors their code and loses one technical debt card",
                        player.getNicknameToken()
                )
        );
    }

    public void addUselessCodeReview(Player player) {
        lines.add(
            String.format(
                    "%s does a code review but there is no more bonus cards to get. So their action is cancelled.",
                    player.getNicknameToken()
            )
        );
    }

    public void addSemiUsefulCodeReview(Player player) {
        lines.add(
            String.format(
                    "%s does a code review but gains only 1 bonus card in their discard pile (there is not enough bonus cards to get 2)",
                    player.getNicknameToken()
            )
        );
    }

    public void addUsefulCodeReview(Player player) {
        lines.add(
            String.format(
                    "%s does a code review and gains 2 bonus cards in their discard pile",
                    player.getNicknameToken()
            )
        );
    }

    public void addTraining(Player player) {
        lines.add(
                String.format(
                        "%s trains, draws 2 cards and can play one more card",
                        player.getNicknameToken()
                )
        );
    }

    public void addCoding(Player player) {
        lines.add(
                String.format(
                        "%s codes, draws 1 card and can play two more cards",
                        player.getNicknameToken()
                )
        );
    }

    public void addUselessContinuousIntegration(Player player, CardType secondaryCardType) {
        lines.add(
                String.format(
                        "%s tries to improve their continuous integration chain, but does not have any card %s to automate. So their action is cancelled",
                        player.getNicknameToken(),
                        secondaryCardType.toString()
                )
        );
    }

    public void addUsefulContinuousIntegration(Player player, CardType secondaryCardType) {
        lines.add(
                String.format(
                        "%s improves their continuous integration chain and automates a card %s",
                        player.getNicknameToken(),
                        secondaryCardType.toString()
                )
        );
    }

    public void addDailyRoutine(Player player) {
        lines.add(
                String.format(
                        "%s does their daily routine and puts the card in permanent skills (it will allow them to take cards from farther desks)",
                        player.getNicknameToken()
                )
        );
    }

    public void addArchitectureStudy(Player player) {
        lines.add(
                String.format(
                        "%s does an architecture study and puts the card in permanent skills (it will allow them to draw more cards)",
                        player.getNicknameToken()
                )
        );
    }

    public void addUselessTaskPrioritization(Player player, CardType cardTypeToThrow) {
        lines.add(
                String.format(
                        "%s tries to change their task prioritization, but does not have any card %s to throw. So their action is cancelled.",
                        player.getNicknameToken(),
                        cardTypeToThrow.toString()
                )
        );
    }

    public void addReallyUselessTaskPrioritization(Player player, CardType cardTypeTotake) {
        lines.add(
                String.format(
                        "%s tries to change their task prioritization, but there is no card %s and no bonus to take. So their action is cancelled.",
                        player.getNicknameToken(),
                        cardTypeTotake.toString()
                )
        );
    }

    public void addSemiUselessTaskPrioritization(Player player, CardType cardTypeTotake) {
        lines.add(
                String.format(
                        "%s tries to change their task prioritization. There is no card %s to take, so they take a bonus card instead",
                        player.getNicknameToken(),
                        cardTypeTotake.toString()
                )
        );
    }

    public void addUsefulTaskPrioritization(Player player, CardType cardTypeThrown, CardType cardTypeTaken) {
        lines.add(
                String.format(
                        "%s changes their task prioritization, throws a card %s and takes a card %s instead",
                        player.getNicknameToken(),
                        cardTypeThrown.toString(),
                        cardTypeTaken.toString()
                )
        );
    }

    public void addNoCardToPlay(Player player) {
        lines.add(
                String.format(
                        "%s does not have any card to play in their hand",
                        player.getNicknameToken()
                )
        );
    }

    public void addPlayerInput(String line) {
        lines.add(line);
    }

    public void addTieBreakEndOfGame(Player winner, Player a, Player b) {
        lines.add(
                winner.getNicknameToken() +
                        " won the game because their team had less technical debt:"
        );
        lines.add(a.getNicknameToken() + ": " + a.getTechnicalDebtCardsCount() + " technical debt");
        lines.add(b.getNicknameToken() + ": " + b.getTechnicalDebtCardsCount() + " technical debt");
    }

    public void addEndOfGame(Player a, Player b) {
        Player winner = a.getScore() > b.getScore() ? a : b;
        Player loser = a.getScore() > b.getScore() ? b : a;
        lines.add(winner.getNicknameToken() + " has released " + winner.getScore() + " applications and won the game");
        lines.add(loser.getNicknameToken() + " has only released " + loser.getScore() + " applications");
    }

    public void addInactivePlayerEndOfGame(Player activePlayer, Player inactivePlayer) {
        lines.add(inactivePlayer.getNicknameToken() + " is not active anymore");
        lines.add(activePlayer.getNicknameToken() + " has won the game");
    }
}
