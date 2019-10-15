package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.cheatsheet.Content;

/**
 * Jackson-friendly version of {@link Content}.
 */
class JsonAdaptedContent {

    private final String content;

    /**
     * Constructs a {@code JsonAdaptedContent} with the given {@code content}.
     */
    @JsonCreator
    public JsonAdaptedContent(String content) {
        this.content = content;
    }

    /**
     * Converts a given {@code Content} into this class for Jackson use.
     */
    public JsonAdaptedContent(Content source) {
        content = source.content;
    }

    @JsonValue
    public String getContent() {
        return content;
    }

    /**
     * Converts this Jackson-friendly adapted content object into the model's {@code Content} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted content.
     */
    public Content toModelType() throws IllegalValueException {
        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }
        return new Content(content);
    }

}
