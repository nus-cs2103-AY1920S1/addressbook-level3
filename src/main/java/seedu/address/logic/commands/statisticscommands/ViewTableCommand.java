package seedu.address.logic.commands.statisticscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entry.Date;

/**
 * Obtains information about the statistics from model and displays the statistics in form of table.
 */
public class ViewTableCommand extends Command {

    public static final String COMMAND_WORD = "viewTable";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View Statistics of guiltTrip() in table form. Use a "
            + "comma to separate the two dates. \n"
            + "Parameters: "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERIOD + "9/2019, 10/2019 ";

    public static final String MESSAGE_SUCCESS = "Display all statistics by the specified period";
    public static final String MESSAGE_FAILURE = "There is an error with your specified range of months. Only two "
            + "specified dates Start and End are allowed";
    private List<Date> listOfPeriods;


    public ViewTableCommand(List<Date> period) {
        this.listOfPeriods = period;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (listOfPeriods == null) {
            model.updateListOfStats();
        } else {
            model.updateListOfStats(listOfPeriods);
        }
        return new CommandResult(MESSAGE_SUCCESS, new ArrayList<Boolean>(List.of(true, false, false, false)));
    }
}
