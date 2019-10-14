package seedu.address.testutil;

import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.logic.commands.question.QuestionDeleteCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.question.Question;

/**
 * A utility class for Questions.
 */
public class QuestionUtil {

    /**
     * Returns an add command string for adding the {@code Question}.
     */
    public static String getAddCommand(Question question) {
        return QuestionAddCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_QUESTION + question
            .getQuestion() + " " + CliSyntax.PREFIX_ANSWER + question.getAnswer() + " "
            + CliSyntax.PREFIX_TYPE
            + "open";
    }

    /**
     * Returns an edit command string for adding the {@code Question}.
     */
    public static String getEditCommand(Question question) {
        return QuestionEditCommand.COMMAND_WORD + " 1 " + CliSyntax.PREFIX_QUESTION + "Test Edit"
            + " " + CliSyntax.PREFIX_ANSWER + "Test Answer" + " " + CliSyntax.PREFIX_TYPE + "mcq";
    }

    /**
     * Returns a delete command string for adding the {@code Question}.
     */
    public static String getDeleteCommand(int index) {
        return QuestionDeleteCommand.COMMAND_WORD + " delete " + index;
    }
}
