package seedu.address.storage.accommodation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedTag;
import seedu.address.storage.contact.JsonAdaptedContact;

/**
 * Jackson-friendly version of {@link Contact}.
 */
public class JsonAdaptedAccommodation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Accommodation's %s field is missing!";

    private final String name;
    private final String address;
    private final JsonAdaptedContact contact;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAccommodation} with the given accommodations details.
     */
    @JsonCreator
    public JsonAdaptedAccommodation(@JsonProperty("name") String name, @JsonProperty("address") String address,
            @JsonProperty("contact") JsonAdaptedContact contact, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Accommodation} into this class for Jackson use.
     */
    public JsonAdaptedAccommodation(Accommodation source) {
        name = source.getName().toString();
        address = source.getAddress().toString();
        contact = source.getContact().isPresent() ? new JsonAdaptedContact(source.getContact().get()) : null;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted contacts object into the model's {@code Accommodation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contacts.
     */
    public Accommodation toModelType() throws IllegalValueException {
        final List<Tag> accommodationTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            accommodationTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (address != null && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = address != null ? new Address(address) : null;

        final Contact modelContact = (contact == null) ? null : contact.toModelType();

        final Set<Tag> modelTags = new HashSet<>(accommodationTags);
        return new Accommodation(modelName, modelAddress, modelContact, modelTags);
    }

}
