package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.note.Content;
import seedu.address.model.note.Description;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Person objects.
 */
public class NoteBuilder {

    public static final String DEFAULT_TITLE = "Secret Diary";
    public static final String DEFAULT_DESCRIPTION = "Work";
    public static final String DEFAULT_CONTENT = "secret documents";

    private Title title;
    private Description description;
    private Content content;
    private Set<Tag> tags;

    public NoteBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        content = new Content(DEFAULT_CONTENT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        title = noteToCopy.getTitle();
        description = noteToCopy.getDescription();
        content = noteToCopy.getContent();
        tags = new HashSet<>(noteToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public NoteBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public NoteBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public NoteBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public NoteBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    public Note build() {
        return new Note(title, description, tags, content);
    }

}
