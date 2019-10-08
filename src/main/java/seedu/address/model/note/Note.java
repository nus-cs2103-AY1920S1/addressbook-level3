package seedu.address.model.note;

/**
 * Represents a note in the note list.
 */
public class Note {

    protected String note;
    protected String description;

    /**
     * Creates a new question.
     *
     * @param note to set.
     * @param description to the question.
     */
    public Note(String note, String description) {
        this.note = note;
        this.description = description;
    }

    /**
     * Returns the note.
     *
     * @return Note string
     */
    public String getNote() {
        return this.note;
    }

    /**
     * Returns the description.
     *
     * @return Description string
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the note.
     *
     * @param note to set.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Sets the description.
     *
     * @param description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return note;
    }
}
