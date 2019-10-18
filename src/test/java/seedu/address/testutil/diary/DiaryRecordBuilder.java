package seedu.address.testutil.diary;

import seedu.address.model.diary.DiaryRecords;
import seedu.address.model.diary.components.Diary;

/**
 * A utility class to help with building DiaryRecords objects.
 * Example usage: <br>
 *     {@code DiaryRecords dc = new DiaryRecordBuilder().withDiary("John", "Doe").build();}
 */
public class DiaryRecordBuilder {

    private DiaryRecords diaryRecords;

    public DiaryRecordBuilder() {
        diaryRecords = new DiaryRecords();
    }

    public DiaryRecordBuilder(DiaryRecords diaryRecords) {
        this.diaryRecords = diaryRecords;
    }

    /**
     * Adds a new {@code Diary} to the {@code DiaryRecords} that we are building.
     */
    public DiaryRecordBuilder withDiary(Diary diary) {
        diaryRecords.addDiary(diary);
        return this;
    }

    public DiaryRecords build() {
        return diaryRecords;
    }
}
