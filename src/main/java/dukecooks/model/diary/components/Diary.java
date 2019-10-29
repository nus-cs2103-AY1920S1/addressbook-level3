package dukecooks.model.diary.components;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Diary in Duke Cooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class Diary {

    // Identity fields
    private final DiaryName diaryName;

    // Data fields
    private final ObservableList<Page> pages;

    /**
     * Every field must be present and not null.
     */
    public Diary(DiaryName diaryName) {
        requireAllNonNull(diaryName);
        this.diaryName = diaryName;
        this.pages = FXCollections.observableArrayList();
    }

    /**
     * Overloaded constructor for custom list of pages.
     */
    public Diary(DiaryName diaryName, ObservableList<Page> pages) {
        requireAllNonNull(diaryName);
        this.diaryName = diaryName;
        this.pages = pages;
    }

    public DiaryName getDiaryName() {
        return diaryName;
    }

    public ObservableList<Page> getPages() {
        return pages;
    }


    /**
     * Returns true if both persons of the same diaryName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameDiary(Diary otherDiary) {
        if (otherDiary == this) {
            return true;
        }

        return otherDiary != null
                && otherDiary.getDiaryName().equals(getDiaryName());
    }

    /**
     * Returns true if specified Page exists in diary.
     */
    public boolean hasPage(Page page) {
        requireNonNull(page);
        return pages.contains(page);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Diary)) {
            return false;
        }

        Diary otherDiary = (Diary) other;
        return otherDiary.getDiaryName().equals(getDiaryName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(diaryName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDiaryName());
        return builder.toString();
    }

}
