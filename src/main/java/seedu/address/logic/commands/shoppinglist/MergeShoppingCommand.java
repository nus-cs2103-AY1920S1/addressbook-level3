package seedu.address.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class MergeShoppingCommand extends Command {

    public static final String COMMAND_WORD = "slist merge bought";

    public static final String MESSAGE_SUCCESS = "Merged all bought shopping items into grocery list.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        /*List<ShoppingItem> lastShownList = model.getFilteredShoppingItemList();

        for (int i = 0; i < lastShownList.size(); i++) {
            ShoppingItem shoppingItem = lastShownList.get(i);
            if (shoppingItem.isBought()) {
                model.deleteShoppingItem(shoppingItem);
                GroceryItem groceryItem = new GroceryItem(shoppingItem.getName(),
                        shoppingItem.getAmount(),
                        shoppingItem.getExpiryDate(), new HashSet<>());
                model.addGroceryItem(groceryItem);
            }
        }
        model.updateFilteredShoppingItemList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_PERSONS);*/
        return new CommandResult(MESSAGE_SUCCESS);
    }
}