package dukecooks.testutil.exercise;

import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;

/**
 * A utility class to help with building DukeCooks objects.
 * Example usage: <br>
 *     {@code DukeCooks dc = new UserProfileBuilder().withExercise("John", "Doe").build();}
 */
public class WorkoutPlannerBuilder {

    private ExerciseCatalogue dukeCooks;

    public WorkoutPlannerBuilder() {
        dukeCooks = new ExerciseCatalogue();
    }

    public WorkoutPlannerBuilder(ExerciseCatalogue dukeCooks) {
        this.dukeCooks = dukeCooks;
    }

    /**
     * Adds a new {@code Person} to the {@code DukeCooks} that we are building.
     */
    public WorkoutPlannerBuilder withExercise(Exercise exercise) {
        dukeCooks.addExercise(exercise);
        return this;
    }

    public ExerciseCatalogue build() {
        return dukeCooks;
    }
}
