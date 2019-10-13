package seedu.address.model.diary;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.itinerary.day.DayList;

/**
 * Abstraction of a list of {@link DiaryEntry}s.
 * It is backed by an {@code ObservableList}.
 */
public class DiaryEntryList {

    private ObservableList<DiaryEntry> diaryEntries;

    DiaryEntryList() {
        diaryEntries = FXCollections.observableArrayList();
    }

    public DiaryEntryList(Collection<DiaryEntry> diaryEntries) {
        this();
        this.diaryEntries.addAll(diaryEntries);
    }

    public ObservableList<DiaryEntry> getDiaryEntries() {
        return diaryEntries;
    }

    public ObservableList<DiaryEntry> getReadOnlyDiaryEntries() {
        return FXCollections.unmodifiableObservableList(diaryEntries);
    }


}
