package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.tag.Tag;

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
    public JsonAdaptedCheatSheetContent(
            @JsonProperty("content") String content,
            @JsonProperty("content_tags") List<JsonAdaptedTag> tags) {
        this.content = content;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Content} into this class for Jackson use.
     */
    public JsonAdaptedCheatSheetContent(Content source) {
        this.content = source.content;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
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
