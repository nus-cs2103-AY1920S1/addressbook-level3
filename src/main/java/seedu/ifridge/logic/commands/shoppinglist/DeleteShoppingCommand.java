package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteShoppingCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the shopping item identified by the index number used in the displayed shopping list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SHOPPING_ITEM_SUCCESS = "Deleted ShoppingItem: %1$s";

    private final Index targetIndex;

    public DeleteShoppingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ShoppingItem> lastShownList = model.getFilteredShoppingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
        }

        ShoppingItem shoppingItemToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteShoppingItem(shoppingItemToDelete);
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_DELETE_SHOPPING_ITEM_SUCCESS, shoppingItemToDelete));
        commandResult.setShoppingListCommand();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteShoppingCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteShoppingCommand) other).targetIndex)); // state check
    }
}

