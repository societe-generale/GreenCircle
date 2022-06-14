package com.codingame.game;

@SuppressWarnings("serial")
public class GameRuleException extends Exception {
    private final String errorMessage;
    private final String command;

    public GameRuleException(String command, String errorMessage) {
        super("Invalid Input: Got '" + command + "' but " + errorMessage);
        this.errorMessage = errorMessage;
        this.command = command;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCommand() { return command; }

}
