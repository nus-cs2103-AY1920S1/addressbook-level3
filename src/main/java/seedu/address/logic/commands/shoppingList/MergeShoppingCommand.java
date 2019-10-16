package seedu.address.logic.commands.shoppingList;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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