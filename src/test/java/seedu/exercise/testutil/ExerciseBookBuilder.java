package seedu.exercise.testutil;

import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.exercise.Exercise;

/**
 * A utility class to help with building ExerciseBook objects.
 * Example usage: <br>
 * {@code ExerciseBook eb = new ExerciseBookBuilder().withExercise("Dance", "Swim").build();}
 */
public class ExerciseBookBuilder {

    private ExerciseBook exerciseBook;

    public ExerciseBookBuilder() {
        exerciseBook = new ExerciseBook();
    }

    public ExerciseBookBuilder(ExerciseBook exerciseBook) {
        this.exerciseBook = exerciseBook;
    }

    /**
     * Adds a new {@code Exercise} to the {@code ExerciseBook} that we are building.
     */
    public ExerciseBookBuilder withExercise(Exercise exercise) {
        exerciseBook.addExercise(exercise);
        return this;
    }

    public ExerciseBook build() {
        return exerciseBook;
    }
}
