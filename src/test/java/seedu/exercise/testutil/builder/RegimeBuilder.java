package seedu.exercise.testutil.builder;

import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Builder for {@code Regime}.
 */
public class RegimeBuilder {

    private static final String DEFAULT_NAME = "cardio";

    private Name regimeName;
    private UniqueResourceList<Exercise> regimeExercises;

    public RegimeBuilder() {
        regimeName = new Name(DEFAULT_NAME);
        regimeExercises = new UniqueResourceList<>();
    }

    /**
     * Parses and sets the name of the object we are building to {@code name}.
     */
    public RegimeBuilder withName(String name) {
        this.regimeName = new Name(name);
        return this;
    }

    /**
     * Sets the exercise list of the regime we are building to {@code regimeExercises}.
     */
    public RegimeBuilder withExerciseList(UniqueResourceList<Exercise> regimeExercises) {
        this.regimeExercises = regimeExercises;
        return this;
    }

    public Regime build() {
        return new Regime(regimeName, regimeExercises);
    }
}
