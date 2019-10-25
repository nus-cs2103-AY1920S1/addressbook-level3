package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.exceptions.DuplicateShoppingItemException;
import seedu.ifridge.model.food.exceptions.FoodNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Food#isSameFood(Food)
 */
public class UniqueShoppingList implements Iterable<ShoppingItem> {

    private final ObservableList<ShoppingItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<ShoppingItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ShoppingItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFood);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(ShoppingItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateShoppingItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setShoppingItem(ShoppingItem target, ShoppingItem editedShoppingItem) {
        requireAllNonNull(target, editedShoppingItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.isSameFood(editedShoppingItem) && contains(editedShoppingItem)) {
            throw new DuplicateShoppingItemException();
        }

        internalList.set(index, editedShoppingItem);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(ShoppingItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    public void setShoppingItems(UniqueShoppingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        requireAllNonNull(shoppingItems);
        if (!shoppingItemsAreUnique(shoppingItems)) {
            throw new DuplicateShoppingItemException();
        }

        internalList.setAll(shoppingItems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ShoppingItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ShoppingItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueShoppingList // instanceof handles nulls
                && internalList.equals(((UniqueShoppingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean shoppingItemsAreUnique(List<ShoppingItem> shoppingItems) {
        for (int i = 0; i < shoppingItems.size() - 1; i++) {
            for (int j = i + 1; j < shoppingItems.size(); j++) {
                if (shoppingItems.get(i).isSameFood(shoppingItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
