package seedu.address.model.diary;

import java.util.Optional;

import seedu.address.commons.core.index.Index;

/**
 * Abstraction of the in memory storage of the diary in {@link seedu.address.model.TravelPal}.
 */
public class Diary {

    private final DiaryEntryList diaryEntryList;

    public Diary() {
        this.diaryEntryList = new DiaryEntryList();
    }

    public Diary(DiaryEntryList diaryEntryList) {
        this.diaryEntryList = diaryEntryList;
    }

    public DiaryEntryList getDiaryEntryList() {
        return diaryEntryList;
    }

    public Optional<DiaryEntry> getDiaryEntry(Index index) {
        return diaryEntryList.getDiaryEntry(index);
    }

    /**
     * Returns an {@code Optional} of the first diary entry ordered by index.
     *
     * @return The first {@code DiaryEntry} in the {@code DiaryEntryList}.
     */
    public Optional<DiaryEntry> getFirstDiaryEntry() {
        return diaryEntryList.getFirstDiaryEntry();
    }

    public void addDiaryEntry(DiaryEntry diaryEntry) {
        diaryEntryList.addDiaryEntry(diaryEntry);
    }

    public void setDiaryEntry(DiaryEntry target, DiaryEntry replacement) {
        diaryEntryList.setDiaryEntry(target, replacement);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Diary)) {
            return false;
        }

        Diary otherDiary = (Diary) other;

        return diaryEntryList.equals(otherDiary.diaryEntryList);
    }

    @Override
    public String toString() {
        return "Diary Entry List: " + diaryEntryList.toString();
    }
}
