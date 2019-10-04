package seedu.exercise.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.exercise.Calories;
import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.Name;
import seedu.exercise.model.exercise.Quantity;
import seedu.exercise.model.exercise.Unit;
import seedu.exercise.model.tag.Muscle;

/**
 * Contains utility methods for populating {@code ExerciseBook} with sample data.
 */
public class SampleDataUtil {
    public static Exercise[] getSampleExercises() {
        return new Exercise[]{
            new Exercise(new Name("Rope Skipping"), new Date("26/09/2019"), new Calories("330"),
                new Quantity("10"), new Unit("counts"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Cycling"), new Date("26/09/2019"), new Calories("284"),
                new Quantity("5"), new Unit("km"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Strength Training"), new Date("26/09/2019"), new Calories("341"),
                new Quantity("20"), new Unit("counts"),
                getMuscleSet("Chest")),
            new Exercise(new Name("Swimming"), new Date("26/09/2019"), new Calories("354"),
                new Quantity("10"), new Unit("laps"),
                getMuscleSet("Calves")),
            new Exercise(new Name("Bench Press"), new Date("26/09/2019"), new Calories("222"),
                new Quantity("30"), new Unit("counts"),
                getMuscleSet("Triceps")),
            new Exercise(new Name("Running"), new Date("26/09/2019"), new Calories("9999999"),
                new Quantity("2.4"), new Unit("km"),
                getMuscleSet("Legs"))
        };
    }

    public static ReadOnlyExerciseBook getSampleExerciseBook() {
        ExerciseBook sampleEb = new ExerciseBook();
        for (Exercise sampleExercise : getSampleExercises()) {
            sampleEb.addExercise(sampleExercise);
        }
        return sampleEb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Muscle> getMuscleSet(String... strings) {
        return Arrays.stream(strings)
            .map(Muscle::new)
            .collect(Collectors.toSet());
    }

}
