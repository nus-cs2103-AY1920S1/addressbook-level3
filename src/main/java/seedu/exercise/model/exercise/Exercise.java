package seedu.exercise.model.exercise;

import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.exercise.model.tag.Muscle;

/**
 * Represents an Exercise in the exercise book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise {

    // Identity fields
    private final Name name;
    private final Date date;

    // Data fields
    private final Quantity quantity;
    private final Unit unit;
    private final Calories calories;
    private final Set<Muscle> muscles = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Exercise(Name name, Date date, Calories calories, Quantity quantity, Unit unit, Set<Muscle> muscles) {
        requireAllNonNull(name, date, calories, quantity, unit, muscles);
        this.name = name;
        this.date = date;
        this.calories = calories;
        this.quantity = quantity;
        this.unit = unit;
        this.muscles.addAll(muscles);
    }

    public Name getName() {
        return name;
    }

    public Calories getCalories() {
        return calories;
    }

    public Date getDate() {
        return date;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Muscle> getMuscles() {
        return Collections.unmodifiableSet(muscles);
    }

    /**
     * Returns true if both exercises of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two exercises.
     */
    public boolean isSameExercise(Exercise otherExercise) {
        if (otherExercise == this) {
            return true;
        }

        return otherExercise != null
            && otherExercise.getName().equals(getName())
            && otherExercise.getDate().equals(getDate());
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     * This defines a stronger notion of equality between two exercises.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) other;
        return otherExercise.getName().equals(getName())
            && otherExercise.getCalories().equals(getCalories())
            && otherExercise.getDate().equals(getDate())
            && otherExercise.getQuantity().equals(getQuantity())
            && otherExercise.getUnit().equals(getUnit())
            && otherExercise.getMuscles().equals(getMuscles());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, calories, quantity, unit, muscles);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Date: ")
            .append(getDate())
            .append(" Calories: ")
            .append(getCalories())
            .append(" Quantity: ")
            .append(getQuantity())
            .append("Unit")
            .append(getUnit())
            .append(" Muscle(s): ");
        getMuscles().forEach(builder::append);
        return builder.toString();
    }

}
