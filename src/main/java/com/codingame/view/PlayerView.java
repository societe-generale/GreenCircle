package com.codingame.view;

import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.entities.TextBasedEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerView {

    public String getColor() {
        return model.getIndex() == 0 ? "Blue" : "Orange";
    }

    private static class PlayerViewState {
        int playerIndex;

        public PlayerViewState(int playerIndex) {
            this.playerIndex = playerIndex;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            PlayerViewState other = (PlayerViewState) obj;
            if (playerIndex != other.playerIndex) return false;
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + playerIndex;
            return result;
        }
    }

    Group group;
    SpriteAnimation sprite;
    TextBasedEntity<?> message;

    SpriteAnimation badAction, goodAction;

    Group rotationWrapper;

    Player model;

    public PlayerView(Player model) {
        this.model = model;
    }

    public Player getPlayerModel() {
        return model;
    }

    static Map<PlayerViewState, String[]> stateToImages = new HashMap<>();
    static {
        for (int idx = 0; idx < 2; ++idx) {
            stateToImages.put(new PlayerViewState(idx), getPlayerImages(idx));
       }
    }

    private static String[] getPlayerImages(int playerIndex) {
        String color = playerIndex == 0 ? "Blue" : "Orange";

        List<String> playerImages = new ArrayList<String>();
        playerImages.add(color + "_Meeple.png");
        return playerImages.toArray(new String[0]);
    }

    public void setViewState(int playerIndex) {
        sprite.setImages(stateToImages.get(new PlayerViewState(playerIndex)));
    }
}
