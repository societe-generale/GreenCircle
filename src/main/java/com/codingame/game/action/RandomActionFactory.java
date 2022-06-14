package com.codingame.game.action;

import com.codingame.game.Config;
import com.codingame.game.Game;
import com.codingame.game.Application;
import com.codingame.game.Player;

import com.codingame.game.card.Card;
import com.codingame.game.card.CardType;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class RandomActionFactory {

    public MoveAction createMoveAction(Game game, Player player) {
        int zoneId=(player.getZoneId() + 1 + game.getRandom().nextInt(Config.ZONES_COUNT-1)) % Config.ZONES_COUNT;
        return new MoveAction(zoneId, zoneId);
    }

    public ReleaseAction createReleaseAction(Game game, Player player) {
        List<Application> releasableApplications = game.getReleasableApplications(player);
        int applicationId= releasableApplications.get(game.getRandom().nextInt(releasableApplications.size())).getId();
        return new ReleaseAction(applicationId);
    }

    public GiveAction createGiveAction(Game game, Player player) {
        List<Card> giveableCards = player.getNonTechnicalDebtCardsInHand();
        CardType cardType=giveableCards.get(game.getRandom().nextInt(giveableCards.size())).getCardType();
        return new GiveAction(cardType);
    }

    public Action createPlayAction(Game game, Player player) {
        List<Card> actionCards = player.getActionCardsInHand();
        if (!Config.CAN_PLAY_COMPLEX_CARDS) {
            actionCards = player.getSimpleActionCardsInHand();
        }
        if (actionCards.size()==0) return new WaitAction();
        Card cardToPlay=actionCards.get(game.getRandom().nextInt(actionCards.size()));
        actionCards.remove(cardToPlay);
        if (cardToPlay.getCardType()==CardType.CONTINUOUS_INTEGRATION || cardToPlay.getCardType()==CardType.TASK_PRIORITIZATION) {
            if (actionCards.size()==0){
                return new WaitAction();
            }
            Card secondaryCard=actionCards.get(game.getRandom().nextInt(actionCards.size()));
            actionCards.remove(secondaryCard);
            if (cardToPlay.getCardType()==CardType.TASK_PRIORITIZATION) {
                int zoneId=game.getRandom().nextInt(Config.ZONES_COUNT);
                return new PlayAction(cardToPlay.getCardType(), secondaryCard.getCardType(), CardType.values()[zoneId]);
            }
            return new PlayAction(cardToPlay.getCardType(), secondaryCard.getCardType());
        }
        return new PlayAction(cardToPlay.getCardType());
    }

    public ThrowAction createThrowAction(Game game, Player player) {
        List<Card> throwableCards = player.getNonTechnicalDebtCardsInHand();
        CardType cardType=throwableCards.get(game.getRandom().nextInt(throwableCards.size())).getCardType();
        return new ThrowAction(cardType);
    }

    public WaitAction createWaitAction(Game game, Player player) {
        return new WaitAction();
    }
}

