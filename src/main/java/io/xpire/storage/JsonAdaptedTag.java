package io.xpire.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.commons.util.StringUtil;
import io.xpire.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag implements JsonAdapted {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = StringUtil.convertToSentenceCase(tagName);
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        this.tagName = StringUtil.convertToSentenceCase(source.getTagName());
    }

    @JsonValue
    public String getTagName() {
        return this.tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    @Override
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(this.tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(this.tagName);
    }

}
