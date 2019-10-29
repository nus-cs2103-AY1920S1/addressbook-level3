package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;

/**
 * Gets statistics of how well the user has attempted the questions.
 */
public class GetOverviewCommand extends Command {
    public static final String COMMAND_WORD = "overview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets an overview of the types of "
            + "questions that have been added into the app.\n"
            + "An optional time period can be included.\n"
            + "A stacked bar chart, sorted by subjects, will be returned.\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + "START_DATE] "
            + "[" + PREFIX_DATE + "END_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "dt/08/09/2019 "
            + "dt/10/09/2019";

    public static final String MESSAGE_SUCCESS = "Here is an overview of the questions: ";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "The format of date is incorrect.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "The format of the command is incorrect.";
    public static final String MESSAGE_NO_STATISTICS = "There are no questions done, try doing some questions.";

    private QuizResultFilter quizResultFilter;

    public GetOverviewCommand(QuizResultFilter quizResultFilter) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.updateQuizResultFilter(quizResultFilter);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_STATISTICS);
        }
        CommandResult c = new CommandResult(MESSAGE_SUCCESS, 8);
        c.setType(OVERVIEW);
        return c;
    }
}
