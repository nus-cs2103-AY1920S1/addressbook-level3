package seedu.address.model.groceryitem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.groceryitem.exceptions.DuplicateFoodException;
import seedu.address.model.groceryitem.exceptions.FoodNotFoundException;

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
public class UniqueFoodList implements Iterable<GroceryItem> {

    private final ObservableList<GroceryItem> internalList = FXCollections.observableArrayList();
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
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(GroceryItem target, GroceryItem editedFood) {
        requireAllNonNull(target, editedFood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.isSameFood(editedFood) && contains(editedFood)) {
            throw new DuplicateFoodException();
        }

        internalList.set(index, editedFood);
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

    public void setPersons(UniqueFoodList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<GroceryItem> foods) {
        requireAllNonNull(foods);
        if (!personsAreUnique(foods)) {
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
                || (other instanceof UniqueFoodList // instanceof handles nulls
                        && internalList.equals(((UniqueFoodList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<GroceryItem> foods) {
        for (int i = 0; i < foods.size() - 1; i++) {
            for (int j = i + 1; j < foods.size(); j++) {
                if (foods.get(i).isSameFood(foods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
