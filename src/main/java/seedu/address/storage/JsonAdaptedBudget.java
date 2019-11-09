package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenseList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field of a budget is missing!";

    private final String name;
    private final String amount;
    private final String amountLeft;
    private final String currency;
    private final String startDate;
    private final String endDate;
    private final JsonSerializableExpenseList expenseList;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("name") String name, @JsonProperty("amount") String amount,
                             @JsonProperty("amountLeft") String amountLeft,
                             @JsonProperty("currency") String currency,
                             @JsonProperty("startDate") String startDate,
                             @JsonProperty("endDate") String endDate,
                             @JsonProperty("expenseList") JsonSerializableExpenseList expenseList) {
        this.name = name;
        this.amount = amount;
        this.amountLeft = amountLeft;
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenseList = expenseList;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        name = source.getName().fullName;
        amount = source.getAmount().value;
        amountLeft = source.getAmountLeft().value;
        currency = source.getCurrency().name;
        startDate = source.getStartDate().rawValue;
        endDate = source.getEndDate().rawValue;
        expenseList = new JsonSerializableExpenseList(source.getExpenseList());
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted budget.
     */
    public Budget toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (amountLeft == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amountLeft)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmountLeft = new Amount(amountLeft);

        if (currency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Currency.class.getSimpleName()));
        }
        if (!Currency.isValidCurrency(currency)) {
            throw new IllegalValueException(Currency.MESSAGE_CONSTRAINTS);
        }
        final Currency modelCurrency = new Currency(currency);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(startDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelStartDate = new Date(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(endDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelEndDate = new Date(endDate);

        if (expenseList == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ExpenseList.class.getSimpleName()));
        }

        return new Budget(modelName, modelAmount, modelAmountLeft, modelCurrency, modelStartDate, modelEndDate,
            new ExpenseList(expenseList.toModelType()));
    }
}
