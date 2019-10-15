package seedu.address.logic.commands.merge;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Updates a data field of a duplicate entity in the Addressbook.
 */
public abstract class MergeConfirmedCommand extends Command {

    public static final String COMMAND_WORD = "yes";
    public static final String DEFAULT_COMMAND_WORD = "";


    /**
     * Creates an {@code MergeConfirmedCommand} to update the original object.
     */
    public MergeConfirmedCommand() {
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean isLastMerge();

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeConfirmedCommand); // instanceof handles nulls
    }
}
