package seedu.address.testutil;

import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryName;

/**
 * A utility class to help with building Person objects.
 */
public class DiaryBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private DiaryName diaryName;

    public DiaryBuilder() {
        diaryName = new DiaryName(DEFAULT_NAME);
    }

    /**
     * Initializes the DiaryBuilder with the data of {@code diaryToCopy}.
     */
    public DiaryBuilder(Diary diaryToCopy) {
        diaryName = diaryToCopy.getDiaryName();
    }

    /**
     * Sets the {@code DiaryName} of the {@code Diary} that we are building.
     */
    public DiaryBuilder withName(String name) {
        this.diaryName = new DiaryName(name);
        return this;
    }

    public Diary build() {
        return new Diary(diaryName);
    }

}
