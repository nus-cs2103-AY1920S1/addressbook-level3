package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.Command;

/**
 * Represents a question command.
 */
public abstract class QuizCommand extends Command {

    public static final String COMMAND_WORD = "quiz";
    protected static final String QUIZ_DOES_NOT_EXIST = "Quiz with ID '%1$s' does not exist, create quiz first. ";
    protected static final String QUIZ_ALREADY_EXISTS = "Quiz with ID '%1$s' already exists! ";
    protected static final String REPEATED_QUESTION = "That is a repeated question!";
    protected static final String NOT_ENOUGH_QUESTIONS_IN_STORAGE =
            "You do not have enough questions in the storage! Add more questions and try again.";
    protected static final String INVALID_QUESTION_INDEX = "You are deleting a question index which does not exist!";
    protected static final String HTML_EXISTS = "Failed to export because the file '%1$s'.html already exists.";
    protected static final String BLANK_QUIZ_ID = "Please provide an identifier for your quiz!";
    protected static final String BLANK_TYPE = "Please provide a question type!";
    protected static final String INVALID_TYPE = "That is an invalid question type! Try mcq/open/all.";
    protected static final String INVALID_QUESTION_NUMBERS =
            "Please provide correct question numbers, and not leave blanks!";
    protected static final String INVALID_NUM_QUESTIONS =
            "Please provide the correct number of questions, and not leave blanks!";
}
