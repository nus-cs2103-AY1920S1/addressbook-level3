package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.STATS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;

/**
 * Gets statistics of how well the user has attempted the questions.
 */
public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a break down of the questions "
            + "by its results. You can get the statistics of a difficulty and multiple subjects.\n"
            + "Parameters: ["
            + PREFIX_DIFFICULTY + "DIFFICULTY] ["
            + PREFIX_SUBJECT + "SUBJECT1] "
            + "[" + PREFIX_SUBJECT + "SUBJECT2]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Here are the statistics: %s";
    public static final String MESSAGE_NO_STATISTICS = "There is no available data for computation of "
            + "statistics, try doing some questions.";

    private QuizResultFilter quizResultFilter;
    private String message;

    /**
     * Creates a GetStatisticsCommand to get the specified statistics
     * filtered by the {@code QuizResultFilter}.
     * @param quizResultFilter The filter to be applied to the quiz results.
     */
    public GetStatisticsCommand(QuizResultFilter quizResultFilter, String message) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.filterQuizResult(quizResultFilter);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_STATISTICS);
        }
        CommandResult c = new CommandResult(String.format(MESSAGE_SUCCESS, message), 8);
        c.setType(STATS);
        return c;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetStatisticsCommand // instanceof handles nulls
                && quizResultFilter.equals(((GetStatisticsCommand) other).quizResultFilter)); // state check
    }
}
