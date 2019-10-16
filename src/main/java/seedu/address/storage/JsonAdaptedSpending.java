package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.spending.Address;
import seedu.address.model.spending.Date;
import seedu.address.model.spending.Email;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Spending}.
 */
class JsonAdaptedSpending {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Spending's %s field is missing!";

    private final String name;
    private final String date;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSpending} with the given Spending details.
     */
    @JsonCreator
    public JsonAdaptedSpending(@JsonProperty("name") String name, @JsonProperty("date") String date,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.date = date;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Spending} into this class for Jackson use.
     */
    public JsonAdaptedSpending(Spending source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Spending object into the model's {@code Spending} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Spending.
     */
    public Spending toModelType() throws IllegalValueException {
        final List<Tag> spendingTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            spendingTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(spendingTags);
        return new Spending(modelName, modelDate, modelEmail, modelAddress, modelTags);
    }

}
