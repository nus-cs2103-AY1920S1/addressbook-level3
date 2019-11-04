package seedu.exercise.model.resource;

import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;

import java.util.Objects;

import seedu.exercise.model.SortedUniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.storage.resource.JsonAdaptedRegime;

/**
 * Represents a Regime in the regime book.
 */
public class Regime extends Resource {
    private final Name regimeName;
    private final SortedUniqueResourceList<Exercise> regimeExercises;

    public Regime(Name regimeName, SortedUniqueResourceList<Exercise> regimeExercises) {
        this.regimeName = regimeName;
        this.regimeExercises = regimeExercises;
    }

    public void addExercise(Exercise exercise) {
        regimeExercises.add(exercise);
    }

    public void deleteExercise(Exercise exercise) {
        regimeExercises.remove(exercise);
    }

    public Name getRegimeName() {
        return regimeName;
    }

    public SortedUniqueResourceList<Exercise> getRegimeExercises() {
        return regimeExercises;
    }

    /**
     * Returns a deep copy of the current regime.
     *
     * @return a regime object with the same name and same list of exercises
     */
    public Regime deepCopy() {
        Name newName = new Name(regimeName.toString());
        SortedUniqueResourceList<Exercise> newRegimeExercises =
                new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        newRegimeExercises.setAll(regimeExercises);
        return new Regime(newName, newRegimeExercises);
    }

    public int getTotalCalorieCount() {
        int count = 0;
        for (Exercise e : regimeExercises) {
            count += Integer.parseInt(e.getCalories().value);
        }
        return count;
    }

    /**
     * Returns true if both regimes have the same name.
     */
    @Override
    public boolean isSameResource(Resource otherResource) {
        return this.equals(otherResource);
    }

    @Override
    public String toString() {
        String str = "";
        int i = 1;
        for (Exercise e : regimeExercises) {
            str += "Exercise " + i + ": " + e.getName().toString() + "\n";
            i++;
        }
        return str;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Regime)
            && regimeName.equals(((Regime) other).getRegimeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(regimeName, regimeExercises);
    }

    @Override
    public JsonAdaptedRegime toJsonType() {
        return new JsonAdaptedRegime(this);
    }
}
