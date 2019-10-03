package seedu.exercise.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.exercise.model.exercise.Calories;
import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.Name;
import seedu.exercise.model.exercise.Quantity;
import seedu.exercise.model.tag.Muscle;
import seedu.exercise.model.util.SampleDataUtil;

/**
 * A utility class to help with building Exercise objects.
 */
public class ExerciseBuilder {

    private static final String DEFAULT_NAME = "Running";
    private static final String DEFAULT_DATE = "26/09/2019";
    private static final String DEFAULT_CALORIES = "111";
    private static final String DEFAULT_QUANTITY = "2.4km clocked";

    private Name name;
    private Date date;
    private Calories calories;
    private Quantity quantity;
    private Set<Muscle> muscles;

    public ExerciseBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        calories = new Calories(DEFAULT_CALORIES);
        quantity = new Quantity(DEFAULT_QUANTITY);
        muscles = new HashSet<>();
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code exerciseToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        name = exerciseToCopy.getName();
        date = exerciseToCopy.getDate();
        calories = exerciseToCopy.getCalories();
        quantity = exerciseToCopy.getQuantity();
        muscles = new HashSet<>(exerciseToCopy.getMuscles());
    }

    /**
     * Sets the {@code Name} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code muscles} into a {@code Set<Muscle>} and set it to the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withMuscles(String... muscles) {
        this.muscles = SampleDataUtil.getMuscleSet(muscles);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withCalories(String calories) {
        this.calories = new Calories(calories);
        return this;
    }

    public Exercise build() {
        return new Exercise(name, date, calories, quantity, muscles);
    }

}
