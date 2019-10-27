package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.Command;

/**
 * Represents a question command.
 */
public abstract class QuizCommand extends Command {

    public static final String COMMAND_WORD = "quiz";
    public static final String QUIZ_DOES_NOT_EXIST = "Quiz with ID '%1$s' not exist, create quiz first. ";
}
