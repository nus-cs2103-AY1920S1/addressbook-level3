package seedu.deliverymans.model.restaurant.exceptions;

/**
 * Signals that the operation will result in duplicate Restaurants (Restaurants are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRestaurantException extends RuntimeException {
    public DuplicateRestaurantException() {
        super("Operation would result in duplicate persons");
    }
}
