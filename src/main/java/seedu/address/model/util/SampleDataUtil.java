package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ExerciseBook;
import seedu.address.model.ReadOnlyExerciseBook;
import seedu.address.model.exercise.Calories;
import seedu.address.model.exercise.Date;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Name;
import seedu.address.model.exercise.Quantity;
import seedu.address.model.tag.Muscle;

/**
 * Contains utility methods for populating {@code ExerciseBook} with sample data.
 */
public class SampleDataUtil {
    public static Exercise[] getSampleExercises() {
        return new Exercise[]{
            new Exercise(new Name("Rope Skipping"), new Date("26/09/2019"), new Calories("330"),
                new Quantity("10 counts of 10"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Cycling"), new Date("26/09/2019"), new Calories("284"),
                new Quantity("Cycled 5km"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Strength Training"), new Date("26/09/2019"), new Calories("341"),
                new Quantity("20 counts of 10"),
                getMuscleSet("Chest")),
            new Exercise(new Name("Swimming"), new Date("26/09/2019"), new Calories("354"),
                new Quantity("10 laps"),
                getMuscleSet("Calves")),
            new Exercise(new Name("Bench Press"), new Date("26/09/2019"), new Calories("222"),
                new Quantity("30 counts"),
                getMuscleSet("Triceps")),
            new Exercise(new Name("Running"), new Date("26/09/2019"), new Calories("9999999"),
                new Quantity("2.4km clocked"),
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
