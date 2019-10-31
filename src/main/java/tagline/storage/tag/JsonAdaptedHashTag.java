package tagline.storage.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.tag.HashTag;

/**
 * Jackson-friendly version of {@link HashTag}.
 */
@JsonTypeName("hashTag")
public class JsonAdaptedHashTag extends JsonAdaptedTag {
    private String value;

    @JsonCreator
    public JsonAdaptedHashTag(@JsonProperty("value") String value) {
        super();
        this.value = value;
    }

    public JsonAdaptedHashTag(HashTag source) {
        super();
        this.value = source.getValue().toString();
    }

    @Override
    public HashTag toModelType() throws IllegalValueException {
        return new HashTag(value);
    }
}
