package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Content;
import seedu.address.model.person.Person;
import seedu.address.model.person.Title;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lecture note's %s field is missing!";

    private final String title;
    private final String content;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        title = source.getTitle().title;
        content = source.getContent().content;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
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

        return new Person(modelTitle, modelContent);
    }
}
