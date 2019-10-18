package seedu.address.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.GroceryItem;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteGroceryCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the grocery item identified by the index number used in the displayed grocery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GROCERY_ITEM_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeleteGroceryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<GroceryItem> lastShownList = model.getFilteredGroceryItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROCERY_ITEM_DISPLAYED_INDEX);
        }

        GroceryItem groceryItemToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.addWasteItem(groceryItemToDelete);

        model.deleteGroceryItem(groceryItemToDelete);
        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_GROCERY_ITEM_SUCCESS,
                groceryItemToDelete));
        result.setWastelistCommand();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroceryCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteGroceryCommand) other).targetIndex)); // state check
    }
}
