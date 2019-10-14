package seedu.address.model.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;

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
        checkArgument(!doesClash(diaryEntry), DiaryEntry.MESSAGE_CONSTRAINTS);
        diaryEntries.add(diaryEntry);
    }

    public void setDiaryEntry(DiaryEntry target, DiaryEntry replacement) {
        requireAllNonNull(target, replacement);
        diaryEntries.remove(target);
        addDiaryEntry(replacement);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Diary Entries: ");
        for (DiaryEntry diaryEntry : diaryEntries) {
            builder.append(diaryEntry.toString()).append("\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DiaryEntryList)) {
            return false;
        }

        DiaryEntryList otherEntryList = (DiaryEntryList) obj;

        return diaryEntries.equals(otherEntryList.diaryEntries);
    }

}
