package seedu.exercise.testutil.builder;

import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

/**
 * A utility class to help with building ExerciseBook objects.
 * Example usage: <br>
 * {@code ReadOnlyResourceBook<Exercise> eb = new ExerciseBookBuilder().withExercise("Dance", "Swim").build();}
 */
public class ExerciseBookBuilder {

    private ReadOnlyResourceBook<Exercise> exerciseBook;

    public ExerciseBookBuilder() {
        exerciseBook = new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR);
    }

    public ExerciseBookBuilder(ReadOnlyResourceBook<Exercise> exerciseBook) {
        this.exerciseBook = exerciseBook;
    }

    /**
     * Adds a new {@code Exercise} to the {@code ReadOnlyResourceBook<Exercise>} that we are building.
     */
    public ExerciseBookBuilder withExercise(Exercise exercise) {
        exerciseBook.addResource(exercise);
        return this;
    }

    public ReadOnlyResourceBook<Exercise> build() {
        return exerciseBook;
    }
}
