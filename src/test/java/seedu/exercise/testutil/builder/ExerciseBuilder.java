package seedu.exercise.testutil.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.util.SampleDataUtil;

/**
 * A utility class to help with building Exercise objects.
 */
public class ExerciseBuilder {

    private static final String DEFAULT_NAME = "Running";
    private static final String DEFAULT_DATE = "26/09/2019";
    private static final String DEFAULT_CALORIES = "111";
    private static final String DEFAULT_QUANTITY = "0.5";
    private static final String DEFAULT_UNIT = "hours";

    private Name name;
    private Date date;
    private Calories calories;
    private Quantity quantity;
    private Unit unit;
    private Set<Muscle> muscles;
    private Map<String, String> customProperties;

    public ExerciseBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        calories = new Calories(DEFAULT_CALORIES);
        quantity = new Quantity(DEFAULT_QUANTITY);
        unit = new Unit(DEFAULT_UNIT);
        muscles = new HashSet<>();
        customProperties = new HashMap<>();
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code exerciseToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        name = exerciseToCopy.getName();
        date = exerciseToCopy.getDate();
        calories = exerciseToCopy.getCalories();
        quantity = exerciseToCopy.getQuantity();
        unit = exerciseToCopy.getUnit();
        muscles = new HashSet<>(exerciseToCopy.getMuscles());
        customProperties = new HashMap<>(exerciseToCopy.getCustomPropertiesMap());
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

    /**
     * Sets the {@code Unit} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withUnit(String unit) {
        this.unit = new Unit(unit);
        return this;
    }

    /**
     * Sets the {@code customProperties} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withCustomProperties(Map<String, String> customProperties) {
        this.customProperties = customProperties;
        return this;
    }

    public Exercise build() {
        return new Exercise(name, date, calories, quantity, unit, muscles, customProperties);
    }

}
