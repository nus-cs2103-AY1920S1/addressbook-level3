package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.exercise.Name;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;
    private Set<ExerciseDetail> exerciseDetails;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        exerciseDetails = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Exercise exerciseToCopy) {
        name = exerciseToCopy.getName();
        exerciseDetails = new HashSet<>(exerciseToCopy.getExerciseDetails());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.exerciseDetails = SampleDataUtil.getTagSet(tags);
        return this;
    }


    public Exercise build() {
        return new Exercise(name, exerciseDetails);
    }

}
