package com.codingame.game.action;

public abstract class Action {

    public static final Action NO_ACTION = new Action() {
    };

    public boolean isMove() {
        return false;
    }
    public boolean isGive() { return false; }
    public boolean isPlay() { return false; }
    public boolean isThrow() { return false; }
    public boolean isRelease() { return false; }
    public boolean isWait() {
        return false;
    }
}
