package seedu.address.storage.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.genre.Genre;

/**
 * Jackson-friendly version of {@link Genre}.
 */
public class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Genre} into this class for Jackson use.
     */
    public JsonAdaptedTag(Genre source) {
        tagName = source.tagName;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted genre object into the model's {@code Genre} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted genre.
     */
    public Genre toModelType() throws IllegalValueException {
        if (!Genre.isValidGenreName(tagName)) {
            throw new IllegalValueException(Genre.MESSAGE_CONSTRAINTS);
        }
        return new Genre(tagName);
    }

}
