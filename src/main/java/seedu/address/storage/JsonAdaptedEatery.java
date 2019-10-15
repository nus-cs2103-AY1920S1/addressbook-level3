package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Eatery}.
 */
class JsonAdaptedEatery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Eatery's %s field is missing!";

    private final String name;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEatery} with the given eatery details.
     */
    @JsonCreator
    public JsonAdaptedEatery(@JsonProperty("name") String name,
                             @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Eatery} into this class for Jackson use.
     */
    public JsonAdaptedEatery(Eatery source) {
        name = source.getName().fullName;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted eatery object into the model's {@code Eatery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted eatery.
     */
    public Eatery toModelType() throws IllegalValueException {
        final List<Tag> eateryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            eateryTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(eateryTags);
        return new Eatery(modelName, modelAddress, modelTags);
    }

}
