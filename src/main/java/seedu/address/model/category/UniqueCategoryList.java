package seedu.address.model.category;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A list of categories that enforces uniqueness between its elements and does not allow nulls.
 * A category is considered unique by comparing using the value
 *
 * Supports a minimal set of list operations.
 *
 *
 */
public class UniqueCategoryList implements Iterable<Category> {
    private final ObservableList<Category> internalList = FXCollections.observableArrayList();
    private final ObservableList<Category> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final HashMap<Category, Integer> categoryMap = new HashMap<>();

    /**
     * Returns true if the list contains an equivalent category as the given argument.
     */

    public boolean contains(Category toCheck) {
        requireNonNull(toCheck);
        //use hashMap to check the existence of the category
        return categoryMap.containsKey(toCheck);
    }

    /**
     * Adds a category to the list
     * the category must not already exist in the list.
     */
    public void add(Category toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // increase the value by 1 if toAdd is already added to the list
            categoryMap.put(toAdd, categoryMap.get(toAdd) + 1);
        } else {
            //first occurrence of toAdd
            categoryMap.put(toAdd, 1);
            internalList.add(toAdd);
        }
    }

    /**
     * Adds a category to the list from a set
     * the category must not already exist in the list.
     */
    public void add(Set<Category> categorySet) {
        requireNonNull(categorySet);
        categorySet.forEach(category -> add(category));

    }

    /**
     * Removes the equivalent flashCard from the list.
     * The flashCard must exist in the list.
     */
    public void remove(Category toRemove) {
        requireNonNull(toRemove);
        if (canRemove(toRemove)) {
            categoryMap.remove(toRemove);
            internalList.remove(toRemove);
        } else {
            categoryMap.put(toRemove, categoryMap.get(toRemove) - 1);
        }
    }

    public void remove(Set<Category> categorySet) {
        categorySet.forEach(category -> remove(category));
    }

    /**
     * Return true if the {@code toRemove} is last occurrence
     */
    private boolean canRemove(Category toRemove) {
        return categoryMap.get(toRemove) == 1;
    }

    /**
     *  clear the entire list and map
     */
    public void clear() {
        categoryMap.clear();
        internalList.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Category> asUnmodifiableObservablelist() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueCategoryList
                        && internalList.equals(((UniqueCategoryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
