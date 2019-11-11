package seedu.deliverymans.model.food;

import static seedu.deliverymans.commons.util.AppUtil.checkArgument;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

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
    private final Set<Tag> tags;
    private final int quantityOrdered;

    /**
     * Constructs a {@code Food}.
     *
     * @param name A valid food name.
     * @param price A non-negative price.
     * @param tags Tags of the food.
     */
    public Food(Name name, BigDecimal price, Set<Tag> tags) {
        this(name, price, tags, 0);
    }

    public Food(Name name, BigDecimal price, Set<Tag> tags, int quantityOrdered) {
        requireAllNonNull(name, price, quantityOrdered, tags);
        checkArgument(isValidPrice(price), PRICE_CONSTRAINTS);
        checkArgument(isValidQuantity(quantityOrdered), QUANTITY_CONSTRAINTS);
        this.name = name;
        this.price = price;
        this.quantityOrdered = quantityOrdered;
        this.tags = Set.copyOf(tags);
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

    /**
     * Returns a new Food with quantity added to quantityOrdered.
     */
    public Food addQuantity(int quantity) {
        return new Food(name, price, tags, quantityOrdered + quantity);
    }

    /**
     * Updates tags with "Popular" tag and returns a new Food if necessary.
     */
    public Food updateTag(int totalQuantity, int menuSize) {
        Set<Tag> updatedTags = new HashSet<>(tags);
        updatedTags.remove(new Tag("Popular"));
        if (quantityOrdered != 0 && quantityOrdered >= 1.5 * totalQuantity / menuSize) {
            updatedTags.add(new Tag("Popular"));
        }
        if (tags.equals(updatedTags)) {
            return this;
        } else {
            return new Food(name, price, updatedTags, quantityOrdered);
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

    public Set<Tag> getTags() {
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
