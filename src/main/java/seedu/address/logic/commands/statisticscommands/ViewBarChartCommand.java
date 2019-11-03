package seedu.address.logic.commands.statisticscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.Date;

/**
 * Obtains information about the statistics from model and displays the statistics in form of bar chart.
 */
public class ViewBarChartCommand extends Command {

    public static final String COMMAND_WORD = "viewBar";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View Statistics of guiltTrip() in bar chart form "
            + "for the specific month."
            + "Parameters: "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERIOD + "9/2019";

    public static final String MESSAGE_SUCCESS = "Display all statistics by the specified period";
    public static final String MESSAGE_FAILURE = "There is an error with your specified range of months. Only two "
            + "specified dates Start and End are allowed";
    private Date monthToCalculate;

    public ViewBarChartCommand(Date period) {
        this.monthToCalculate = period;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (monthToCalculate == null) {
            model.updateBarCharts();
        } else {
            model.updateBarCharts(monthToCalculate);
        }
        return new CommandResult(MESSAGE_SUCCESS, new ArrayList<Boolean>(List.of(false, false, true, false)));
    }
}
