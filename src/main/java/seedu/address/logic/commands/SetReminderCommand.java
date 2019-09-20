package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the reminder threshold for a particular item in the list.
 */

public class SetReminderCommand extends Command {

    public static final String COMMAND_WORD = "set reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the reminder threshold of the item "
            + "identified by the index number used in the last item listing. "
            + "Existing threshold will be overwritten by the input.\n"
            + "Parameters: <index>(must be a positive integer) <threshold>(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "|1|7";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "set reminder command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Index: %d, Threshold: %d";

    private final Index index;
    private final int threshold;

    /**
     * @param index Index of the item in the list.
     * @param threshold New threshold.
     */
    public SetReminderCommand(Index index, int threshold) {
        requireAllNonNull(index, threshold);

        this.index = index;
        this.threshold = threshold;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, this.index.getOneBased(), this.threshold));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SetReminderCommand)) {
            return false;
        } else {
            SetReminderCommand other = (SetReminderCommand) obj;
            return this.index.equals(other.index) && this.threshold == other.threshold;
        }
    }
}
