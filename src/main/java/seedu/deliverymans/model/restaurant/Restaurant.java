package seedu.deliverymans.model.restaurant;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.deliverymans.model.Food;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Order;

/**
 * Represents a Restaurant
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Restaurant {
    private int numberOfRatings;

    // Identity fields
    private final Name name;
    private final Location location;
    private final Rating rating;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Order> orders = new HashSet<>();
    private final Set<Food> menu = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Location location, Set<Tag> tags) {
        requireAllNonNull(name, location, tags);
        this.numberOfRatings = 0;
        this.name = name;
        this.location = location;
        this.rating = new Rating("0");

        this.tags.addAll(tags);
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

    public Set<Order> getOrders() {
        return orders;
    }

    public Set<Food> getMenu() {
        return menu;
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
                && otherRestaurant.getName().equals(getName())
                && otherRestaurant.getLocation().equals(getLocation());
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
