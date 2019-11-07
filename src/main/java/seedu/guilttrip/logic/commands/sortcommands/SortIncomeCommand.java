package seedu.guilttrip.logic.commands.sortcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_SEQUENCE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.SortSequence;
import seedu.guilttrip.model.entry.SortType;

/**
 * Sorts the income list according to sortType and sortSequence.
 */
public class SortIncomeCommand extends Command {

    public static final String COMMAND_WORD = "sortIncome";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Sorts the list of income in guiltTrip(). \n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_SEQUENCE + "SEQUENCE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "Time "
            + PREFIX_SEQUENCE + "Ascending ";

    public static final String MESSAGE_SUCCESS = "Sorted all incomes by %s";

    private final SortType type;
    private final SortSequence sequence;

    public SortIncomeCommand(SortType type, SortSequence sequence) {
        this.type = type;
        this.sequence = sequence;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortFilteredIncome(type, sequence);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_SUCCESS, type));
    }
}
