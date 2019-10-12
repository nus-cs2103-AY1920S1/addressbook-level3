package seedu.address.testutil;

import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * A utility class to help with building Diary objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code diaryToCopy}.
     */
    public PersonBuilder(Diary diaryToCopy) {
        name = diaryToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Diary} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Diary} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        return this;
    }


    public Diary build() {
        return new Diary(name);
    }

}
