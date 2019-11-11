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
    private boolean isPrivate;


    /**
     * Every field must be present and not null.
     */

    public DiaryEntry(Title title, Date date, Place place, Memory memory) {
        requireAllNonNull(title, date, place, memory);
        this.title = title;
        this.date = date;
        this.place = place;
        this.memory = memory;
        isPrivate = memory.getPrivacy();
    }

    /**
     * Return a copy of this title
     * @return Title
     */
    public Title getTitle() {
        return title.copy();
    }

    /**
     * Return this date
     * @return Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Return a copy of this palce
     * @return Place
     */
    public Place getPlace() {
        return place.copy();
    }

    /**
     * Return a copy of this memory
     * @return Memory
     */
    public Memory getMemory() {
        return memory.copy();
    }

    /**|
     * Return Date in a presentable format
     * @return String
     */

    public String getDateAsString() {
        return DateFormatter.convertToStringPrint(date);
    }

    /**
     * Return Date in a storage format
     * @return String
     */
    public String getDateAsStringtoStore() {
        return DateFormatter.convertToStringStore(date);
    }

    public DiaryEntry copy() {
        return new DiaryEntry(getTitle(),getDate(),getPlace(),getMemory());
    }

    /**
     * Set this memory to private
     */
    public void setPrivate() {
        isPrivate = true;
        this.memory.setPrivate();
    }

    /**
     * Set this memory as unprivate
     */
    public void unPrivate() {
        isPrivate = false;
        this.memory.unPrivate();
    }

    /**
     * Get the privacy status of this memory
     * @return boolean
     */
    public boolean getPrivacy() {
        return isPrivate;
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

        DiaryEntry otherEntry = (DiaryEntry) other;
        return otherEntry.getTitle().equals(getTitle()) &&
                otherEntry.getDateAsStringtoStore().equalsIgnoreCase(getDateAsStringtoStore());
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
                .append(getMemory().toString() + "\n");
        return builder.toString();
    }

}
