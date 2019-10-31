package com.typee.logic.interactive.parser;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.interactive.parser.state.State;

public class Parser implements InteractiveParser {

    private static String MESSAGE_CLEAR_ALL = "// clear";

    private boolean isActive;
    private State currentState;

    public Parser() {
        this.currentState = null;
        this.isActive = false;
    }

    @Override
    public void parseInput(String commandText) {
        if (commandText.equalsIgnoreCase(MESSAGE_CLEAR_ALL)) {
            resetParser();
            return;
        }

        if (isActive) {
            parseActive(commandText);
        } else {
            parseInactive(commandText);
        }
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

    private void resetParser() {
        this.isActive = false;
        this.currentState = null;
    }

}
