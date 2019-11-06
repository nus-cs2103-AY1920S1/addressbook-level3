package seedu.address.storage.earnings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Type;

/**
 * Jackson-friendly version of {@link Earnings}.
 */
public class JsonAdaptedEarnings {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String date;
    private final String classId;
    private final String amount;
    private final String type;
    private final String claim;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEarnings(@JsonProperty("date") String date, @JsonProperty("classId") String classId,
                             @JsonProperty("amount") String amount, @JsonProperty("type") String type,
                               @JsonProperty("claim") String claim) {
        this.date = date;
        this.classId = classId;
        this.amount = amount;
        this.type = type;
        this.claim = claim;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedEarnings(Earnings source) {
        date = source.getDate().dateNum;
        classId = source.getClassId().value;
        amount = source.getAmount().amount;
        type = source.getType().type;
        claim = source.getClaim().claimStatus;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Earnings toModelType() throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDateNum(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (classId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ClassId.class.getSimpleName()));
        }
        if (!ClassId.isValidClassId(classId)) {
            throw new IllegalValueException(ClassId.MESSAGE_CONSTRAINTS);
        }
        final ClassId modelClassId = new ClassId(classId);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (claim == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Claim.class.getSimpleName()));
        }
        if (!Claim.isValidClaim(claim)) {
            throw new IllegalValueException(Claim.MESSAGE_CONSTRAINTS);
        }
        final Claim modelClaim = new Claim(claim);

        Earnings earnings = new Earnings(modelDate, modelClassId, modelAmount, modelType);
        earnings.setClaim(modelClaim);

        return earnings;
    }
}
