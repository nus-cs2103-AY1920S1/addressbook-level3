package seedu.exercise.model.resource;

import static seedu.exercise.commons.util.CollectionUtil.mapToStringList;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.storage.resource.JsonAdaptedExercise;

/**
 * Represents an Exercise in the exercise book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise extends Resource {

    // Identity fields
    private final Name name;
    private final Date date;
    private final Quantity quantity;
    private final Unit unit;
    private final Calories calories;

    // Optional fields
    private final Set<Muscle> muscles = new HashSet<>();
    private final Map<String, String> customProperties = new TreeMap<>();

    /**
     * Every field must be present and not null.
     */
    public Exercise(Name name, Date date, Calories calories, Quantity quantity, Unit unit, Set<Muscle> muscles,
                    Map<String, String> customProperties) {
        requireAllNonNull(name, date, calories, quantity, unit, muscles);
        this.name = name;
        this.date = date;
        this.calories = calories;
        this.quantity = quantity;
        this.unit = unit;
        this.muscles.addAll(muscles);
        this.customProperties.putAll(customProperties);
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
     * Returns an immutable muscle set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Muscle> getMuscles() {
        return Collections.unmodifiableSet(muscles);
    }

    /**
     * Returns an immutable custom properties map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, String> getCustomPropertiesMap() {
        return Collections.unmodifiableMap(customProperties);
    }

    /**
     * Returns an Observable List that can be used by the UI component for display.
     */
    public ObservableList<String> getObservableCustomPropertiesList() {
        List<String> propertiesList = mapToStringList(customProperties);
        return FXCollections.observableList(propertiesList);
    }

    /**
     * Returns true if both exercises of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two exercises.
     */
    @Override
    public boolean isSameResource(Resource otherResource) {
        if (otherResource == this) {
            return true;
        }

        if (!(otherResource instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) otherResource;
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
            && otherExercise.getMuscles().equals(getMuscles())
            && otherExercise.getCustomPropertiesMap().equals(getCustomPropertiesMap());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, calories, quantity, unit, muscles, customProperties);
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
            .append(" Unit: ")
            .append(getUnit());
        appendMuscles(builder);
        builder.append(" ");
        appendCustomProperties(builder);
        return builder.toString().stripTrailing();
    }

    @Override
    public JsonAdaptedExercise toJsonType() {
        return new JsonAdaptedExercise(this);
    }

    /**
     * Appends muscle information into the input StringBuilder, if there are any.
     */
    private void appendMuscles(StringBuilder builder) {
        if (!muscles.isEmpty()) {
            builder.append(" Muscle(s): ");
            getMuscles().forEach(builder::append);
        }
    }

    /**
     * Appends custom properties information into the input StringBuilder, if there are any.
     */
    private void appendCustomProperties(StringBuilder builder) {
        if (!customProperties.isEmpty()) {
            getCustomPropertiesMap().forEach((x, y) -> builder.append(x + ": " + y + " "));
        }
    }
}
