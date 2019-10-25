package seedu.ifridge.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
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
     * Creates an GroceryList using the Persons in the {@code toBeCopied}
     */
    public ShoppingList(ReadOnlyShoppingList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setShoppingItems(List<ShoppingItem> foods) {
        this.shoppingItems.setShoppingItems(foods);
    }

    /**
     * Resets the existing data of this {@code GroceryList} with {@code newData}.
     */
    public void resetData(ReadOnlyShoppingList newData) {
        requireNonNull(newData);

        setShoppingItems(newData.getShoppingList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasShoppingItem(ShoppingItem shoppingItem) {
        requireNonNull(shoppingItem);
        return shoppingItems.contains(shoppingItem);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addShoppingItem(ShoppingItem p) {
        shoppingItems.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setShoppingItem(ShoppingItem target, ShoppingItem editedShoppingItem) {
        requireNonNull(editedShoppingItem);

        shoppingItems.setShoppingItem(target, editedShoppingItem);
    }

    /**
     * Removes {@code key} from this {@code GroceryList}.
     * {@code key} must exist in the address book.
     */
    public void removeShoppingItem(ShoppingItem key) {
        shoppingItems.remove(key);
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
