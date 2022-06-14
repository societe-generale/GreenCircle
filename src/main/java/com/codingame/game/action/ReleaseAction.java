package com.codingame.game.action;

public class ReleaseAction extends Action {
    protected Integer applicationId;

    public ReleaseAction(int applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean isRelease() {
        return true;
    }

    public Integer getApplicationId() {
        return applicationId;
    }
}
