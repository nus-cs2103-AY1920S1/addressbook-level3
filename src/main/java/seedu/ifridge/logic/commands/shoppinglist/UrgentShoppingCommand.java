package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.food.ShoppingItem.isCompletelyBought;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class UrgentShoppingCommand extends Command {

    public static final String COMMAND_WORD = "urgent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the shopping item identified by the index used in the displayed shopping list as urgent.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_URGENT_SHOPPING_ITEM_SUCCESS = "ShoppingItem marked as urgent is: %1$s";

    public static final String MESSAGE_URGENT_SHOPPING_ITEM_FAILURE =
            "Shopping item cannot me marked as urgent since it is already completely bought.";

    private final Index targetIndex;

    public UrgentShoppingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ShoppingItem> lastShownList = model.getFilteredShoppingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
        }

        ShoppingItem shoppingItemToMarkAsUrgent = lastShownList.get(targetIndex.getZeroBased());
        ObservableList<GroceryItem> internalBoughtList = model.getBoughtList().getGroceryList();
        if (shoppingItemToMarkAsUrgent.isBought()) {
            if (isCompletelyBought(shoppingItemToMarkAsUrgent, internalBoughtList)) {
                CommandResult commandResult =
                        new CommandResult(String.format(MESSAGE_URGENT_SHOPPING_ITEM_FAILURE));
                return commandResult;
            }
        }
        model.urgentShoppingItem(shoppingItemToMarkAsUrgent);
        ShoppingItem shoppingItemToPrint = shoppingItemToMarkAsUrgent.setUrgent(true);
        model.sortShoppingItems();
        model.commitShoppingList();
        model.commitBoughtList();
        CommandResult commandResult =
                new CommandResult(String.format(MESSAGE_URGENT_SHOPPING_ITEM_SUCCESS, shoppingItemToPrint));
        commandResult.setShoppingListCommand();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UrgentShoppingCommand // instanceof handles nulls
                && targetIndex.equals(((UrgentShoppingCommand) other).targetIndex)); // state check
    }

}
