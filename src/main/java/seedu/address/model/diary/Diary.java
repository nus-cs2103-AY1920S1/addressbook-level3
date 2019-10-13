package seedu.address.model.diary;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Diary in Duke Cooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class Diary {

    // Identity fields
    private final Name name;

    // Data fields
    private final ArrayList<Page> pages;

    /**
     * Every field must be present and not null.
     */
    public Diary(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.pages = new ArrayList<>();
    }

    /**
     * Overloaded constructor for custom list of pages.
     */
    public Diary(Name name, ArrayList<Page> pages) {
        requireAllNonNull(name);
        this.name = name;
        this.pages = pages;
    }

    public Name getName() {
        return name;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameDiary(Diary otherDiary) {
        if (otherDiary == this) {
            return true;
        }

        return otherDiary != null
                && otherDiary.getName().equals(getName());
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
        return otherDiary.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
