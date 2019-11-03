package seedu.exercise.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UniqueResourceList;
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
            new Exercise(new Name("Rope Skipping"), new Date("12/12/2019"), new Calories("330"),
                new Quantity("10"), new Unit("counts"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Cycling"), new Date("12/12/2019"), new Calories("284"),
                new Quantity("5"), new Unit("km"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Strength Training"), new Date("12/12/2019"), new Calories("341"),
                new Quantity("20"), new Unit("counts"),
                getMuscleSet("Chest")),
            new Exercise(new Name("Swimming"), new Date("12/12/2019"), new Calories("354"),
                new Quantity("10"), new Unit("laps"),
                getMuscleSet("Calves")),
            new Exercise(new Name("Bench Press"), new Date("12/12/2019"), new Calories("222"),
                new Quantity("30"), new Unit("counts"),
                getMuscleSet("Triceps")),
            new Exercise(new Name("Running"), new Date("12/12/2019"), new Calories("9999"),
                new Quantity("2.4"), new Unit("km"),
                getMuscleSet("Legs"))
        };
    }

    public static Regime[] getSampleRegimes() {
        UniqueResourceList<Exercise> list1 = new UniqueResourceList<>();
        list1.add(new Exercise(new Name("Rope Skipping"), new Date("12/12/2019"), new Calories("330"),
            new Quantity("10"), new Unit("counts"),
            getMuscleSet("Legs")));
        list1.add(new Exercise(new Name("Bench Press"), new Date("12/12/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps")));


        UniqueResourceList<Exercise> list2 = new UniqueResourceList<>();
        list2.add(new Exercise(new Name("Running"), new Date("12/12/2019"), new Calories("9999"),
            new Quantity("2.4"), new Unit("km"),
            getMuscleSet("Legs")));
        list2.add(new Exercise(new Name("Bench Press"), new Date("12/12/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps")));
        list2.add(new Exercise(new Name("Swimming"), new Date("12/12/2019"), new Calories("354"),
            new Quantity("10"), new Unit("laps"),
            getMuscleSet("Calves")));

        UniqueResourceList<Exercise> list3 = new UniqueResourceList<>();
        list3.add(new Exercise(new Name("Rope Skipping"), new Date("12/12/2019"), new Calories("330"),
            new Quantity("10"), new Unit("counts"),
            getMuscleSet("Legs")));
        list3.add(new Exercise(new Name("Swimming"), new Date("12/12/2019"), new Calories("354"),
            new Quantity("10"), new Unit("laps"),
            getMuscleSet("Calves")));
        list3.add(new Exercise(new Name("Bench Press"), new Date("12/12/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps")));
        list3.add(new Exercise(new Name("Cycling"), new Date("12/12/2019"), new Calories("284"),
            new Quantity("5"), new Unit("km"),
            getMuscleSet("Legs")));
        list3.add(new Exercise(new Name("Strength Training"), new Date("12/12/2019"), new Calories("341"),
            new Quantity("20"), new Unit("counts"),
            getMuscleSet("Chest")));

        return new Regime[]{
            new Regime(new Name("Level 1"), list1),
            new Regime(new Name("Level 2"), list2),
            new Regime(new Name("Level 3"), list3)
        };
    }

    public static Schedule[] getSampleSchedules() {
        Regime[] sampleRegimes = getSampleRegimes();
        return new Schedule[]{
            new Schedule(sampleRegimes[0], new Date("29/12/2019")),
            new Schedule(sampleRegimes[1], new Date("30/12/2019")),
            new Schedule(sampleRegimes[2], new Date("31/12/2019"))
        };
    }

    public static Exercise[] getBasicExercises() {
        return new Exercise[]{
            new Exercise(new Name("Running"), new Date("26/09/2019"), new Calories("333"),
                new Quantity("2.4"), new Unit("km"),
                getMuscleSet("Legs", "Cardio")),
            new Exercise(new Name("Push ups"), new Date("26/09/2019"), new Calories("50"),
                new Quantity("60"), new Unit("counts"),
                getMuscleSet("Chest", "Triceps")),
            new Exercise(new Name("Sit ups"), new Date("26/09/2019"), new Calories("50"),
                new Quantity("60"), new Unit("counts"),
                getMuscleSet("Abs", "Core"))
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
        ReadOnlyResourceBook<Exercise> sampleEb = new ReadOnlyResourceBook<>();
        for (Exercise sampleExercise : getSampleExercises()) {
            sampleEb.addResource(sampleExercise);
        }
        return sampleEb;
    }

    public static ReadOnlyResourceBook<Schedule> getSampleScheduleBook() {
        ReadOnlyResourceBook<Schedule> sampleSb = new ReadOnlyResourceBook<>();
        for (Schedule sampleSchedule : getSampleSchedules()) {
            sampleSb.addResource(sampleSchedule);
        }
        return sampleSb;
    }

    public static ReadOnlyResourceBook<Regime> getSampleRegimeBook() {
        ReadOnlyResourceBook<Regime> sampleRb = new ReadOnlyResourceBook<>();
        for (Regime sampleRegime : getSampleRegimes()) {
            sampleRb.addResource(sampleRegime);
        }
        return sampleRb;
    }
}
