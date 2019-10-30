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

    static final String MESSAGE_DUPLICATE_ENTRIES = "You cannot have multiple diary entries for a one day. ";

    static final String MESSAGE_DAY_MISSING = "Attempted to execute an operation on a diary entry with day"
            + "index %1$d but it was not found in the list.";

    /**
     * The comparator used for {@link DiaryEntry}s, which compares the day indexes of any two diary entries.
     */
    private static final Comparator<DiaryEntry> DIARY_ENTRY_COMPARATOR =
            Comparator.comparingInt(DiaryEntry::getDayNumber);

    private final SortedList<DiaryEntry> diaryEntrySortedList;
    private final ObservableList<DiaryEntry> diaryEntryObservableList;

    /**
     * Constructs a new {@link DiaryEntryList} with a {@code diaryEntrySortedList} wrapped around
     * an empty {@code diaryEntryObservableList}, using the {@code DIARY_ENTRY_COMPARATOR}.
     */
    DiaryEntryList() {
        diaryEntryObservableList = FXCollections.observableArrayList();
        diaryEntrySortedList = new SortedList<DiaryEntry>(diaryEntryObservableList, DIARY_ENTRY_COMPARATOR);
    }

    /**
     * Constructs a new {@link DiaryEntryList} with a {@code diaryEntrySortedList} wrapped around
     * a {@code diaryEntryObservableList} using the {@code DIARY_ENTRY_COMPARATOR}.
     * The {@code diaryEntryObservableList} is initialized with the {@link DiaryEntry}s in the
     * supplied {@link Collection} {@code diaryEntries}.
     *
     * @param diaryEntries The {@link Collection} of {@link DiaryEntry}s to initialize the
     *                     {@code diaryEntryObservableList} with.
     * @throws IllegalArgumentException If there are {@link DiaryEntry}s in the specified collection
     *                                  with the same day indexes.
     */
    public DiaryEntryList(Collection<DiaryEntry> diaryEntries) throws IllegalArgumentException {
        this();
        diaryEntries.forEach(this::addDiaryEntry);
    }

    /**
     * Retrieves a unmodifiable {@code diaryEntrySortedList}.
     *
     * @return The wrapper, unmodifiable {@link SortedList} {@code diaryEntrySortedList}.
     */
    public SortedList<DiaryEntry> getDiaryEntrySortedList() {
        return diaryEntrySortedList;
    }

    /**
     * Retrieves the {@link DiaryEntry} with the specified {@link Index}, returning an empty
     * {@link Optional} if none is found.
     *
     * @param index The {@link Index} to check for.
     * @return The {@link DiaryEntry} with the corresponding {@link Index}.
     */
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
    Optional<DiaryEntry> getFirstDiaryEntry() {
        return diaryEntrySortedList.size() == 0
                ? Optional.empty()
                : Optional.of(diaryEntrySortedList.get(0));
    }

    /**
     * Adds the specified {@link DiaryEntry} to the {@code diaryEntryObservableList}.
     *
     * @param diaryEntry The {@link DiaryEntry} to add.
     * @throws IllegalArgumentException If the supplied {@code diaryEntry} has a {@code dayIndex} that clashes
     *                                  with another {@link DiaryEntry} already in the {@code diaryEntryObservableList}.
     */
    void addDiaryEntry(DiaryEntry diaryEntry) {
        requireNonNull(diaryEntry);
        checkArgument(!doesClash(diaryEntry), MESSAGE_DUPLICATE_ENTRIES);

        diaryEntryObservableList.add(diaryEntry);
    }

    /**
     * Replaces the specified {@link DiaryEntry} {@code target} with the {@code replacement}.
     *
     * @param target      The {@link DiaryEntry} to replace.
     * @param replacement The {@link DiaryEntry} to add.
     * @throws IllegalArgumentException If the supplied {@code diaryEntry} has a {@code dayIndex} that clashes
     *                                  with another {@link DiaryEntry} already in the {@code diaryEntryObservableList},
     *                                  or the {@code target} DiaryEntry does not exist in the list.
     */
    void setDiaryEntry(DiaryEntry target, DiaryEntry replacement) {
        requireAllNonNull(target, replacement);
        boolean isRemoved = diaryEntryObservableList.remove(target);
        checkArgument(isRemoved, MESSAGE_DAY_MISSING);
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
                .anyMatch(entry -> entry.getDayNumber() == diaryEntry.getDayNumber());
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
