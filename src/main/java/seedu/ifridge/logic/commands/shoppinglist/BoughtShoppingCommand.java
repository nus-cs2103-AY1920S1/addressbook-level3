package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;
import static seedu.ifridge.model.food.Amount.hasSameAmountUnitType;
import static seedu.ifridge.model.food.ShoppingItem.isCompletelyBought;

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
            + PREFIX_EXPIRY_DATE + "30/04/2019"
            + PREFIX_AMOUNT + "2units";

    public static final String MESSAGE_BOUGHT_SHOPPING_ITEM_SUCCESS = "Bought shopping item: %1$s";
    public static final String MESSAGE_NOT_PROPER = "At least one of the required fields (amount and expiry date) "
            + "are not provided.";
    public static final String MESSAGE_WRONG_UNIT_TYPE =
            "The amount you buy must have the same unit type that the amount of shopping item has.";

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

        if (Amount.isEmptyAmount(amount)) {
            throw new CommandException(Amount.MESSAGE_ZERO_AMOUNT);
        }
        ShoppingItem shoppingItemToMarkAsBought = lastShownList.get(index.getZeroBased());
        ShoppingItem boughtShoppingItem = shoppingItemToMarkAsBought.setBought(true);
        GroceryItem boughtItem = shoppingItemToMarkAsBought.getBoughtItem(amount, expiryDate);

        if (!hasSameAmountUnitType(amount, shoppingItemToMarkAsBought.getAmount())) {
            throw new CommandException(MESSAGE_WRONG_UNIT_TYPE);
        }

        model.addBoughtItem(boughtItem);
        if (isCompletelyBought(boughtShoppingItem, model.getBoughtList().getGroceryList())) {
            boughtShoppingItem = boughtShoppingItem.setUrgent(false);
        }
        model.setShoppingItem(shoppingItemToMarkAsBought, boughtShoppingItem);
        model.sortShoppingItems();
        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        model.commitShoppingList();
        model.commitBoughtList();

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_BOUGHT_SHOPPING_ITEM_SUCCESS,
                boughtShoppingItem));
        commandResult.setShoppingListCommand();
        return commandResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof BoughtShoppingCommand)) {
            return false;
        } else {
            return this.index.equals(((BoughtShoppingCommand) o).index)
                    && this.amount.equals(((BoughtShoppingCommand) o).amount)
                    && this.expiryDate.equals(((BoughtShoppingCommand) o).expiryDate);
        }
    }
}
