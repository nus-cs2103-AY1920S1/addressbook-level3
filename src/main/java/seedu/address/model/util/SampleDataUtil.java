package seedu.address.model.util;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Diary[] getSampleDiaries() {
        return new Diary[] {
            new Diary(new Name("Asian Cuisines")),
            new Diary(new Name("Healthy Living")),
            new Diary(new Name("Meat Lovers")),
            new Diary(new Name("Vegan Diet")),
            new Diary(new Name("One Week Slimming")),
            new Diary(new Name("Core Exercises")),
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Diary sampleDiary : getSampleDiaries()) {
            sampleDc.addDiary(sampleDiary);
        }
        return sampleDc;
    }
}
