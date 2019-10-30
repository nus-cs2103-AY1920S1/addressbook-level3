package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;

import java.util.ArrayList;
import java.util.List;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.UniqueTemplateItems;


/**
 * Adds items in the specified template not found in the grocery list.
 */
public class AddTemplateShoppingCommand extends Command {

    public static final String COMMAND_WORD = "addTemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds items from template to the shopping list. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Template added to shopping list: %1$s";
    public static final String MESSAGE_NOTHING_ADDED = "All items in template are already in stock!";

    private final ArrayList<ShoppingItem> itemsToAdd = new ArrayList<ShoppingItem>();
    private final Index templateIndex;

    /**
     * Creates an AddShoppingCommand to add the specified {@code ShoppingItem}
     */
    public AddTemplateShoppingCommand(Index templateIndex) {
        requireNonNull(templateIndex);
        this.templateIndex = templateIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        UniqueTemplateItems templateToAdd = getTemplateToAdd(model);
        for (int i = 0; i < templateToAdd.getSize(); i++) {
            ShoppingItem newItem = checkGroceryListPerItem(templateToAdd.get(i), model);
            if (newItem != null) {
                itemsToAdd.add(newItem);
            }
        }

        if (itemsToAdd.isEmpty()) {
            throw new CommandException(MESSAGE_NOTHING_ADDED);
        }

        updateShoppingList(model);

        model.sortShoppingItems();
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, templateToAdd.getName()));
        commandResult.setShoppingListCommand();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateShoppingCommand // instanceof handles nulls
                && templateIndex.equals(((AddTemplateShoppingCommand) other).templateIndex));
    }

    /**
     * Update all items into the shopping list. If an item with the same name already exists,
     * the items should be updated. Otherwise, a new item is inserted into the list.
     * @param model model to be edited
     */
    public void updateShoppingList(Model model) {
        requireNonNull(model);
        List<ShoppingItem> shoppingList = model.getFilteredShoppingList();

        for (int i = 0; i < itemsToAdd.size(); i++) {
            ShoppingItem toAdd = itemsToAdd.get(i);
            if (model.hasShoppingItem(toAdd)) {
                replacePreviousItem(model.getShoppingItem(toAdd), toAdd, model);
            } else {
                model.addShoppingItem(toAdd);
            }
        }
        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
    }

    /**
     * A new shopping item with the same name will be created where the amount is the sum of the amounts in toEdit
     * and toAdd.
     * @param itemToEdit original item in the shopping list
     * @param templateItem shopping item from template to be added with original amount
     */
    public void replacePreviousItem(ShoppingItem itemToEdit, ShoppingItem templateItem, Model model) {
        requireNonNull(itemToEdit);
        requireNonNull(templateItem);
        assert(itemToEdit.isSameName(templateItem));
        Name name = itemToEdit.getName();
        Amount reqAmt = templateItem.getAmount().increaseBy(itemToEdit.getAmount());

        ShoppingItem editedItem = new ShoppingItem(name, reqAmt);

        model.setShoppingItem(itemToEdit, editedItem);
    }

    /**
     * Retrieves the list of template items to be compared against the grocery list.
     * @param model to be modified
     * @return template to add
     * @throws CommandException
     */
    public UniqueTemplateItems getTemplateToAdd(Model model) throws CommandException {
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        // Check that the template index is valid
        if (templateIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }
        return lastShownList.get(templateIndex.getZeroBased());
    }

    /**
     * This method checks for all instances of the template item in the grocery list. If an item
     * with the same name exists but is not yet expired, it will be subtracted from the required amount
     * to be bought. If the total amount in the fridge exceeds or is equal to the required amount,
     * a null object will be returned.
     * @param toCheck the template item to be checked against the grocery list
     * @return ShoppingItem with the updated amount.
     */
    public ShoppingItem checkGroceryListPerItem(Food toCheck, Model model) {
        List<GroceryItem> groceryList = model.getFilteredGroceryItemList();
        Name itemName = toCheck.getName();
        Amount reqAmt = toCheck.getAmount();
        for (int i = 0; i < groceryList.size(); i++) {
            GroceryItem g = groceryList.get(i);
            if (g.isSameName(toCheck) && !g.hasExpired()) {
                reqAmt = reqAmt.reduceBy(g.getAmount());
            }
        }
        return createShoppingItem(itemName, reqAmt);
    }

    /**
     * Creates a new shopping item, returns null when the amount value is invalid i.e. <= 0
     * @param name the name of the item
     * @param amt the amount of the item
     * @return the shopping item
     */
    public ShoppingItem createShoppingItem(Name name, Amount amt) {
        requireNonNull(name);
        requireNonNull(amt);
        if (amt.getValue(amt) <= 0) {
            return null;
        }
        return new ShoppingItem(name, amt);
    }
}
