package seedu.address.storage.earnings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Module;

/**
 * Jackson-friendly version of {@link Earnings}.
 */
public class JsonAdaptedEarnings {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String date;
    private final String module;
    private final String amount;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEarnings(@JsonProperty("date") String date, @JsonProperty("module") String module,
                             @JsonProperty("amount") String amount) {
        this.date = date;
        this.module = module;
        this.amount = amount;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedEarnings(Earnings source) {
        date = source.getDate().dateNum;
        module = source.getModule().moduleName;
        amount = source.getAmount().amount;
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

        if (module == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Module.class.getSimpleName()));
        }
        if (!Module.isValidModuleName(module)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS);
        }
        final Module modelModule = new Module(module);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        return new Earnings(modelDate, modelModule, modelAmount);
    }
}
