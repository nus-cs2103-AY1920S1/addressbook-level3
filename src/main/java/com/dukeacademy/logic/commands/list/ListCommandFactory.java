package com.dukeacademy.logic.commands.list;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Factory class to represent all the necessary components for creating an ListCommand instance.
 */
public class ListCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;

    /**
     * Instantiates a new Attempt command factory.
     *
     * @param questionsLogic the questions logic
     */
    public ListCommandFactory(QuestionsLogic questionsLogic) {
        this.questionsLogic = questionsLogic;
    }

    @Override
    public String getCommandWord() {
        return "showall";
    }

    @Override public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.matches("\\s*")) {
            throw new InvalidCommandArgumentsException("Showall command does "
                + "not take any arguments");
        }
        return new ListCommand(questionsLogic);
    }


}

