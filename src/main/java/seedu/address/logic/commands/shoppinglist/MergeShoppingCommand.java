package seedu.address.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;
import static seedu.address.model.food.Amount.getValue;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.food.Amount;
import seedu.address.model.food.ExpiryDate;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.Name;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class MergeShoppingCommand extends Command {

    public static final String COMMAND_WORD = "mergebought";

    public static final String MESSAGE_SUCCESS = "Merged all bought shopping items into grocery list.";

    private GroceryItem createUpdatedGroceryItem(GroceryItem boughtItem, GroceryItem groceryItem) {
        Name name = boughtItem.getName();
        ExpiryDate expiryDate = boughtItem.getExpiryDate();
        Amount updatedAmount = boughtItem.getAmount().increaseBy(groceryItem.getAmount());
        Set<Tag> tags = groceryItem.getTags();
        return new GroceryItem(name, updatedAmount, expiryDate, tags);
    }

    private void modifyShoppingListAccordingToBoughtItem(GroceryItem boughtItem, Model model) {
        Amount boughtAmount = boughtItem.getAmount();
        List<ShoppingItem> shoppingList = model.getFilteredShoppingList();
        for (int i = 0; i < shoppingList.size(); i++) {
            ShoppingItem shoppingItem = shoppingList.get(i);
            Amount shoppingAmount = shoppingItem.getAmount();
            if (!shoppingItem.getName().equals(boughtItem.getName())) {
                continue;
            }
            if (getValue(shoppingAmount) > getValue(boughtAmount)) {
                Amount updatedShoppingAmount = shoppingItem.getAmount().reduceBy(boughtAmount);
                ShoppingItem editShoppingItem = new ShoppingItem(shoppingItem.getName(), updatedShoppingAmount,
                        shoppingItem.isBought(), shoppingItem.isUrgent());
                model.setShoppingItem(shoppingItem, editShoppingItem);
            } else {
                model.deleteShoppingItem(shoppingItem);
                --i;
            }
        }
    }

    private boolean modifyGroceryListAccordingToBoughtItem(GroceryItem boughtItem, Model model) {
        boolean toAdd = true;
        List<GroceryItem> groceryList = model.getFilteredGroceryItemList();
        for (int j = 0; j < groceryList.size(); j++) {
            GroceryItem groceryItem = groceryList.get(j);
            if (boughtItem.isSameFood(groceryItem)) {
                GroceryItem updatedGroceryItem = createUpdatedGroceryItem(boughtItem, groceryItem);
                model.setGroceryItem(groceryItem, updatedGroceryItem);
                toAdd = false;
                break;
            }
        }
        if (toAdd) {
            model.addGroceryItem(boughtItem);
        }
        return toAdd;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<GroceryItem> lastShownBoughtList = model.getFilteredBoughtItemList();
        List<GroceryItem> groceryList = model.getFilteredGroceryItemList();
        for (int i = 0; i < lastShownBoughtList.size(); i++) {
            GroceryItem boughtItem = lastShownBoughtList.get(i);
            modifyGroceryListAccordingToBoughtItem(boughtItem, model);
            modifyShoppingListAccordingToBoughtItem(boughtItem, model);
        }

        model.setBoughtList(new AddressBook());

        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_PERSONS);
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setShoppingListCommand();
        return commandResult;
    }
}
