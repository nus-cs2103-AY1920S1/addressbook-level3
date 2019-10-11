package seedu.deliverymans.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.restaurant.exceptions.DuplicateRestaurantException;
import seedu.deliverymans.model.restaurant.exceptions.RestaurantNotFoundException;

/**
 * A list of restaurants that enforces uniqueness between its elements and does not allow nulls.
 * A restaurant is considered unique by comparing using {@code Restaurant#isSameRestaurant(Restaurant)}. As such,
 * adding and updating of restaurants uses Restaurant#isSameRestaurant(Restaurant) for equality so as to ensure
 * that the restaurant being added or updated is unique in terms of identity in the UniqueRestaurantList.
 * However, the removal of a person uses Restaurant#equals(Object) so as to ensure that the restaurant with exactly the
 * same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Restaurant#isSameRestaurant(Restaurant)
 */
public class UniqueRestaurantList implements Iterable<Restaurant> {

    private final ObservableList<Restaurant> internalList = FXCollections.observableArrayList();
    private final ObservableList<Restaurant> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent restaurant as the given argument.
     */
    public boolean contains(Restaurant toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRestaurant);
    }

    /**
     * Adds a restaurant to the list.
     * The restaurant must not already exist in the list.
     */
    public void add(Restaurant toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRestaurantException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the restaurant {@code target} in the list with {@code editedRestaurant}.
     * {@code target} must exist in the list.
     * The restaurant identity of {@code editedRestaurant} must not be the same as another existing restaurant in the list.
     */
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RestaurantNotFoundException();
        }

        if (!target.isSameRestaurant(editedRestaurant) && contains(editedRestaurant)) {
            throw new DuplicateRestaurantException();
        }

        internalList.set(index, editedRestaurant);
    }

    /**
     * Removes the equivalent restaurant from the list.
     * The restaurant must exist in the list.
     */
    public void remove(Restaurant toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RestaurantNotFoundException();
        }
    }

    public void setRestaurants(UniqueRestaurantList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code restaurants}.
     * {@code restaurants} must not contain duplicate restaurants.
     */
    public void setRestaurants(List<Restaurant> restaurants) {
        requireAllNonNull(restaurants);
        if (!restaurantsAreUnique(restaurants)) {
            throw new DuplicateRestaurantException();
        }

        internalList.setAll(restaurants);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Restaurant> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Restaurant> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRestaurantList // instanceof handles nulls
                && internalList.equals(((UniqueRestaurantList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code restaurants} contains only unique restaurants.
     */
    private boolean restaurantsAreUnique(List<Restaurant> restaurants) {
        for (int i = 0; i < restaurants.size() - 1; i++) {
            for (int j = i + 1; j < restaurants.size(); j++) {
                if (restaurants.get(i).isSameRestaurant(restaurants.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
