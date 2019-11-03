package seedu.address.testutil.note;

import seedu.address.model.note.Note;
import seedu.address.model.note.Priority;

/**
 * A utility class to help with building Note objects.
 */
public class NoteBuilder {

    public static final String DEFAULT_TITLE = "Sunday Afternoon";
    public static final String DEFAULT_DESCRIPTION = "Parent Teacher Meeting";
    public static final Priority DEFAULT_PRIORITY = Priority.UNMARKED;

    private String note;
    private String desc;
    private Priority priority;

    public NoteBuilder() {
        note = DEFAULT_TITLE;
        desc = DEFAULT_DESCRIPTION;
        priority = DEFAULT_PRIORITY;
    }

    /**
     * Initializes the NoteBuilder with the data of {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        note = noteToCopy.getNote();
        desc = noteToCopy.getDescription();
        priority = noteToCopy.getPriority();
    }

    /**
     * Sets the {@code note} of the {@code Note} that we are building.
     */
    public NoteBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    /**
     * Sets the {@code desc} of the {@code Note} that we are building.
     */
    public NoteBuilder withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    /**
     * Sets the {@code priority} of the {@code Note} that we are building.
     */
    public NoteBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Note build() {
        return new Note(note, desc, priority);
    }
}
