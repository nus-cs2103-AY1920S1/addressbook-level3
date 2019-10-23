package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Date;

/**
 * Obtains information about the statistics from Model.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "ViewHistory";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Looks up your entries history in guiltTrip(). \n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "Expense "
            + PREFIX_PERIOD + "11/9/2019, 21/9/2019 ";

    public static final String MESSAGE_SUCCESS = "Display all statistics by %s";
    private String type;
    private ArrayList<Date> listOfPeriods;

    public StatisticsCommand(String type) {
        this.type = type;
        this.listOfPeriods = new ArrayList<Date>();
    }

    public StatisticsCommand(String type, ArrayList<Date> period) {
        this.type = type;
        this.listOfPeriods = period;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        //TODO Call Model's Statistics Method
        return new CommandResult(String.format(MESSAGE_SUCCESS, type));
    }
}
