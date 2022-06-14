package com.codingame.game.action;

public class MoveAction extends Action {
    protected Integer zoneToMoveId;
    protected Integer zoneToTakeCardId;

    public MoveAction (int zoneToMoveId, int zoneToTakeCardId) {
        this.zoneToMoveId = zoneToMoveId;
        this.zoneToTakeCardId = zoneToTakeCardId;
    }

    @Override
    public boolean isMove() {
        return true;
    }

    public Integer getZoneToMoveId() {
        return zoneToMoveId;
    }
    public Integer getZoneToTakeCardId() { return zoneToTakeCardId; }
}
