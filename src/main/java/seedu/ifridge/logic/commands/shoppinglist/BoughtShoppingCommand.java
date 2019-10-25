package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;

import java.util.List;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * Edits the details of an existing shopping Item in the shopping list.
 */
public class BoughtShoppingCommand extends Command {

    public static final String COMMAND_WORD = "bought";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the ShoppingItem at the index as bought. "
            + "Also specifies the expiry date and amount of items bought. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EXPIRY_DATE + "EXPIRY_DATE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRY_DATE + "30.04.2019"
            + PREFIX_AMOUNT + "2";

    public static final String MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS = "Edited shopping item: %1$s";
    public static final String MESSAGE_NOT_PROPER = "At least one of the required fields (amount and expiry date) "
            + "are not provided.";

    private final Index index;
    private final Amount amount;
    private final ExpiryDate expiryDate;

    /**
     * @param index of the shopping item in the filtered shopping list to edit
     * @param amount of the item to be marked as bought
     * @param expiryDate of the item to be marked as bought
     */
    public BoughtShoppingCommand(Index index, Amount amount, ExpiryDate expiryDate) {
        requireNonNull(index);
        requireNonNull(amount);
        requireNonNull(expiryDate);

        this.index = index;
        this.amount = amount;
        this.expiryDate = expiryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ShoppingItem> lastShownList = model.getFilteredShoppingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
        }

        ShoppingItem shoppingItemToMarkAsBought = lastShownList.get(index.getZeroBased());
        ShoppingItem boughtShoppingItem = shoppingItemToMarkAsBought.setBought(true);

        GroceryItem boughtItem = shoppingItemToMarkAsBought.getBoughtItem(amount, expiryDate);

        model.setShoppingItem(shoppingItemToMarkAsBought, boughtShoppingItem);
        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        model.addBoughtItem(boughtItem);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS,
                boughtShoppingItem));
        commandResult.setShoppingListCommand();
        return commandResult;
    }

}
