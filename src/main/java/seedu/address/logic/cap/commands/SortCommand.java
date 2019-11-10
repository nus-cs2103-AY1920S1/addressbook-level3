package seedu.address.logic.cap.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.model.cap.Model;

/**
 * Sort modules by the time the modules are taken.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sort the modules according to time the module was taken.";

    public static final String MESSAGE_SORT_SUCCESS = "List sorted by academic year and semester";

    public SortCommand() {
    }

    @Override
    public CommandResult execute(Model capModel) throws CommandException {
        requireNonNull(capModel);
        capModel.setSortedList();
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortCommand); // instanceof handles nulls
    }
}
