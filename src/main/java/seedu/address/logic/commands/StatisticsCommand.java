package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Date;

/**
 * Obtains information about the statistics from Model.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "viewStatistics";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View Statistics of guiltTrip(). Use a comma to "
            + "seperate the two dates. \n"
            + "Parameters: "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERIOD + "9/2019 ";

    public static final String MESSAGE_SUCCESS = "Display all statistics by the specified period";
    public static final String MESSAGE_FAILURE = "There is an error with your specified range of months. Only two "
            + "specified dates Start and End are allowed";
    private List<Date> listOfPeriods;


    public StatisticsCommand(List<Date> period) {
        this.listOfPeriods = period;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (listOfPeriods == null) {
            model.getStats().updateListOfStats();
        } else {
            model.getStats().updateListOfStats(listOfPeriods);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
