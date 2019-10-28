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
 * Gets questions that have been answered correctly/incorrectly by subject.
 */
public class GetQnsCommand extends Command {
    public static final String COMMAND_WORD = "questions";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets all questions that have been answered "
            + "correctly/incorrectly for a particular subject by using either -c/-i respectively.\n"
            + "You can also see past answers to the questions by adding the -a field.\n"
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + "[-c] [-i] "
            + "[-a]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "CS2103T" + " -c -a";

    public static final String MESSAGE_SUCCESS = "Here are the questions: ";
    public static final String MESSAGE_NO_QNS = "There are no correctly/incorrectly answered questions, "
            + "try doing some questions.";

    private QuizResultFilter quizResultFilter;

    public GetQnsCommand(QuizResultFilter quizResultFilter) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.filterQuizResult(quizResultFilter);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_QNS);
        }
        CommandResult c = new CommandResult(MESSAGE_SUCCESS, 8);
        c.setType(QUESTIONS);
        return c;
    }
}
