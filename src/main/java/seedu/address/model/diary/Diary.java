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

    public Optional<DiaryEntry> getFirstDiaryEntry() {
        return diaryEntryList.getFirstDiaryEntry();
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
        return diaryEntryList.equals(otherDiary.getDiaryEntryList());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Diary Entries: ")
                .append(diaryEntryList.toString());

        return builder.toString();
    }
}
