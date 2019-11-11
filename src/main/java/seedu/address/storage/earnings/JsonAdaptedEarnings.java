package seedu.address.storage.earnings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Count;
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
    private final String count;

    /**
     * Constructs a {@code JsonAdaptedEarnings} with the given earnings details.
     */
    @JsonCreator
    public JsonAdaptedEarnings(@JsonProperty("date") String date, @JsonProperty("classId") String classId,
                             @JsonProperty("amount") String amount, @JsonProperty("type") String type,
                               @JsonProperty("claim") String claim, @JsonProperty("count") String count) {
        this.date = date;
        this.classId = classId;
        this.amount = amount;
        this.type = type;
        this.claim = claim;
        this.count = count;
    }

    /**
     * Converts a given {@code Earnings} into this class for Jackson use.
     */
    public JsonAdaptedEarnings(Earnings source) {
        date = source.getDate().dateNum;
        classId = source.getClassId().value;
        amount = source.getAmount().amount;
        type = source.getType().type;
        claim = source.getClaim().claimStatus;
        count = source.getCount().count;
    }

    /**
     * Converts this Jackson-friendly adapted earnings object into the model's {@code Earnings} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted earnings.
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

        if (count == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Count.class.getSimpleName()));
        }
        if (!Count.isValidCount(count)) {
            throw new IllegalValueException(Count.MESSAGE_CONSTRAINTS);
        }
        final Count modelCount = new Count(count);

        Earnings earnings = new Earnings(modelDate, modelClassId, modelAmount, modelType);
        earnings.setClaim(modelClaim);
        earnings.setCount(modelCount);

        return earnings;
    }
}
