package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens up a window with the commands available for financial tracker.
 */
public class SortFinCommand extends Command<Model> {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " time, date, amount, type or default";

    public static final String MESSAGE_SUCCESS = "Expense List sorted!";
    private final String comparingType;

    /**
     * Creates an SortFinCommand to sort an expense list.
     */
    public SortFinCommand(String comparingType) {
        requireNonNull(comparingType);
        this.comparingType = comparingType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setComparator(comparingType);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortFinCommand // instanceof handles nulls
                && comparingType.equals(((SortFinCommand) other).comparingType));
    }
}

