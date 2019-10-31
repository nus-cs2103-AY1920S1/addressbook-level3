package com.dukeacademy.logic.commands.tab;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Factory class to generate Tab commands.
 */
public class TabCommandFactory implements CommandFactory {
    private final ApplicationState applicationState;

    public TabCommandFactory(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    public String getCommandWord() {
        return "tab";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.matches("\\s*")) {
            throw new InvalidCommandArgumentsException("Exit command does not take any arguments");
        }

        return new TabCommand(applicationState);
    }
}
