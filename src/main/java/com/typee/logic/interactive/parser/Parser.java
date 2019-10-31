package com.typee.logic.interactive.parser;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;

public class Parser implements InteractiveParser {

    private boolean isActive;
    private State currentState;

    public Parser() {
        this.currentState = null;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void parseCommand(String commandText) {

    }

    @Override
    public CommandResult fetchResult() {
        return null;
    }

    @Override
    public boolean hasParsedCommand() {
        return currentState.isEndState();
    }

    @Override
    public Command makeCommand() {
        return null;
    }


}
