package seedu.address.logic.commands.merge;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Merges a user input and the duplicate object.
 */
public abstract class MergeCommand extends Command {

    /**
     * Creates an {@code MergeCommand}.
     */
    public MergeCommand() {
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract String[] getDifferences();

    public abstract void removeFirstDifferentField();

    public abstract String getNextMergeFieldType();

    public abstract boolean onlyOneMergeLeft();

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand); // instanceof handles nulls
    }
}
