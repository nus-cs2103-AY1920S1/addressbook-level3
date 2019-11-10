package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.quiz.Statistics;

/**
 * Provides a summary of statistics for current quiz session or last done quiz.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows summary statistics of your latest "
            + "quiz performance.";

    private static final String MESSAGE_SUCCESS = "Summary statistics shown!";


    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);
        ObservableList<Statistics> history = model.getStatisticsList();
        if (history.isEmpty()) {
            return new CommandResultBuilder().withFeedBack("You have not attempted any quizzes yet!").build();
        }
        return new CommandResultBuilder().withFeedBack(String.format(MESSAGE_SUCCESS + "\nLatest quiz results shown: \n"
                + history.get(history.size() - 1))).withStats(true).build();
    }
}
