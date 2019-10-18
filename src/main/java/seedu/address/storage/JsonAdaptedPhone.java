package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Phone}.
 */
class JsonAdaptedPhone {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Phone's %s field is missing!";

    // Identity fields
    private final String identityNumber;
    private final String serialNumber;

    // Data fields
    private final String phoneName;
    private final String brand;
    private final String capacity;
    private final String colour;
    private final String cost;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPhone} with the given phone details.
     */
    @JsonCreator
    public JsonAdaptedPhone(@JsonProperty("identityNumber") String identityNumber,
                               @JsonProperty("serialNumber") String serialNumber,
                               @JsonProperty("phoneName") String phoneName, @JsonProperty("brand") String brand,
                               @JsonProperty("capacity") String capacity, @JsonProperty("colour") String colour,
                               @JsonProperty("cost") String cost,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.identityNumber = identityNumber;
        this.serialNumber = serialNumber;
        this.phoneName = phoneName;
        this.brand = brand;
        this.capacity = capacity;
        this.colour = colour;
        this.cost = cost;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Phone} into this class for Jackson use.
     */
    public JsonAdaptedPhone(Phone source) {
        identityNumber = source.getIdentityNumber().value;
        serialNumber = source.getSerialNumber().value;
        phoneName = source.getPhoneName().fullName;
        brand = source.getBrand().value;
        capacity = source.getCapacity().value.split("GB")[0];
        colour = source.getColour().value;
        cost = source.getCost().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted phone object into the model's {@code Phone} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted phone.
     */
    public Phone toModelType() throws IllegalValueException {
        final List<Tag> phoneTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            phoneTags.add(tag.toModelType());
        }

        if (identityNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentityNumber.class.getSimpleName()));
        }
        if (!IdentityNumber.isValidIdentityNumber(identityNumber)) {
            throw new IllegalValueException(IdentityNumber.MESSAGE_CONSTRAINTS);
        }
        final IdentityNumber modelIdentityNumber = new IdentityNumber(identityNumber);

        if (serialNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SerialNumber.class.getSimpleName()));
        }
        if (!SerialNumber.isValidSerialNumber(serialNumber)) {
            throw new IllegalValueException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        final SerialNumber modelSerialNumber = new SerialNumber(serialNumber);

        if (phoneName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PhoneName.class.getSimpleName()));
        }
        if (!PhoneName.isValidPhoneName(phoneName)) {
            throw new IllegalValueException(PhoneName.MESSAGE_CONSTRAINTS);
        }
        final PhoneName modelPhoneName = new PhoneName(phoneName);

        if (brand == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Brand.class.getSimpleName()));
        }
        if (!Brand.isValidBrand(brand)) {
            throw new IllegalValueException(Brand.MESSAGE_CONSTRAINTS);
        }
        final Brand modelBrand = new Brand(brand);

        if (capacity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Capacity.class.getSimpleName()));
        }
        if (!Capacity.isValidCapacity(capacity)) {
            throw new IllegalValueException(Capacity.MESSAGE_CONSTRAINTS);
        }

        Capacity temp;

        switch (capacity) {
        case "8": temp = Capacity.SIZE_8GB;
                break;
        case "16": temp = Capacity.SIZE_16GB;
                break;
        case "32": temp = Capacity.SIZE_32GB;
                break;
        case "64": temp = Capacity.SIZE_64GB;
                break;
        case "128": temp = Capacity.SIZE_128GB;
                break;
        case "256": temp = Capacity.SIZE_256GB;
                break;
        case "512": temp = Capacity.SIZE_512GB;
                break;
        default: temp = Capacity.SIZE_1024GB;
                break;
        }

        final Capacity modelCapacity = temp;

        if (colour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Colour.class.getSimpleName()));
        }
        if (!Colour.isValidColour(colour)) {
            throw new IllegalValueException(Colour.MESSAGE_CONSTRAINTS);
        }
        final Colour modelColour = new Colour(colour);

        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Cost modelCost = new Cost(cost);

        final Set<Tag> modelTags = new HashSet<>(phoneTags);
        return new Phone(modelIdentityNumber, modelSerialNumber, modelPhoneName, modelBrand, modelCapacity,
                modelColour, modelCost, modelTags);
    }

}
