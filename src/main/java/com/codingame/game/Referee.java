package com.codingame.game;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.view.View;
import com.google.inject.Inject;

import java.util.Properties;
import java.util.stream.Collectors;

public class Referee extends AbstractReferee {
    // Uncomment the line below and comment the line under it to create a Solo Game
    // @Inject private SoloGameManager<Player> gameManager;
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private Game game;
    @Inject private GameSummaryManager gameSummaryManager;
    @Inject private CommandManager commandManager;
    @Inject private View view;
    @Inject private EndScreenModule endScreenModule;
    private static final int MAX_TURNS = 200;
    private int turnId=0;
    private int activePlayerId;
    private GamePhase gamePhase;
    long seed;
    int maxFrames;
    boolean gameOverFrame;

    @Override
    public void init() {
        // Initialize your game here.
        turnId=0;
        activePlayerId = 0;
        gamePhase = GamePhase.MOVE;
        gameOverFrame = false;
        //viewModule.setReferee(this);
        this.seed = gameManager.getSeed();

        // Set configuration depending on game rules:
        Config.setDefaultValueByLevel(LeagueRules.fromIndex(gameManager.getLeagueLevel()));

        // Override configuration with game parameters:
        if (System.getProperty("allow.config.override") != null) {
            computeConfiguration(gameManager.getGameParameters());
        }
        maxFrames = MAX_TURNS;

        try {
            //   Config.load(gameManager.getGameParameters());
            // Config.export(gameManager.getGameParameters());
            gameManager.setFrameDuration(500);
            gameManager.setMaxTurns(MAX_TURNS);
            gameManager.setFirstTurnMaxTime(1000);
            gameManager.setTurnMaxTime(50);

            game.init(seed);
            sendGlobalInfo();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Referee failed to initialize");
            abort();
        }
    }

    private void computeConfiguration(Properties gameParameters) {
        Config.apply(gameParameters);
    }

    @Override
    public void gameTurn(int turn) {
        turnId++;
        if (!gameOverFrame) {
            game.resetGameTurnData();

            Player player = gameManager.getPlayer(activePlayerId);
            gameSummaryManager.addStartPhase(player, gamePhase);
            gameSummaryManager.addCardsInHand(player);
            player.sendInputLine(gamePhase.toString());
            for (String line : game.getCurrentFrameInfoFor(player, gamePhase)) {
                player.sendInputLine(line);
            }

            player.execute();
            try {
                commandManager.parseCommands(player, player.getOutputs(), game, gamePhase);
                if (player.isActive()) {
                    game.performGameUpdate(player);
                }
            } catch (TimeoutException e) {
                commandManager.deactivatePlayer(player, "Timeout!");
                gameSummaryManager.addPlayerTimeout(player);
                gameSummaryManager.addPlayerDisqualified(player);
            } catch (Exception e) {
                commandManager.deactivatePlayer(player, e.getMessage());
                gameSummaryManager.addPlayerTimeout(player);
                gameSummaryManager.addPlayerDisqualified(player);
            }

            setNextPhase(player);
            gameManager.addToGameSummary(gameSummaryManager.getSummary());

            view.refreshCards(game);
            view.refreshApplications(game);
            view.refreshPlayersTooltips(game);

            if (game.isGameOver()) {
                gameOverFrame = true;
            }
        } else {
            game.resetGameTurnData();
            game.performGameOver();
            gameManager.endGame();
        }
    }

    private Boolean canPlayActionCard(Player activePlayer) {
        return (Config.CAN_PLAY_SIMPLE_CARDS && activePlayer.canPlaySimpleCard())
                || (Config.CAN_PLAY_COMPLEX_CARDS && activePlayer.canPlayCard());
    }

    private void setNextPhase(Player activePlayer) {
        if (gamePhase == GamePhase.MOVE || gamePhase == GamePhase.GIVE_CARD || gamePhase == GamePhase.THROW_CARD) {
            if (activePlayer.getNumberOfCardsToThrow()>0) {
                gamePhase = GamePhase.THROW_CARD;
            }
            else if (activePlayer.mustGiveCard()) {
                gamePhase = GamePhase.GIVE_CARD;
            }
            else if (canPlayActionCard(activePlayer)) {
                startPlayCardPhase(activePlayer);
            }
            else if (game.canReleaseApplication(activePlayer)) {
                gamePhase = GamePhase.RELEASE;
            }
            else {
                switchToNextPlayer(activePlayer);
            }
        }
        else if (gamePhase == GamePhase.PLAY_CARD) {
            activePlayer.removeOnePlay();
            if (activePlayer.getPlaysLeft() <= 0) {
                gameSummaryManager.addNoMorePlayingCardAllowed(activePlayer);
                if (game.canReleaseApplication(activePlayer)) {
                    gamePhase = GamePhase.RELEASE;
                }
                else {
                    switchToNextPlayer(activePlayer);
                }
            }
            else if (!canPlayActionCard(activePlayer)) {
                gameSummaryManager.addNoCardToPlay(activePlayer);
                activePlayer.setPlaysLeft(0);
                if (game.canReleaseApplication(activePlayer)) {
                    gamePhase = GamePhase.RELEASE;
                }
                else {
                    switchToNextPlayer(activePlayer);
                }
            }
        }
        else if (gamePhase == GamePhase.RELEASE) {
            switchToNextPlayer(activePlayer);
        }
    }

    private void startPlayCardPhase(Player player) {
        gamePhase = GamePhase.PLAY_CARD;
        player.addMorePlays(1);
    }

    private void switchToNextPlayer(Player player) {
        player.discardAndRedrawCards(game.getRandom(), view);
        gameSummaryManager.addDiscardCards(player);
        gamePhase = GamePhase.MOVE;
        //move to next player
        activePlayerId = (activePlayerId + 1) % gameManager.getPlayerCount();
        if (activePlayerId==0 && game.isLastTurn()) {
            gameOverFrame = true;
        }
    }

    //player input before the 1st turn
    private void sendGlobalInfo() {
        for (Player player : gameManager.getActivePlayers()) {
            for (String line : game.getGlobalInfoFor(player)) {
                player.sendInputLine(line);
            }
        }
    }

    private void abort() {
        System.err.println("Unexpected game end");
        gameManager.endGame();
    }

    @Override
    public void onEnd() {
        Player a = gameManager.getPlayer(0);
        Player b = gameManager.getPlayer(1);
        if (a.isActive() && !b.isActive()) {
            a.setScore(1);
            a.setScoreDescription("only player still active");
            b.setScore(0);
            b.setScoreDescription("timed out");
            gameSummaryManager.addInactivePlayerEndOfGame(a, b);
        } else if (!a.isActive() && b.isActive()) {
            a.setScore(0);
            a.setScoreDescription("timed out");
            b.setScore(1);
            b.setScoreDescription("only player still active");
            gameSummaryManager.addInactivePlayerEndOfGame(b, a);
        } else if (a.getScore() == b.getScore()) {
            // Tie breaker
            a.setScore(-a.getTechnicalDebtCardsCount());
            b.setScore(-b.getTechnicalDebtCardsCount());
            a.setScoreDescription(String.format("has %d technical debts", a.getTechnicalDebtCardsCount()));
            b.setScoreDescription(String.format("has %d technical debts", b.getTechnicalDebtCardsCount()));
            if (a.getScore() == b.getScore()) {
                gameManager.addToGameSummary("Tie!");
            } else {
                Player winner = a.getScore() > b.getScore() ? a : b;
                gameSummaryManager.addTieBreakEndOfGame(winner, a, b);
            }
        }
        else {
            a.setScoreDescription(String.format("released %d applications", a.getScore()));
            b.setScoreDescription(String.format("released %d applications", b.getScore()));
            gameSummaryManager.addEndOfGame(a, b);
        }
        endScreenModule.setTitleRankingsSprite("logo.png");
        endScreenModule.setScores(
                gameManager.getPlayers()
                        .stream()
                        .mapToInt(player -> player.getScore())
                        .toArray(),

                gameManager.getPlayers()
                        .stream()
                        .map(player -> player.getScoreDescription())
                        .collect(Collectors.toList())
                        .toArray(new String[2])
        );
    }
}
