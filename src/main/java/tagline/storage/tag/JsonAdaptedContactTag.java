package tagline.storage.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.contact.ContactId;
import tagline.model.tag.ContactTag;

/**
 * Jackson-friendly version of {@link ContactTag}.
 */
@JsonTypeName("contactTag")
public class JsonAdaptedContactTag extends JsonAdaptedTag {
    private String contactId;

    @JsonCreator
    public JsonAdaptedContactTag(@JsonProperty("contactId") String contactId) {
        super();
        this.contactId = contactId;
    }

    public JsonAdaptedContactTag(ContactTag source) {
        super();
        this.contactId = source.getContactId().value.toString();
    }

    @Override
    public ContactTag toModelType() throws IllegalValueException {
        return new ContactTag(new ContactId(contactId));
    }
}
