package seedu.address.model.util;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Diary[] getSamplePersons() {
        return new Diary[] {
            new Diary(new Name("Alex Yeoh")),
            new Diary(new Name("Bernice Yu")),
            new Diary(new Name("Charlotte Oliveiro")),
            new Diary(new Name("David Li")),
            new Diary(new Name("Irfan Ibrahim")),
            new Diary(new Name("Roy Balakrishnan")),
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Diary sampleDiary : getSamplePersons()) {
            sampleDc.addPerson(sampleDiary);
        }
        return sampleDc;
    }
}
