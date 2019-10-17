package tagline.storage.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.contact.ContactId;
import tagline.model.tag.ContactTag;
import tagline.model.tag.Tag;
import tagline.model.tag.Tag.TagType;
import tagline.model.tag.TagId;

/**
 * Jackson-friendly version of {@link Tag}.
 */
public class JsonAdaptedTag {

    private final String tagType;
    private final String tagId;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagType") String tagType, @JsonProperty("tagId") String tagId) {
        this.tagType = tagType;
        this.tagId = tagId;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagType = source.tagType.name();
        tagId = source.tagId.toString();
    }

    @JsonValue
    public String getTagType() {
        return tagType;
    }

    @JsonValue
    public String getTagId() {
        return tagId;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        final String str = TagType.CONTACT_TAG.name();
        switch (tagType) {
        case "contact":
            return new ContactTag(new TagId(tagId), new ContactId("123"));
        default:
            throw new IllegalStateException("Unexpected value: " + tagType);
        }
    }
}
