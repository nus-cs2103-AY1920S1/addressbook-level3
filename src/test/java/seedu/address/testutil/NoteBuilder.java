package seedu.address.testutil;

import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

/**
 * A utility class to help with building Note objects.
 */
public class NoteBuilder {
    public static final String DEFAULT_TITLE = "A lecture note";
    public static final String DEFAULT_CONTENT = "Lorem ipsum dolor sit amet";

    private Title title;
    private Content content;

    public NoteBuilder() {
        title = new Title(DEFAULT_TITLE);
        content = new Content(DEFAULT_CONTENT);
    }

    /**
     * Initializes the NoteBuilder with the data of {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        title = noteToCopy.getTitle();
        content = noteToCopy.getContent();
    }

    /**
     * Sets the {@code Title} of the {@code Note} that we are building.
     */
    public NoteBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code Note} that we are building.
     */
    public NoteBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    public Note build() {
        return new Note(title, content);
    }
}
