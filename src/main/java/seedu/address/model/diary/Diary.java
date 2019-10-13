package seedu.address.model.diary;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.trip.Trip;

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

    public DiaryEntryList getDiaryEntries() {
        return diaryEntryList;
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
        return diaryEntryList.equals(otherDiary.getDiaryEntries());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Diary Entries: ")
                .append(diaryEntryList.toString());

        return builder.toString();
    }
}
