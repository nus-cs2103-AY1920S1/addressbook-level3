package seedu.address.testutil;

import java.util.Set;

import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Note objects.
 */
public class NoteBuilder {

    public static final String DEFAULT_TITLE = "Pipeline Definition";
    public static final String DEFAULT_CONTENT = "Pipelining is a process where a processor simultaneously runs "
            + "multiple instructions";

    private Title title;
    private Content phone;
    private Set<Tag> tags;

    public NoteBuilder() {
        title = new Title(DEFAULT_TITLE);
        phone = new Content(DEFAULT_CONTENT);
        // tags = new HashSet<>();
    }

    /**
     * Initializes the NoteBuilder with the data of {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        title = noteToCopy.getTitle();
        phone = noteToCopy.getContent();
        // tags = new HashSet<>(noteToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Note} that we are building.
     */
    public NoteBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Note} that we are building.
     */
    public NoteBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code Note} that we are building.
     */
    public NoteBuilder withContent(String phone) {
        this.phone = new Content(phone);
        return this;
    }

    public Note build() {
        return new Note(title, phone);
    }

}
