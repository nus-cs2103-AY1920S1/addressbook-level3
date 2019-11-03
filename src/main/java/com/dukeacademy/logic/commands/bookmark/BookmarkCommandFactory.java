package com.dukeacademy.logic.commands.bookmark;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Factory class to represent all the necessary components for creating an
 * BookmarkCommand instance.
 */
public class BookmarkCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;

    /**
     * Instantiates a new Bookmark command factory.
     *
     * @param questionsLogic    the questions logic
     */
    public BookmarkCommandFactory(QuestionsLogic questionsLogic) {
        this.questionsLogic = questionsLogic;
    }

    @Override
    public String getCommandWord() {
        return "bookmark";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new BookmarkCommand(index, questionsLogic);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Invalid input. Please call the bookmark command "
                    + "in this format: <bookmark [id]> , where id is the positive integer beside the question title.");
        }
    }
}
