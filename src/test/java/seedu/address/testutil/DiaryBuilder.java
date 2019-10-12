package seedu.address.testutil;

import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * A utility class to help with building Diary objects.
 */
public class DiaryBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;

    public DiaryBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the DiaryBuilder with the data of {@code diaryToCopy}.
     */
    public DiaryBuilder(Diary diaryToCopy) {
        name = diaryToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Diary} that we are building.
     */
    public DiaryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Diary build() {
        return new Diary(name);
    }

}
