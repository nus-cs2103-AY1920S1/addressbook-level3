package com.dukeacademy.logic.commands.view;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.State.ApplicationState;

/**
 * Factory class to represent all the necessary components for creating an
 * ViewCommand instance.
 */
public class ViewCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new View command factory.
     *
     * @param questionsLogic        the questions logic
     */
    public ViewCommandFactory(QuestionsLogic questionsLogic, ApplicationState applicationState) {
        this.questionsLogic = questionsLogic;
        this.applicationState = applicationState;
    }

    @Override
    public String getCommandWord() {
        return "view";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new ViewCommand(index, questionsLogic, applicationState);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Invalid index entered.");
        }
    }
}
