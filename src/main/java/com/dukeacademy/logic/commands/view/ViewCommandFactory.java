package com.dukeacademy.logic.commands.view;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.problemstatement.ProblemStatementLogic;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Factory class to represent all the necessary components for creating an
 * ViewCommand instance.
 */
public class ViewCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;
    private ProblemStatementLogic problemStatementLogic;

    /**
     * Instantiates a new View command factory.
     *
     * @param questionsLogic        the questions logic
     * @param problemStatementLogic the problem statement logic
     */
    public ViewCommandFactory(QuestionsLogic questionsLogic,
                              ProblemStatementLogic problemStatementLogic) {
        this.questionsLogic = questionsLogic;
        this.problemStatementLogic = problemStatementLogic;
    }

    @Override
    public String getCommandWord() {
        return "view";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new ViewCommand(index, questionsLogic, problemStatementLogic);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Invalid index entered.");
        }
    }
}
