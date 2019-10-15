package seedu.address.logic.commands.merge;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Rejects an update of a data field of a duplicate entity in the Addressbook.
 */
public abstract class MergeRejectedCommand extends Command {

    public static final String COMMAND_WORD = "no";


    /**
     * Creates an {@code MergeRejectedCommand}.
     */
    public MergeRejectedCommand() {
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean isLastMerge();

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeRejectedCommand); // instanceof handles nulls
    }
}
