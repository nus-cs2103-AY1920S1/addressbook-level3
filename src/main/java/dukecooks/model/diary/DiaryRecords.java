package dukecooks.model.diary;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.UniqueDiaryList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the diary level
 * Duplicates are not allowed (by .isSameDiary comparison)
 */
public class DiaryRecords implements ReadOnlyDiary {

    private final UniqueDiaryList diaries;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        diaries = new UniqueDiaryList();
    }

    public DiaryRecords() {}

    /**
     * Creates a DiaryRecords using the Persons in the {@code toBeCopied}
     */
    public DiaryRecords(ReadOnlyDiary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the diary list with {@code diaries}.
     * {@code diaries} must not contain duplicate diaries.
     */
    public void setDiaries(List<Diary> diaries) {
        this.diaries.setDiaries(diaries);
    }

    /**
     * Resets the existing data of this {@code DiaryRecords} with {@code newData}.
     */
    public void resetData(ReadOnlyDiary newData) {
        requireNonNull(newData);

        setDiaries(newData.getDiaryList());
    }

    //// diary-level operations

    /**
     * Returns true if a diary with the same identity as {@code diary} exists in Duke Cooks.
     */
    public boolean hasDiary(Diary diary) {
        requireNonNull(diary);
        return diaries.contains(diary);
    }

    /**
     * Adds a diary to Duke Cooks.
     * The diary must not already exist in Duke Cooks.
     */
    public void addDiary(Diary p) {
        diaries.add(p);
    }

    /**
     * Replaces the given diary {@code target} in the list with {@code editedDiary}.
     * {@code target} must exist in Duke Cooks.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in Duke Cooks.
     */
    public void setDiary(Diary target, Diary editedDiary) {
        requireNonNull(editedDiary);

        diaries.setDiary(target, editedDiary);
    }

    /**
     * Removes {@code key} from this {@code DiaryRecords}.
     * {@code key} must exist in Duke Cooks.
     */
    public void removeDiary(Diary key) {
        diaries.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return diaries.asUnmodifiableObservableList().size() + " diaries";
        // TODO: refine later
    }

    @Override
    public ObservableList<Diary> getDiaryList() {
        return diaries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryRecords // instanceof handles nulls
                && diaries.equals(((DiaryRecords) other).diaries));
    }

    @Override
    public int hashCode() {
        return diaries.hashCode();
    }
}
