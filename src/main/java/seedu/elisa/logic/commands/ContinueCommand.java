package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.commons.core.index.Index;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.exceptions.IllegalListException;
import seedu.elisa.model.item.VisualizeList;

/**
 * Mark a task as not done using it's index in ELISA.
 */
public class ContinueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cont";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the item identified by the index number used in the displayed item list as not done.\n"
            + "Can only be used on task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_ITEM_SUCCESS = "Oops... More work I guess. \nItem: %1$s";

    private final Index targetIndex;
    private Item oldItem;
    private Item itemNotDone;

    public ContinueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        VisualizeList lastShownList = model.getVisualList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        try {
            oldItem = model.getItem(targetIndex.getZeroBased());
            itemNotDone = model.markComplete(targetIndex.getZeroBased(), false);
            if (!isExecuted()) {
                model.getElisaCommandHistory().clearRedo();
                setExecuted(true);
            }
            return new CommandResult(String.format(MESSAGE_COMPLETE_ITEM_SUCCESS, itemNotDone));
        } catch (IllegalListException e) {
            throw new CommandException("Continue can only be done on the task list.");
        }
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.replaceItem(itemNotDone, oldItem);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContinueCommand // instanceof handles nulls
                && targetIndex.equals(((ContinueCommand) other).targetIndex)); // state check
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}

