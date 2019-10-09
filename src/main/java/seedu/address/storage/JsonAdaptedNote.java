package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

/**
 * Jackson-friendly version of {@link Note}.
 */
class JsonAdaptedNote {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lecture note's %s field is missing!";

    private final String title;
    private final String content;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given lecture note details.
     */
    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Converts a given {@code Note} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        title = source.getTitle().title;
        content = source.getContent().content;
    }

    /**
     * Converts this Jackson-friendly adapted lecture note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Note toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }
        final Content modelContent = new Content(content);

        return new Note(modelTitle, modelContent);
    }
}
