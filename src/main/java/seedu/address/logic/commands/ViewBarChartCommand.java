package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Date;
import seedu.address.model.statistics.StatisticsManager;

public class ViewBarChartCommand extends StatisticsCommand {

    public static final String COMMAND_WORD = "viewBar";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View Statistics of guiltTrip(). Use a comma to "
            + "seperate the two dates. \n"
            + "Parameters: "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERIOD + "9/2019 ";

    public static final String MESSAGE_SUCCESS = "Display all statistics by the specified period";
    public static final String MESSAGE_FAILURE = "There is an error with your specified range of months. Only two "
            + "specified dates Start and End are allowed";

//    public ViewBarChartCommand(List<Date> period) {
//        this.listOfPeriods = period;
//    }

    @Override
    public CommandResult execute(Model model, StatisticsManager stats, CommandHistory history) {
        requireNonNull(model);
        stats.updateLineCharts();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
