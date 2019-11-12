package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteFragment;
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
    public static final String EXTRA_CONTENT = "Pipelining is a /* C/process TAG/important */ where a /* C/processor "
            + "TAG/cs2100 TAG/cheatsheet */ simultaneously runs multiple instructions. tag/cs";


    private Title title;
    private Content content;
    private Set<Tag> tags;

    public NoteBuilder() {
        title = new Title(DEFAULT_TITLE);
        content = new Content(DEFAULT_CONTENT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the NoteBuilder with the data of {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        title = noteToCopy.getTitle();
        content = noteToCopy.getContent();
        tags = new HashSet<>(noteToCopy.getTags());
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
    public NoteBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    public Note build() {
        return new Note(title, content, tags);
    }

    public NoteFragment buildFragment() {
        return new NoteFragment(title, content, tags);
    }

}
