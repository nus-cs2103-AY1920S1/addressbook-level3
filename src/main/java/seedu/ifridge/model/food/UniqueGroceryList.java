package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.exceptions.DuplicateFoodException;
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
public class UniqueGroceryList implements Iterable<GroceryItem> {

    final ObservableList<GroceryItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<GroceryItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(GroceryItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFood);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(GroceryItem toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setGroceryItem(GroceryItem target, GroceryItem editedGroceryItem) {
        requireAllNonNull(target, editedGroceryItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        internalList.set(index, editedGroceryItem);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Food toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    public void setGroceryList(UniqueGroceryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setGroceryList(List<GroceryItem> foods) {
        requireAllNonNull(foods);
        if (!groceryItemsAreUnique(foods)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foods);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<GroceryItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<GroceryItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroceryList // instanceof handles nulls
                        && internalList.equals(((UniqueGroceryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean groceryItemsAreUnique(List<GroceryItem> groceryList) {
        for (int i = 0; i < groceryList.size() - 1; i++) {
            for (int j = i + 1; j < groceryList.size(); j++) {
                if (groceryList.get(i).isSameFood(groceryList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
