package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Objects;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.expense.Timestamp;
import seedu.address.ui.panel.PanelName;

/**
 * Calculates statistics for Moolah
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_SUCCESS = "Statistics Calculated!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates statistics between the Start Date and End Date "
            + "Parameters: "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "11-11-1111 "
            + PREFIX_END_DATE + "12-12-1212 ";

    private final Timestamp startDate;
    private final Timestamp endDate;

    /**
     * Creates an StatsCommand to calculate statistics between 2 dates {@code Timestamp}
     */
    public StatsCommand(Timestamp startDate, Timestamp endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    protected void validate(Model model) {
        Objects.requireNonNull(model);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String statsResult = model.calculateStatistics(COMMAND_WORD, startDate, endDate, null);
        //modifies model to store the statistic in its field

        return new CommandResult(statsResult, false, false, true, false, PanelName.CURRENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsCommand // instance of handles nulls
                && startDate.equals(((StatsCommand) other).startDate)
                && endDate.equals(((StatsCommand) other).endDate));
    }
}


