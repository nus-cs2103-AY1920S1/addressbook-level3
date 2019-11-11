package seedu.address.storage.calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.tag.TaskTag;

/**
 * Jackson-friendly version of {@link TaskTag}.
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
     * Converts a given {@code TaskTag} into this class for Jackson use.
     */
    public JsonAdaptedTag(TaskTag source) {
        tagName = source.tagName;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the calendarModel's {@code TaskTag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public TaskTag toModelType() throws IllegalValueException {
        if (!TaskTag.isValidTagName(tagName)) {
            throw new IllegalValueException(TaskTag.MESSAGE_CONSTRAINTS);
        }
        return new TaskTag(tagName);
    }

}
