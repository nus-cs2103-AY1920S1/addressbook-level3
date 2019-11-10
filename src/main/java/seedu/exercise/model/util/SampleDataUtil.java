package seedu.exercise.model.util;

import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.SortedUniqueResourceList;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Contains utility methods for populating {@code ReadOnlyResourceBook} with sample data.
 */
public class SampleDataUtil {
    public static Exercise[] getSampleExercises() {
        return new Exercise[]{
            new Exercise(new Name("Rope Skipping"), new Date("11/11/2019"), new Calories("330"),
                new Quantity("10"), new Unit("counts"),
                getMuscleSet("Legs"), new TreeMap<>()),
            new Exercise(new Name("Cycling"), new Date("10/11/2019"), new Calories("284"),
                new Quantity("5"), new Unit("km"),
                getMuscleSet("Legs"), new TreeMap<>()),
            new Exercise(new Name("Strength Training"), new Date("09/11/2019"), new Calories("341"),
                new Quantity("20"), new Unit("counts"),
                getMuscleSet("Chest"), new TreeMap<>()),
            new Exercise(new Name("Swimming"), new Date("08/11/2019"), new Calories("354"),
                new Quantity("10"), new Unit("laps"),
                getMuscleSet("Calves"), new TreeMap<>()),
            new Exercise(new Name("Bench Press"), new Date("07/11/2019"), new Calories("222"),
                new Quantity("30"), new Unit("counts"),
                getMuscleSet("Triceps"), new TreeMap<>()),
            new Exercise(new Name("Running"), new Date("06/11/2019"), new Calories("431"),
                new Quantity("2.4"), new Unit("km"),
                getMuscleSet("Legs"), new TreeMap<>())
        };
    }

    public static Regime[] getSampleRegimes() {
        SortedUniqueResourceList<Exercise> list1 = new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        list1.add(new Exercise(new Name("Rope Skipping"), new Date("11/11/2019"), new Calories("330"),
            new Quantity("10"), new Unit("counts"),
            getMuscleSet("Legs"), new TreeMap<>()));
        list1.add(new Exercise(new Name("Bench Press"), new Date("07/11/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps"), new TreeMap<>()));


        SortedUniqueResourceList<Exercise> list2 = new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        list2.add(new Exercise(new Name("Running"), new Date("06/11/2019"), new Calories("9999"),
            new Quantity("2.4"), new Unit("km"),
            getMuscleSet("Legs"), new TreeMap<>()));
        list2.add(new Exercise(new Name("Bench Press"), new Date("07/11/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps"), new TreeMap<>()));
        list2.add(new Exercise(new Name("Swimming"), new Date("08/11/2019"), new Calories("354"),
            new Quantity("10"), new Unit("laps"),
            getMuscleSet("Calves"), new TreeMap<>()));

        SortedUniqueResourceList<Exercise> list3 = new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        list3.add(new Exercise(new Name("Rope Skipping"), new Date("11/11/2019"), new Calories("330"),
            new Quantity("10"), new Unit("counts"),
            getMuscleSet("Legs"), new TreeMap<>()));
        list3.add(new Exercise(new Name("Swimming"), new Date("08/11/2019"), new Calories("354"),
            new Quantity("10"), new Unit("laps"),
            getMuscleSet("Calves"), new TreeMap<>()));
        list3.add(new Exercise(new Name("Bench Press"), new Date("07/11/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps"), new TreeMap<>()));
        list3.add(new Exercise(new Name("Cycling"), new Date("10/11/2019"), new Calories("284"),
            new Quantity("5"), new Unit("km"),
            getMuscleSet("Legs"), new TreeMap<>()));
        list3.add(new Exercise(new Name("Strength Training"), new Date("09/11/2019"), new Calories("341"),
            new Quantity("20"), new Unit("counts"),
            getMuscleSet("Chest"), new TreeMap<>()));

        return new Regime[]{
            new Regime(new Name("Level 1"), list1),
            new Regime(new Name("Level 2"), list2),
            new Regime(new Name("Level 3"), list3)
        };
    }

    public static Schedule[] getSampleSchedules() {
        Regime[] sampleRegimes = getSampleRegimes();
        return new Schedule[]{
            new Schedule(sampleRegimes[0], new Date("12/12/2019")),
            new Schedule(sampleRegimes[1], new Date("13/12/2019")),
            new Schedule(sampleRegimes[2], new Date("14/12/2019"))
        };
    }

    /**
     * Returns a muscle set containing the list of strings given.
     */
    public static Set<Muscle> getMuscleSet(String... strings) {
        return Arrays.stream(strings)
            .map(Muscle::new)
            .collect(Collectors.toSet());
    }


    public static ReadOnlyResourceBook<Exercise> getSampleExerciseBook() {
        ReadOnlyResourceBook<Exercise> sampleEb = new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR);
        for (Exercise sampleExercise : getSampleExercises()) {
            sampleEb.addResource(sampleExercise);
        }
        return sampleEb;
    }

    public static ReadOnlyResourceBook<Schedule> getSampleScheduleBook() {
        ReadOnlyResourceBook<Schedule> sampleSb = new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR);
        for (Schedule sampleSchedule : getSampleSchedules()) {
            sampleSb.addResource(sampleSchedule);
        }
        return sampleSb;
    }

    public static ReadOnlyResourceBook<Regime> getSampleRegimeBook() {
        ReadOnlyResourceBook<Regime> sampleRb = new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR);
        for (Regime sampleRegime : getSampleRegimes()) {
            sampleRb.addResource(sampleRegime);
        }
        return sampleRb;
    }
}
