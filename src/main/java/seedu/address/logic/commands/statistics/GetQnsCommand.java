package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.QUESTIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;

/**
 * Gets all questions that have been answered correctly/incorrectly by subject.
 */
public class GetQnsCommand extends Command {
    public static final String COMMAND_WORD = "question";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets all questions that have been answered "
            + "correctly/incorrectly by using either -c/-i respectively.\n"
            + "Either one of -c or -i must be used, you can also include optional subject fields.\n"
            + "Parameters: "
            + "[-c] [-i] ["
            + PREFIX_SUBJECT + "SUBJECT1]...\n"
            + "Example: " + COMMAND_WORD + " -c "
            + PREFIX_SUBJECT + "CS2103T";

    public static final String MESSAGE_NO_QNS = "There are no correctly/incorrectly answered questions, "
            + "try doing some questions.";

    private QuizResultFilter quizResultFilter;
    private String message;

    /**
     * Creates a GetQnsCommand to get the specified questions filtered by the {@code QuizResultFilter}.
     * @param quizResultFilter The filter to be applied to the quiz results.
     */
    public GetQnsCommand(QuizResultFilter quizResultFilter, String message) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.filterQuizResult(quizResultFilter);
            model.updateQuizResultFilter(quizResultFilter);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_QNS);
        }
        CommandResult c = new CommandResult(message, 8);
        c.setType(QUESTIONS);
        return c;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetQnsCommand // instanceof handles nulls
                && quizResultFilter.equals(((GetQnsCommand) other).quizResultFilter)); // state check
    }
}
