package seedu.deliverymans.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.logic.Logic;
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
    private int quantityOrdered;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final List<Food> menu;

    /**
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Location location, Set<Tag> tags) {
        requireAllNonNull(name, location, tags);
        this.name = name;
        this.location = location;
        this.rating = new Rating("0", 0);
        this.tags.addAll(tags);
        this.menu = List.of();
        this.quantityOrdered = 0;
    }

    public Restaurant(Name name, Location location, Set<Tag> tags, List<Food> menu) {
        requireAllNonNull(name, location, tags, menu);
        this.name = name;
        this.location = location;
        this.rating = new Rating("0", 0);
        this.tags.addAll(tags);
        this.menu = List.copyOf(menu);
        this.quantityOrdered = 0;
    }

    public Restaurant(Name name, Location location, Rating rating, Set<Tag> tags, List<Food> menu,
                      int quantityOrdered) {
        requireAllNonNull(name, location, rating, tags);
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.tags.addAll(tags);
        this.menu = List.copyOf(menu);
        this.quantityOrdered = quantityOrdered;
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

    public List<Food> getMenu() {
        return menu;
    }

    public ObservableList<Order> getOrders(Logic logic) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        ObservableList<Order> allOrders = logic.getFilteredOrderList();
        for (Order order : allOrders) {
            if (order.getRestaurant().equals(this.name)) {
                orders.add(order);
            }
        }
        return orders;
    }

    public int getQuantityOrdered() {
        return this.quantityOrdered;
    }



    /**
     * Returns a new restaurant with the food item added to the restaurant's menu
     *
     * @param toAdd Food to be added.
     */
    public Restaurant addFood(Food toAdd) {
        requireNonNull(toAdd);
        boolean isDuplicate = menu.stream().anyMatch(toAdd::isSameFood);
        if (isDuplicate) {
            throw new DuplicateFoodException();
        }
        List<Food> updatedMenu = new ArrayList<>(menu);
        updatedMenu.add(toAdd);
        return new Restaurant(name, location, rating, tags, updatedMenu, quantityOrdered);
    }

    /**
     * Returns a new restaurant with the food item removed from the restaurant's menu
     *
     * @param toRemove Food to be removed.
     */
    public Restaurant removeFood(Food toRemove) {
        requireAllNonNull(toRemove);
        List<Food> updatedMenu = new ArrayList<>(menu);
        if (!updatedMenu.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
        return new Restaurant(name, location, rating, tags, updatedMenu, quantityOrdered);
    }

    /**
     * Updates quantityOrdered based on order
     */
    public Restaurant updateQuantity(Order order) {
        int updatedQuantity = quantityOrdered;
        List<Food> updatedMenu = new ArrayList<>(menu);
        for (Map.Entry<Name, Integer> entry : order.getFoodList().entrySet()) {
            for (int i = 0; i < updatedMenu.size(); i++) {
                Food food = updatedMenu.get(i);
                if (food.getName().equals(entry.getKey())) {
                    updatedQuantity += entry.getValue();
                    updatedMenu.set(i, food.addQuantity(entry.getValue()));
                }
            }
        }
        for (int i = 0; i < updatedMenu.size(); i++) {
            Food food = updatedMenu.get(i);
            updatedMenu.set(i, food.updateTag(updatedQuantity, updatedMenu.size()));
        }
        return new Restaurant(name, location, rating, tags, updatedMenu, updatedQuantity);
    }

    /**
     * Updates quantityOrdered based on quantity and returns a new Restaurant.
     */
    public Restaurant updateQuantity(int quantity) {
        List<Food> updatedMenu = new ArrayList<>();
        int updatedQuantity = this.quantityOrdered + quantity;
        for (Food food : this.menu) {
            updatedMenu.add(food.updateTag(updatedQuantity, this.menu.size()));
        }
        return new Restaurant(name, location, rating, tags, updatedMenu, updatedQuantity);
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
                && otherRestaurant.getMenu().equals(getMenu())
                && otherRestaurant.getQuantityOrdered() == this.quantityOrdered;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, rating, tags, menu, quantityOrdered);
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
