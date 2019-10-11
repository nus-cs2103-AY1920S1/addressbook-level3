package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Expense}.
 */
class JsonAdaptedExpense {

    private final JsonAdaptedPerson person;
    private final double amount;

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("person") JsonAdaptedPerson person, @JsonProperty("amount") double amount) {
        this.person = person;
        this.amount = amount;
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        person = new JsonAdaptedPerson(source.getPerson());
        amount = source.getAmount().value;
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {
        final Person person = this.person.toModelType();

        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount amount = new Amount(this.amount);

        return new Expense(person, amount);
    }
}
