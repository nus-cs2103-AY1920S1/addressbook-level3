package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;

/**
 * Jackson-friendly version of {@link UserTag}.
 */
class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.getTagName();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public UserTag toModelType() throws IllegalValueException {
        if (!UserTag.isValidTagName(tagName)) {
            throw new IllegalValueException(UserTag.MESSAGE_CONSTRAINTS);
        }
        return new UserTag(tagName);
    }

}
