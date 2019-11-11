package seedu.guilttrip.logic.commands.statisticscommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.ArrayList;
import java.util.List;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Date;

/**
 * Obtains information about the statistics from model and displays the statistics in form of bar chart.
 */
public class ViewBarChartCommand extends Command {

    public static final String COMMAND_WORD = "viewBar";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": View Statistics of GuiltTrip in bar chart form "
            + "for the specific month.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERIOD + "9/2019";

    public static final String MESSAGE_SUCCESS = "Display all statistics by the specified period";
    public static final String MESSAGE_FAILURE = "There is an error with your specified range of months. Only one"
            + " month is allowed to be specified for ViewBar.";
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
