package seedu.ifridge.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.UniqueShoppingList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ShoppingList implements ReadOnlyShoppingList {

    private final UniqueShoppingList shoppingItems;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        shoppingItems = new UniqueShoppingList();
    }

    public ShoppingList() {}

    /**
     * Creates an ShoppingList using the ShoppingItems in the {@code toBeCopied}
     */
    public ShoppingList(ReadOnlyShoppingList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the shopping list with {@code foods}.
     * {@code foods} must not contain duplicate ShoppingItems.
     */
    public void setShoppingItems(List<ShoppingItem> foods) {
        this.shoppingItems.setShoppingItems(foods);
    }

    /**
     * Resets the existing data of this {@code ShoppingList} with {@code newData}.
     */
    public void resetData(ReadOnlyShoppingList newData) {
        requireNonNull(newData);

        setShoppingItems(newData.getShoppingList());
    }

    //// shopping item-level operations

    /**
     * Returns true if a ShoppingItem with the same identity as {@code shoppingItem} exists in the shopping list.
     */
    public boolean hasShoppingItem(ShoppingItem shoppingItem) {
        requireNonNull(shoppingItem);
        return shoppingItems.contains(shoppingItem);
    }

    /**
     * Gets the shopping item from the shopping list.
     */
    public ShoppingItem getShoppingItem(ShoppingItem shoppingItem) {
        requireNonNull(shoppingItem);
        return shoppingItems.get(shoppingItem);
    }

    /**
     * Adds a shopping item to the shopping list.
     * The shopping item must not already exist in the shopping list.
     */
    public void addShoppingItem(ShoppingItem p) {
        shoppingItems.add(p);
    }

    /**
     * Marks the given shopping item in shopping list as urgent.
     * @param toMarkAsUrgent
     */
    public void urgentShoppingItem(ShoppingItem toMarkAsUrgent) {
        shoppingItems.markAsUrgent(toMarkAsUrgent);
    }

    /**
     * Replaces the given shopping item {@code target} in the list with {@code editedShoppingItem}.
     * {@code target} must exist in the shopping list.
     * The shopping item identity of {@code editedShoppingItem} must not be the same as another
     * existing shopping item in the shopping list.
     */
    public void setShoppingItem(ShoppingItem target, ShoppingItem editedShoppingItem) {
        requireNonNull(editedShoppingItem);

        shoppingItems.setShoppingItem(target, editedShoppingItem);
    }

    /**
     * Removes {@code key} from this {@code ShoppingList}.
     * {@code key} must exist in the shopping list.
     */
    public void removeShoppingItem(ShoppingItem key) {
        shoppingItems.remove(key);
    }

    public boolean containsShoppingItemWithName(Food foodItem) {
        return shoppingItems.contains(foodItem);
    }

    //// util methods

    @Override
    public String toString() {
        return shoppingItems.asUnmodifiableObservableList().size() + " shoppingItems";
        // TODO: refine later
    }

    @Override
    public ObservableList<ShoppingItem> getShoppingList() {
        return shoppingItems.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShoppingList // instanceof handles nulls
                && shoppingItems.equals(((ShoppingList) other).shoppingItems));
    }

    @Override
    public int hashCode() {
        return shoppingItems.hashCode();
    }

}
