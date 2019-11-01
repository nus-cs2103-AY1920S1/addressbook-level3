package seedu.deliverymans.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.food.exceptions.DuplicateFoodException;
import seedu.deliverymans.model.food.exceptions.FoodNotFoundException;
import seedu.deliverymans.model.location.Location;
import seedu.deliverymans.model.order.Order;

/**
 * Represents a Restaurant
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Restaurant {
    // Identity fields
    private final Name name;
    private final Location location;
    private final Rating rating;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final ObservableList<Food> menu = FXCollections.observableArrayList();
    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    /**
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Location location, Set<Tag> tags) {
        requireAllNonNull(name, location, tags);
        this.name = name;
        this.location = location;
        this.rating = new Rating("0", 0);
        this.tags.addAll(tags);
    }

    public Restaurant(Name name, Location location, Set<Tag> tags, ObservableList<Food> menu) {
        requireAllNonNull(name, location, tags, menu);
        this.name = name;
        this.location = location;
        this.rating = new Rating("0", 0);
        this.tags.addAll(tags);
        this.menu.addAll(menu);
    }

    public Restaurant(Name name, Location location, Rating rating, Set<Tag> tags, ObservableList<Food> menu,
                      ObservableList<Order> orders) {
        requireAllNonNull(name, location, rating, tags);
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.tags.addAll(tags);
        this.menu.addAll(menu);
        this.orders.addAll(orders);
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Rating getRating() {
        return rating;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    public ObservableList<Food> getMenu() {
        return menu;
    }

    /**
     * Adds the food item to the restaurant's menu
     * @param toAdd
     */
    public void addFood(Food toAdd) {
        requireNonNull(toAdd);
        boolean isDuplicate = menu.stream().anyMatch(toAdd::isSameFood);
        if (isDuplicate) {
            throw new DuplicateFoodException();
        }
        menu.add(toAdd);
    }

    /**
     * Removes the food time from the restaurant's menu
     * @param toRemove
     */
    public void removeFood(Food toRemove) {
        requireAllNonNull(toRemove);
        if (!menu.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameRestaurant(Restaurant otherRestaurant) {
        if (otherRestaurant == this) {
            return true;
        }

        return otherRestaurant != null
                && otherRestaurant.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Restaurant)) {
            return false;
        }

        Restaurant otherRestaurant = (Restaurant) other;
        return otherRestaurant.getName().equals(getName())
                && otherRestaurant.getLocation().equals(getLocation())
                && otherRestaurant.getRating().equals(getRating())
                && otherRestaurant.getTags().equals(getTags())
                && otherRestaurant.getOrders().equals(getOrders())
                && otherRestaurant.getMenu().equals(getMenu());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, rating, tags, orders, menu);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Location: ")
                .append(getLocation())
                .append(" Rating: ")
                .append(getRating())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
