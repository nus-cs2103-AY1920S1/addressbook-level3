package seedu.address.diaryfeature.model.diaryEntry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;


/**
 * Represents a DiaryEntry
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DiaryEntry {

    // Identity fields
    private final Title title;
    private final Date date;
    private final Place place;
    private final Memory memory;


    /**
     * Every field must be present and not null.
     */

    public DiaryEntry(Title title, Date date, Place place, Memory memory) {
        requireAllNonNull(title, date);
        this.title = title;
        this.date = date;
        this.place = place;
        this.memory = memory;
    }

    public Title getTitle() {
        return title;
    }

    public String getDateAsString() {
        return DateFormatter.convertToStringPrint(date);
    }

    public String getDateAsStringtoStore() {
        return DateFormatter.convertToStringStore(date);
    }

    public Date getDate() {
        return date;
    }

    public Place getPlace() {
        return place;
    }

    public Memory getMemory() {
        return memory;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEntry(DiaryEntry otherDiaryEntry) {
        if (otherDiaryEntry == this) {
            return true;
        }
        return otherDiaryEntry != null
                && otherDiaryEntry.getTitle().equals(getTitle());
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

        if (!(other instanceof DiaryEntry)) {
            return false;
        }

        DiaryEntry otherPerson = (DiaryEntry) other;
        return otherPerson.getTitle().equals(getTitle())
                && otherPerson.getDate().equals(getDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title: ")
                .append(getTitle() + "\n")
                .append("Date: ")
                .append(getDateAsString() + "\n")
                .append("Place: ")
                .append(getPlace() + "\n")
                .append("Memory: ")
                .append(getMemory() + "\n");
        return builder.toString();
    }

}
