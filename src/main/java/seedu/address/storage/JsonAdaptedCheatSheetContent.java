package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Jackson-friendly version of {@link Content}.
 */
class JsonAdaptedCheatSheetContent {

    @JsonProperty("content")
    private final String content;

    @JsonProperty("content_tags")
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContent} with the given {@code content}.
     */
    @JsonCreator
    public JsonAdaptedCheatSheetContent(@JsonProperty("content") String content, @JsonProperty("content_tags") Set<Tag> tags) {
        this.content = content;

        for (Tag tag: tags) {
            this.tags.add(new JsonAdaptedTag(tag));
        }
    }

    /**
     * Converts a given {@code Content} into this class for Jackson use.
     */
    public JsonAdaptedCheatSheetContent(Content source) {
        this.content = source.content;
        for (Tag tag: source.getTags()) {
            this.tags.add(new JsonAdaptedTag(tag));
        }
    }

    //@JsonValue
    public String getContent() {
        return content;
    }

    //@JsonValue
    public String getTag() {
        return tags.toString();
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


        final List<Tag> contentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            contentTags.add(tag.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(contentTags);

        return new Content(content, modelTags);
    }

}
