package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String tagColor;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("name") String tagName,
                          @JsonProperty("color") String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        this.tagName = source.getName();
        this.tagColor = source.getColor();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName) || tagName.equals("#forRefresh#")) {
            throw new IllegalValueException(Tag.MESSAGE_NAME_CONSTRAINTS);
        }
        if (!Tag.isValidTagColor(tagColor)) {
            throw new IllegalValueException(Tag.MESSAGE_COLOR_CONSTRAINTS);
        }
        return new Tag(tagName, tagColor);
    }

}
