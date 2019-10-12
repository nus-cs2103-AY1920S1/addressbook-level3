package calofit.model.dish;

import static calofit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import calofit.model.tag.Tag;

/**
 * Represents a Dish in the dish database.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Dish {

    // Identity fields
    private final Calorie calories;

    private final Name name;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Dish(Name name, Calorie calories, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.calories = calories;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for a Dish without Tag.
     *
     * @param name is the name of the dish.
     */
    public Dish(Name name, Calorie calories) {
        this(name, calories, new HashSet<Tag>());
    }

    public Name getName() {
        return name;
    }

    public Calorie getCalories() {
        return this.calories;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both dishes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two dishes.
     */
    public boolean isSameDish(Dish otherDish) {
        if (otherDish == this) {
            return true;
        }

        //return otherDish != null && otherDish.getName().equals(getName());

        return otherDish != null
                && otherDish.getName().equals(getName())
                && otherDish.getCalories().equals(getCalories());
    }

    /**
     * Returns true if both dishes have the same name
     * This defines a weaker notion of equality between two dishes.
     */

    public boolean isSameDishName(Dish otherDish) {
        if (otherDish == this) {
            return true;
        }

        return otherDish != null
                && otherDish.getName().equals(getName());
    }

    /**
     * Returns true if both dishes have the same identity and data fields.
     * This defines a stronger notion of equality between two dishes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Dish)) {
            return false;
        }

        Dish otherDish = (Dish) other;
        return otherDish.getName().equals(getName())
                && otherDish.getCalories().equals(getCalories())
                && otherDish.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Calories: ")
                .append(getCalories().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
