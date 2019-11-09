package seedu.exercise.model.util;

import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.util.DateChangerUtil.changeAllDate;
import static seedu.exercise.model.util.SampleDataUtil.getMuscleSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;

/**
 * Contains utility methods for exercise database.
 */
public class DefaultExerciseDatabaseUtil {

    public static Exercise[] getExerciseDatabase() {
        return new Exercise[]{
            new Exercise(new Name("Push Ups"), new Date("11/11/2019"), new Calories("30"),
                new Quantity("100"), new Unit("counts"),
                getMuscleSet("Chest", "Triceps"), new TreeMap<>()),
            new Exercise(new Name("Sit ups"), new Date("10/11/2019"), new Calories("30"),
                new Quantity("100"), new Unit("counts"),
                getMuscleSet("Abs"), new TreeMap<>()),
            new Exercise(new Name("Bench Press"), new Date("11/11/2019"), new Calories("50"),
                new Quantity("70"), new Unit("kg"),
                getMuscleSet("Chest", "Triceps"), new TreeMap<>()),
            new Exercise(new Name("Deadlift"), new Date("11/11/2019"), new Calories("80"),
                new Quantity("120"), new Unit("kg"),
                getMuscleSet("Legs", "Back"), new TreeMap<>()),
            new Exercise(new Name("Run"), new Date("11/11/2019"), new Calories("200"),
                new Quantity("10"), new Unit("km"),
                getMuscleSet("Triceps"), new TreeMap<>()),
            new Exercise(new Name("Squat"), new Date("11/11/2019"), new Calories("100"),
                new Quantity("100"), new Unit("kg"),
                getMuscleSet("Legs", "Back"), new TreeMap<>()),
            new Exercise(new Name("Pull Ups"), new Date("11/11/2019"), new Calories("50"),
                new Quantity("50"), new Unit("counts"),
                getMuscleSet("Back", "Biceps"), new TreeMap<>())
        };
    }

    public static List<Exercise> getBasicExercises() {
        Collection<Exercise> basicExercises = Arrays.asList(
                new Exercise(new Name("Push Ups"), new Date("07/11/2019"), new Calories("80"),
                        new Quantity("100"), new Unit("counts"),
                        getMuscleSet("Chest", "Triceps"), new TreeMap<>()),
                new Exercise(new Name("Sit ups"), new Date("07/11/2019"), new Calories("50"),
                        new Quantity("100"), new Unit("counts"),
                        getMuscleSet("Abs"), new TreeMap<>()),
                new Exercise(new Name("Squats"), new Date("07/11/2019"), new Calories("100"),
                        new Quantity("100"), new Unit("counts"),
                        getMuscleSet("Legs"), new TreeMap<>()),
                new Exercise(new Name("Run"), new Date("07/11/2019"), new Calories("300"),
                        new Quantity("10"), new Unit("km"),
                        getMuscleSet("Legs", "Cardio"), new TreeMap<>())
        );
        return new ArrayList<>(changeAllDate(basicExercises, Date.getToday()));
    }

    public static ReadOnlyResourceBook<Exercise> getExerciseDatabaseBook() {
        ReadOnlyResourceBook<Exercise> exerciseDatabaseBook = new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR);
        Collection<Exercise> exerciseDatabase = changeAllDate(Arrays.asList(getExerciseDatabase()), Date.getToday());
        for (Exercise exercise : exerciseDatabase) {
            exerciseDatabaseBook.addResource(exercise);
        }
        return exerciseDatabaseBook;
    }
}
