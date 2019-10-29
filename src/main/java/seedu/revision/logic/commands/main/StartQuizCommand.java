package seedu.revision.logic.commands.main;

import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Command to start the quiz session.
 */

public class StartQuizCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts quiz based on Mode, if no Mode provided, "
        + "entire question bank will be initialised.\n"
            + "Parameters: "
            + PREFIX_MODE + "MODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODE + "normal";

    private static final String MESSAGE_SUCCESS = "Starting Quiz!";

    private Predicate<Answerable> predicate;

    /**
     * Creates an AddCommand to add the specified {@code Answerable}
     */
    public StartQuizCommand(Predicate<Answerable> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws ParseException {

        ListCommand quizList = new ListCommand(predicate);
        quizList.execute(model);

        return new CommandResult(MESSAGE_SUCCESS, true);

    }
}
