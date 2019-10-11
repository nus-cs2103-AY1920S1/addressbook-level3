package seedu.deliverymans.model.database;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.restaurant.Restaurant;
import seedu.deliverymans.model.restaurant.UniqueRestaurantList;

/**
 * Wraps all data at the restaurant database level
 * Duplicates are not allowed (by .isSameRestaurant comparison)
 */
public class RestaurantDatabase {

    private final UniqueRestaurantList restaurants;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        restaurants = new UniqueRestaurantList();
    }

    public RestaurantDatabase() {}

    /**
     * Creates an RestaurantDatabase using the Restaurants in the {@code toBeCopied}
     */
    public RestaurantDatabase(RestaurantDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the restaurant list with {@code restaurants}.
     * {@code restaurants} must not contain duplicate restaurants.
     */
    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants.setRestaurants(restaurants);
    }

    /**
     * Resets the existing data of this {@code RestaurantDatabase} with {@code newData}.
     */
    public void resetData(RestaurantDatabase newData) {
        requireNonNull(newData);

        setRestaurants(newData.getRestaurantList());
    }

    //// restaurant-level operations

    /**
     * Returns true if a restaurant with the same identity as {@code restaurant} exists in the address book.
     */
    public boolean hasRestaurant(Restaurant restaurant) {
        requireNonNull(restaurant);
        return restaurants.contains(restaurant);
    }

    /**
     * Adds a restaurant to the address book.
     * The restaurant must not already exist in the address book.
     */
    public void addRestaurant(Restaurant p) {
        restaurants.add(p);
    }

    /**
     * Replaces the given restaurant {@code target} in the list with {@code editedRestaurant}.
     * {@code target} must exist in the address book.
     * The restaurant identity of {@code editedRestaurant} must not be the same as another existing restaurant in the address book.
     */
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireNonNull(editedRestaurant);

        restaurants.setRestaurant(target, editedRestaurant);
    }

    /**
     * Removes {@code key} from this {@code RestaurantDatabase}.
     * {@code key} must exist in the address book.
     */
    public void removeRestaurant(Restaurant key) {
        restaurants.remove(key);
    }

    //// util methods

    public ObservableList<Restaurant> getRestaurantList() {
        return restaurants.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return restaurants.asUnmodifiableObservableList().size() + " restaurants";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestaurantDatabase // instanceof handles nulls
                && restaurants.equals(((RestaurantDatabase) other).restaurants));
    }

    @Override
    public int hashCode() {
        return restaurants.hashCode();
    }
}
