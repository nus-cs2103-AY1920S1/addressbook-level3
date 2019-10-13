package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Name;
import seedu.address.model.details.ExerciseDetail;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Exercise[] getSamplePersons() {
        return new Exercise[] {
            new Exercise(new Name("Alex Yeoh"),
                getTagSet("friends")),
            new Exercise(new Name("Bernice Yu"),
                getTagSet("colleagues", "friends")),
            new Exercise(new Name("Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Exercise(new Name("David Li"),
                getTagSet("family")),
            new Exercise(new Name("Irfan Ibrahim"),
                getTagSet("classmates")),
            new Exercise(new Name("Roy Balakrishnan"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Exercise sampleExercise : getSamplePersons()) {
            sampleDc.addPerson(sampleExercise);
        }
        return sampleDc;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<ExerciseDetail> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(ExerciseDetail::new)
                .collect(Collectors.toSet());
    }

}
