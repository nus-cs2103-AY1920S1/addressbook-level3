package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.activity.Expense;

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
}
