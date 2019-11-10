package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Expense}.
 */
class JsonAdaptedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field of an expense is missing!";

    private final String name;
    private final String amount;
    private final JsonAdaptedCurrency currency;
    private final String date;
    private final String tag;

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("name") String name, @JsonProperty("amount") String amount,
                              @JsonProperty("currency") JsonAdaptedCurrency currency, @JsonProperty("date") String date,
                              @JsonProperty("tag") String tag) {
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.tag = tag;
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        name = source.getName().fullName;
        amount = source.getAmount().value;
        currency = new JsonAdaptedCurrency(source.getCurrency());
        date = source.getDate().rawValue;
        tag = source.getTag().tagName;
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {

        if (currency == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Currency.class.getSimpleName()));
        }
        if (currency.toModelType() == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Currency.class.getSimpleName()));
        }
        final Currency modelCurrency = currency.toModelType();

        if (name == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (amount == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (date == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (tag == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName()));
        }
        if (!Tag.isValidTagName(tag)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        final Tag modelTag = new Tag(tag);

        return new Expense(modelName, modelAmount, modelCurrency, modelDate, modelTag);
    }
}
