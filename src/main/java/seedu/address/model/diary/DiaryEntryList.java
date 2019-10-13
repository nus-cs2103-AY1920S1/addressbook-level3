package seedu.address.model.diary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.itinerary.day.DayList;

/**
 * Abstraction of a list of {@link DiaryEntry}s.
 * It is backed by an {@code ObservableList}.
 */
public class DiaryEntryList {

    private ObservableList<DiaryEntry> diaryEntries;

    DiaryEntryList(DayList dayList) {
        diaryEntries = FXCollections.observableArrayList();
        dayList.forEach(day -> {
            diaryEntries.add(new DiaryEntry(day));
        });
    }

    public ObservableList<DiaryEntry> getDiaryEntries() {
        return diaryEntries;
    }

    public ObservableList<DiaryEntry> getReadOnlyDiaryEntries() {
        return FXCollections.unmodifiableObservableList(diaryEntries);
    }


}
