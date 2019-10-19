package seedu.address.logic.commands.statistics;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.note.NoteAddCommand;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.statistics.Statistics;

import static java.util.Objects.requireNonNull;

public class StatisticsAddCommand extends StatisticsCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates students statistics\n"
            + "Parameters:\n"
            + "method/{auto/manual}\n"
            + "file/{full file path}\n"
            + "Example: statistics method/auto file/C:\\Users\\MyUser\\Desktop\\SampleData.xlsx\n";

    public static final String MESSAGE_SUCCESS = "Statistics Generated";
    public static final String MESSAGE_DUPLICATE_STATISTICS = "This statistics already been generated";

    private final Statistics toAdd;
    /**
     * Creates a StatisticsAddCommand to add the specified {@code Statistics}
     *
     * @param stats to set.
     */
    public StatisticsAddCommand(Statistics stats) {
        requireNonNull(stats);
        toAdd = stats;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addStatistics(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsAddCommand // instanceof handles nulls
                && toAdd.equals(((StatisticsAddCommand) other).toAdd));
    }
}
