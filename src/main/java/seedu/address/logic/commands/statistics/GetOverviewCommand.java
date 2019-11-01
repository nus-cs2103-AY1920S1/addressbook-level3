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
 * Gets an overview of the types of questions that have been attempted overall.
 */
public class GetOverviewCommand extends Command {
    public static final String COMMAND_WORD = "overview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets an overview of the types of "
            + "questions that have been attempted overall.\n"
            + "An optional time period can be included using dt/. The format of dt/ is dd/MM/yyyy.\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + "START_DATE "
            + PREFIX_DATE + "END_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "dt/08/09/2019 "
            + "dt/10/09/2019";

    public static final String MESSAGE_SUCCESS = "Here is an overview of the questions: %1$s";
    public static final String MESSAGE_NO_STATISTICS = "There are no questions done, try doing some questions.";

    private QuizResultFilter quizResultFilter;
    private String message;

    /**
     * Creates a GetOverviewCommand to get the specified quiz
     * results filtered by the {@code QuizResultFilter}.
     * @param quizResultFilter The filter to be applied to the quiz results.
     */
    public GetOverviewCommand(QuizResultFilter quizResultFilter, String message) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.updateQuizResultFilter(quizResultFilter);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_STATISTICS);
        }
        CommandResult c = new CommandResult(String.format(MESSAGE_SUCCESS, message), 8);
        c.setType(OVERVIEW);
        return c;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetOverviewCommand // instanceof handles nulls
                && quizResultFilter.equals(((GetOverviewCommand) other).quizResultFilter)); // state check
    }
}
