package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.STATS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizResultFilter;

/**
 * Gets statistics of how well the user has attempted the questions.
 */
public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the statistics of how well "
            + "a question has been answered for a subject. "
            + "A pie chart will be returned.\n"
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Here are the statistics: ";
    public static final String MESSAGE_NO_STATISTICS = "There is no available data for computation of "
            + "statistics, try doing some questions.";

    private QuizResultFilter quizResultFilter;

    public GetStatisticsCommand(QuizResultFilter quizResultFilter) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.filterQuizResult(quizResultFilter);
        CommandResult c = new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
        c.setType(STATS);
        return c;
    }
}
