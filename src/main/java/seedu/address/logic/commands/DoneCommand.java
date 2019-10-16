package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.item.Item;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;
import seedu.address.model.exceptions.IllegalListException;
import seedu.address.model.item.VisualizeList;

/**
 * Mark a task as done using it's index in ELISA.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the item identified by the index number used in the displayed item list as done.\n"
            + "Can only be used on task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_ITEM_SUCCESS = "Completed Item: %1$s";

    private final Index targetIndex;

    public DoneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        VisualizeList lastShownList = model.getVisualList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        try {
            Item itemDone = model.done(targetIndex.getZeroBased());
            return new CommandResult(String.format(MESSAGE_COMPLETE_ITEM_SUCCESS, itemDone));
        } catch (IllegalListException e) {
            throw new CommandException("Done can only be done on the task list.");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && targetIndex.equals(((DoneCommand) other).targetIndex)); // state check
    }
}

