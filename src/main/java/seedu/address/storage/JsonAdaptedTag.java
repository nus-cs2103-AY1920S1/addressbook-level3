package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.PriorityTagType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;

/**
 * Jackson-friendly version of {@link UserTag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private boolean isPriority = false;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagName") String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.getTagName();
        if (source.isPriority()) {
            isPriority = true;
        }
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (isPriority) {
            String name = tagName.replace("PRIORITY.", "");
            if (!PriorityTag.isValidTagName(name)) {
                throw new IllegalValueException(PriorityTag.MESSAGE_CONSTRAINTS);
            }
            return new PriorityTag(PriorityTagType.valueOf(name));
        } else {
            if (!UserTag.isValidTagName(tagName)) {
                throw new IllegalValueException(UserTag.MESSAGE_CONSTRAINTS);
            }
            return new UserTag(tagName);
        }
    }
}
