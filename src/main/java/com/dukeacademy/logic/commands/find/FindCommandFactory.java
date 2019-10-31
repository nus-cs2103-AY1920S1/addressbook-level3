package com.dukeacademy.logic.commands.find;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * The type Find command factory.
 */
public class FindCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;

    /**
     * Instantiates a new Attempt command factory.
     *
     * @param questionsLogic the questions logic
     */
    public FindCommandFactory(QuestionsLogic questionsLogic) {
        this.questionsLogic = questionsLogic;
    }

    @Override
    public String getCommandWord() {
        return "find";
    }

    @Override public Command getCommand(String commandArguments) {
        return new FindCommand(questionsLogic, commandArguments);
    }
}
