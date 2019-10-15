package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.ApprovedClaim;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Description;
import seedu.address.model.claim.PendingClaim;
import seedu.address.model.claim.RejectedClaim;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link seedu.address.model.contact.Contact}.
 */
class JsonAdaptedClaim {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "FinSec's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedClaim.class);

    private final String description;
    private final String amount;
    private final String date;
    private final String name;
    private final String phone;
    private final String status;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedClaim(@JsonProperty("description") String description, @JsonProperty("amount") String amount,
                            @JsonProperty("date") String date, @JsonProperty("name") String name,
                            @JsonProperty("phone") String phone, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                            @JsonProperty("status") String status) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.name = name;
        this.phone = phone;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.status = status;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedClaim(Claim source) {
        description = source.getDescription().text;
        amount = source.getAmount().value;
        date = source.getDate().text;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted claim object into the model's {@code FinSec} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted claim.
     */
    public Claim toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        switch (status) {
        case "PENDING":
            return new PendingClaim(modelDescription, modelAmount, modelDate, modelName, modelPhone, modelTags);
        case "APPROVED":
            return new ApprovedClaim(modelDescription, modelAmount, modelDate, modelName, modelPhone, modelTags);
        case "REJECTED":
            return new RejectedClaim(modelDescription, modelAmount, modelDate, modelName, modelPhone, modelTags);
        default:
            logger.warning("Invalid claim status: " + status);
            throw new IllegalValueException("Status should only be PENDING, REJECTED or APPROVED");
        }
    }

}
