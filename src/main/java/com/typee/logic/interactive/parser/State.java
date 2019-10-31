package com.typee.logic.interactive.parser;

public interface State {
    void processInput(String commandText);

    State transition();

    boolean isAcceptingState();
}
