package seedu.address.model.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Collection;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.model.itinerary.day.DayList;

/**
 * Abstraction of a list of {@link DiaryEntry}s.
 * It is backed by an {@code ObservableList}.
 * It enforces each {@code DiaryEntry} to contain a unique {@code Index}.
 */
public class DiaryEntryList {

    private ObservableList<DiaryEntry> diaryEntries;

    DiaryEntryList() {
        diaryEntries = FXCollections.observableArrayList();
    }

    public DiaryEntryList(Collection<DiaryEntry> diaryEntries) {
        this();
        for (DiaryEntry diaryEntry : diaryEntries) {
            addDiaryEntry(diaryEntry);
        }
    }

    public ObservableList<DiaryEntry> getReadOnlyDiaryEntries() {
        return FXCollections.unmodifiableObservableList(diaryEntries);
    }

    public Optional<DiaryEntry> getDiaryEntry(Index index) {
        requireNonNull(index);
        for (DiaryEntry diaryEntry : diaryEntries) {
            if (diaryEntry.getDayIndex().equals(index)) {
                return Optional.of(diaryEntry);
            }
        }

        return Optional.empty();
    }

    public Optional<DiaryEntry> getFirstDiaryEntry() {
        for (DiaryEntry diaryEntry : diaryEntries) {
            return Optional.of(diaryEntry);
        }

        return Optional.empty();
    }

    public void addDiaryEntry(DiaryEntry diaryEntry) {
        requireNonNull(diaryEntry);
        checkArgument(doesClash(diaryEntry), DiaryEntry.MESSAGE_CONSTRAINTS);
        diaryEntries.add(diaryEntry);
    }

    public boolean doesClash(DiaryEntry diaryEntry) {
        return diaryEntries.stream()
                .anyMatch(entry -> {
                    return entry.getDayIndex().equals(diaryEntry.getDayIndex());
                });
    }

    public boolean hasEntry(Index index) {
        return diaryEntries.stream()
                .anyMatch(entry -> {
                    return entry.getDayIndex().equals(index);
                });
    }

}
