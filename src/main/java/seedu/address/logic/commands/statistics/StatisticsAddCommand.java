package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.statistics.Statistics;

/**
 * Generates a Statistics Report
 */
public class StatisticsAddCommand extends StatisticsCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates Statistics Report\n"
            + "Parameters:\n"
            + "file/{relative/full excel file path}\n"
            + "print/Class 6A.png\n"
            + "Note: the print field is optional\n"
            + "Example: statistics file/C:\\Users\\MyUser\\Desktop\\SampleData.xlsx\n";

    public static final String MESSAGE_SUCCESS = "Statistics Generated";

    private final Statistics toAdd;
    private final String printableName;

    /**
     * Creates a StatisticsAddCommand to add the specified {@code Statistics}
     *
     * @param stats to set.
     */
    public StatisticsAddCommand(Statistics stats, String printableName) {
        requireNonNull(stats);
        toAdd = stats;
        this.printableName = printableName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addStatistics(toAdd);
        CommandResultType statisticsCommandResult = CommandResultType.SHOW_STATISTIC;
        setPrintableAttributes(statisticsCommandResult); //delay printable task to later to utilize javafx Node.
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), statisticsCommandResult);
    }

    public void setPrintableAttributes(CommandResultType commandResultType) {
        if (!printableName.isEmpty()) {
            String fileWithExtension = printableName.endsWith(".png") ? printableName : printableName + ".png";
            commandResultType.setPrintable(true);
            commandResultType.setPrintableName(fileWithExtension);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StatisticsAddCommand // instanceof handles nulls
            && toAdd.equals(((StatisticsAddCommand) other).toAdd));
    }
}
