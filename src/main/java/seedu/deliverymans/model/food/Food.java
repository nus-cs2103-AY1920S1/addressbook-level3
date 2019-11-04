package seedu.deliverymans.model.food;

import static seedu.deliverymans.commons.util.AppUtil.checkArgument;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;

/**
 * Represents a Food in a restaurant.
 */
public class Food {
    public static final String PRICE_CONSTRAINTS = "Price should not be negative";
    public static final String QUANTITY_CONSTRAINTS = "Quantity should not be negative";

    private final Name name;
    private final BigDecimal price;
    private final ObservableList<Tag> tags = FXCollections.observableArrayList();
    private int quantityOrdered;

    /**
     * Constructs a {@code Food}.
     *
     * @param name A valid food name.
     * @param price A non-negative price.
     * @param tags Tags of the food.
     */
    public Food(Name name, BigDecimal price, ObservableList<Tag> tags) {
        requireAllNonNull(name, price, tags);
        checkArgument(isValidPrice(price), PRICE_CONSTRAINTS);
        this.name = name;
        this.price = price;
        this.quantityOrdered = 0;
        this.tags.addAll(tags);
    }

    public Food(Name name, BigDecimal price, ObservableList<Tag> tags, int quantityOrdered) {
        requireAllNonNull(name, price, quantityOrdered, tags);
        checkArgument(isValidPrice(price), PRICE_CONSTRAINTS);
        checkArgument(isValidQuantity(quantityOrdered),QUANTITY_CONSTRAINTS);
        this.name = name;
        this.price = price;
        this.quantityOrdered = quantityOrdered;
        this.tags.addAll(tags);
    }

    /**
     * Returns true if a given number is a valid price.
     */
    public static boolean isValidPrice(BigDecimal test) {
        return test.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Returns true if a given duration is a valid preparation time.
     */
    public static boolean isValidQuantity(int quantityOrdered) {
        return quantityOrdered >= 0;
    }

    public void addQuantity(int quantity) {
        this.quantityOrdered += quantity;
    }

    public void updateTag(int totalQuantity, int menuSize) {
        this.tags.remove(new Tag("Popular"));
        if (this.quantityOrdered >= 1.5 * totalQuantity / menuSize) {
            this.tags.add(new Tag("Popular"));
        }
    }

    public Name getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public ObservableList<Tag> getTags() {
        return tags;
    }

    public String getDisplayPrice() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(getPrice());
    }

    /**
     * Returns true if both food are of the same name.
     * This defines a weaker notion of equality between two food.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Food)) {
            return false;
        }

        // state check
        Food other = (Food) obj;
        return name.equals(other.name)
                && price.compareTo(other.price) == 0
                && quantityOrdered == other.quantityOrdered
                && tags.equals(other.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantityOrdered, tags);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: ")
                .append(getDisplayPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
