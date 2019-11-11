package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_GROCERY_ITEMS;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;
import static seedu.ifridge.model.food.Amount.MESSAGE_INVALID_RESULTANT_AMOUNT;
import static seedu.ifridge.model.food.Amount.getValue;

import java.util.List;
import java.util.Set;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.exceptions.InvalidAmountException;
import seedu.ifridge.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class MergeShoppingCommand extends Command {

    public static final String COMMAND_WORD = "mergebought";

    public static final String MESSAGE_SUCCESS = "Merged all bought shopping items into grocery list.";

    /**
     * Creates an updated grocery item with the sum of its amount and boughtItem's quantity as quantity
     * @param boughtItem with same name as the groceryItem
     * @param groceryItem whose quantity is to be updated
     * @return grocery item with updated amount
     */
    private GroceryItem createUpdatedGroceryItem(GroceryItem boughtItem, GroceryItem groceryItem) {
        Name name = boughtItem.getName();
        ExpiryDate expiryDate = boughtItem.getExpiryDate();
        Amount updatedAmount = groceryItem.getAmount().increaseBy(boughtItem.getAmount());
        Set<Tag> tags = groceryItem.getTags();
        return new GroceryItem(name, updatedAmount, expiryDate, tags);
    }

    /**
     * Deletes or reduces the amount of shopping item according to how much was bought
     * @param boughtItem according to which shoppingList will be modified
     * @param model to be changed
     */
    private void modifyShoppingListAccordingToBoughtItem(GroceryItem boughtItem, Model model) {
        Amount boughtAmount = boughtItem.getAmount();
        List<ShoppingItem> shoppingList = model.getFilteredShoppingList();
        for (int i = 0; i < shoppingList.size(); i++) {
            ShoppingItem shoppingItem = shoppingList.get(i);
            Amount shoppingAmount = shoppingItem.getAmount();
            if (!shoppingItem.getName().equals(boughtItem.getName())) {
                continue;
            }
            try {
                Amount updatedShoppingAmount = shoppingAmount.reduceBy(boughtAmount);
                if (getValue(updatedShoppingAmount) == 0) {
                    model.deleteShoppingItem(shoppingItem);
                    --i;
                    return;
                }
                ShoppingItem editedShoppingItem = new ShoppingItem(shoppingItem.getName(), updatedShoppingAmount,
                        false, shoppingItem.isUrgent()); //once amount reduced, it is not bought anymore
                model.setShoppingItem(shoppingItem, editedShoppingItem);
            } catch (InvalidAmountException e) {
                if (e.getMessage().equals(MESSAGE_INVALID_RESULTANT_AMOUNT)) {
                    model.deleteShoppingItem(shoppingItem); //when boughtAmount exceeds shoppingAmount
                    --i;
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * Adds a new Grocery item or increases the amount of existing grocery item.
     * This is done according to the item that is bought.
     * @param boughtItem according to which groceryList will change
     * @param model to be modified
     */
    private void modifyGroceryListAccordingToBoughtItem(GroceryItem boughtItem, Model model) {
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
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<GroceryItem> lastShownBoughtList = model.getFilteredBoughtItemList();
        for (int i = 0; i < lastShownBoughtList.size(); i++) {
            GroceryItem boughtItem = lastShownBoughtList.get(i);
            modifyGroceryListAccordingToBoughtItem(boughtItem, model);
            modifyShoppingListAccordingToBoughtItem(boughtItem, model);
        }

        model.setBoughtList(new GroceryList());

        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);
        model.sortShoppingItems();
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setShoppingListCommand();
        return commandResult;
    }
}
