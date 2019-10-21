package seedu.address.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;

import java.util.HashSet;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.ShoppingItem;

/**
 * Lists all persons in the address book to the user.
 */
public class MergeShoppingCommand extends Command {

    public static final String COMMAND_WORD = "mergebought";

    public static final String MESSAGE_SUCCESS = "Merged all bought shopping items into grocery list.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<GroceryItem> lastShownBoughtList = model.getFilteredBoughtItemList();
        List<ShoppingItem> lastShownShoppingList = model.getFilteredShoppingList();

        for (int i = 0; i < lastShownBoughtList.size(); i++) {
            GroceryItem groceryItem = lastShownBoughtList.get(i);
            model.addGroceryItem(groceryItem);
        }

        for (int i = 0; i < lastShownShoppingList.size(); i++) {
            ShoppingItem shoppingItem = lastShownShoppingList.get(i);
            if (shoppingItem.isBought()) {
                model.deleteShoppingItem(shoppingItem);
            }
        }

        for (int i = 0; i < lastShownBoughtList.size(); i++) {
           model.setBoughtList(new AddressBook());
        }

        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_PERSONS);
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setShoppingListCommand();
        return commandResult;
    }
}
