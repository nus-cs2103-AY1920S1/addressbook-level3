package seedu.address.model.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import seedu.address.commons.core.index.Index;

/**
 * Abstraction of a list of {@link DiaryEntry}s.
 * It is backed by an {@link SortedList} wrapped over an {@link ObservableList} to provide
 * custom ordering based on the {@code dayIndex} of the {@link DiaryEntry}.
 * It enforces each {@link DiaryEntry} to contain a unique {@link Index}.
 */
public class DiaryEntryList {

    private SortedList<DiaryEntry> diaryEntrySortedList;
    private ObservableList<DiaryEntry> diaryEntryObservableList;

    DiaryEntryList() {
        diaryEntryObservableList = FXCollections.observableArrayList();
        diaryEntrySortedList = new SortedList<DiaryEntry>(diaryEntryObservableList,
                Comparator.comparingInt(entry -> entry.getDayIndex().getZeroBased()));
    }

    public DiaryEntryList(Collection<DiaryEntry> diaryEntries) {
        this();
        for (DiaryEntry diaryEntry : diaryEntries) {
            addDiaryEntry(diaryEntry);
        }
    }

    public ObservableList<DiaryEntry> getReadOnlyDiaryEntries() {
        return FXCollections.unmodifiableObservableList(diaryEntrySortedList);
    }

    public Optional<DiaryEntry> getDiaryEntry(Index index) {
        requireNonNull(index);
        for (DiaryEntry diaryEntry : diaryEntrySortedList) {
            if (diaryEntry.getDayIndex().equals(index)) {
                return Optional.of(diaryEntry);
            }
        }

        return Optional.empty();
    }

    /**
     * Returns an {@code Optional} of the first diary entry ordered by the {@code dayIndex} of the {@link DiaryEntry}s.
     *
     * @return The first {@code DiaryEntry} in the {@code DiaryEntryList}.
     */
    public Optional<DiaryEntry> getFirstDiaryEntry() {
        return diaryEntrySortedList.size() == 0
                ? Optional.empty()
                : Optional.of(diaryEntrySortedList.get(0));
    }

    /**
     * Adds the specified {@link DiaryEntry} to the {@code diaryEntryObservableList}.
     *
     * @param diaryEntry
     */
    public void addDiaryEntry(DiaryEntry diaryEntry) {
        requireNonNull(diaryEntry);
        checkArgument(!doesClash(diaryEntry), DiaryEntry.MESSAGE_CONSTRAINTS);
        diaryEntryObservableList.add(diaryEntry);
    }

    void setDiaryEntry(DiaryEntry target, DiaryEntry replacement) {
        requireAllNonNull(target, replacement);
        diaryEntryObservableList.remove(target);
        addDiaryEntry(replacement);
    }

    /**
     * Checks whether the given {@link DiaryEntry} has an {@link Index} {@code dayIndex} that is the same
     * as any other {@link DiaryEntry} currently in {@code diaryEntryObservableList}.
     *
     * @param diaryEntry The {@link DiaryEntry} to check.
     * @return True if there are clashing {@link DiaryEntry}s.
     */
    private boolean doesClash(DiaryEntry diaryEntry) {
        return diaryEntryObservableList.stream()
                .anyMatch(entry -> {
                    return entry.getDayIndex().equals(diaryEntry.getDayIndex());
                });
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Diary Entries: ");
        for (DiaryEntry diaryEntry : diaryEntrySortedList) {
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

        return diaryEntryObservableList.equals(otherEntryList.diaryEntryObservableList);
    }

}
