package com.codingame.view;

import com.codingame.game.Player;

public class PlayerMove {
    int zoneFrom, zoneTo;
    Player playerModel;

    public PlayerMove(Player playerModel, int zoneFrom, int zoneTo) {
        //super();
        this.zoneFrom = zoneFrom;
        this.zoneTo = zoneTo;
        this.playerModel = playerModel;
    }

}
