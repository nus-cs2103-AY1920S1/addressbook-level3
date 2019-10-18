package seedu.address.testutil.exercise;

import seedu.address.model.exercise.WorkoutPlanner;
import seedu.address.model.exercise.components.Exercise;

/**
 * A utility class to help with building DukeCooks objects.
 * Example usage: <br>
 *     {@code DukeCooks dc = new UserProfileBuilder().withExercise("John", "Doe").build();}
 */
public class WorkoutPlannerBuilder {

    private WorkoutPlanner dukeCooks;

    public WorkoutPlannerBuilder() {
        dukeCooks = new WorkoutPlanner();
    }

    public WorkoutPlannerBuilder(WorkoutPlanner dukeCooks) {
        this.dukeCooks = dukeCooks;
    }

    /**
     * Adds a new {@code Person} to the {@code DukeCooks} that we are building.
     */
    public WorkoutPlannerBuilder withExercise(Exercise exercise) {
        dukeCooks.addExercise(exercise);
        return this;
    }

    public WorkoutPlanner build() {
        return dukeCooks;
    }
}
