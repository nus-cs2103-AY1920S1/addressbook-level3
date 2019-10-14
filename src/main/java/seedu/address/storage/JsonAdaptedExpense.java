package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;

/**
 * Jackson-friendly version of {@link Expense}.
 */
class JsonAdaptedExpense {

    private final int personId;
    private final double amount;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("personId") int personId, @JsonProperty("amount") double amount,
                              @JsonProperty("description") String description) {
        this.personId = personId;
        this.amount = amount;
        this.description = description;
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        personId = source.getPersonId();
        amount = source.getAmount().value;
        description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount amount = new Amount(this.amount);

        return new Expense(personId, amount, description);
    }
}
