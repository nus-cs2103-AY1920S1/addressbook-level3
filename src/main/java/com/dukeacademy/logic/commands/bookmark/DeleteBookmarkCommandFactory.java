package com.dukeacademy.logic.commands.bookmark;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Factory class to represent all the necessary components for creating an
 * DeleteBookmarkCommand instance.
 */
public class DeleteBookmarkCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;

    /**
     * Instantiates a new DeleteBookmark command factory.
     *
     * @param questionsLogic    the questions logic
     */
    public DeleteBookmarkCommandFactory(QuestionsLogic questionsLogic) {
        this.questionsLogic = questionsLogic;
    }

    @Override
    public String getCommandWord() {
        return "deletebookmark";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new DeleteBookmarkCommand(index, questionsLogic);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Invalid input. Please call the deletebookmark command "
                    + "in this format: <deletebookmark [id]> , where id is the positive integer beside the"
                    + " question title.");
        }
    }
}
