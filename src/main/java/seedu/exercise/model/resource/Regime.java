package seedu.exercise.model.resource;

import java.util.Objects;

import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.storage.resource.JsonAdaptedRegime;

/**
 * Represents a Regime in the regime book.
 */
public class Regime extends Resource {
    private final Name regimeName;
    private final UniqueResourceList<Exercise> regimeExercises;

    public Regime(Name regimeName, UniqueResourceList<Exercise> regimeExercises) {
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

    public UniqueResourceList<Exercise> getRegimeExercises() {
        return regimeExercises;
    }

    /**
     * Returns a deep copy of the current regime.
     *
     * @return a regime object with the same list of exercises
     */
    public Regime deepCopy() {
        UniqueResourceList<Exercise> newRegimeExercises = new UniqueResourceList<>();
        newRegimeExercises.setAll(regimeExercises);
        return new Regime(regimeName, newRegimeExercises);
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
