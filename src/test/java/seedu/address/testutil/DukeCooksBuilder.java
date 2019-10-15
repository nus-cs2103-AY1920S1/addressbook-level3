package seedu.address.testutil;

import seedu.address.model.WorkoutPlanner;
import seedu.address.model.exercise.Exercise;

/**
 * A utility class to help with building DukeCooks objects.
 * Example usage: <br>
 *     {@code DukeCooks dc = new DukeCooksBuilder().withPerson("John", "Doe").build();}
 */
public class DukeCooksBuilder {

    private WorkoutPlanner dukeCooks;

    public DukeCooksBuilder() {
        dukeCooks = new WorkoutPlanner();
    }

    public DukeCooksBuilder(WorkoutPlanner dukeCooks) {
        this.dukeCooks = dukeCooks;
    }

    /**
     * Adds a new {@code Person} to the {@code DukeCooks} that we are building.
     */
    public DukeCooksBuilder withPerson(Exercise exercise) {
        dukeCooks.addExercise(exercise);
        return this;
    }

    public WorkoutPlanner build() {
        return dukeCooks;
    }
}
