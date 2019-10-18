package seedu.address.model.util;

import seedu.address.model.diary.DiaryRecords;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.diary.components.Diary;
import seedu.address.model.diary.components.DiaryName;

/**
 * Contains utility methods for populating {@code DiaryRecords} with sample data.
 */
public class DiarySampleDataUtil {
    public static Diary[] getSampleDiaries() {
        return new Diary[] {
            new Diary(new DiaryName("Asian Cuisines")),
            new Diary(new DiaryName("Healthy Living")),
            new Diary(new DiaryName("Meat Lovers")),
            new Diary(new DiaryName("Vegan Diet")),
            new Diary(new DiaryName("One Week Slimming")),
            new Diary(new DiaryName("Core Exercises")),
        };
    }

    public static ReadOnlyDiary getSampleDiaryRecords() {
        DiaryRecords sampleDc = new DiaryRecords();
        for (Diary sampleDiary : getSampleDiaries()) {
            sampleDc.addDiary(sampleDiary);
        }
        return sampleDc;
    }
}
