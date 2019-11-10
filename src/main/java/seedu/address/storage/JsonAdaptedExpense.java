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
    private final int[] involvedIds;
    private final double amount;
    private final String description;
    private final boolean isSettlement;
    private final boolean isDeleted;

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("personId") int personId,
                              @JsonProperty("amount") double amount,
                              @JsonProperty("description") String description,
                              @JsonProperty("isSettlement") boolean isSettlement,
                              @JsonProperty("isDeleted") boolean isDeleted,
                              @JsonProperty("involvedIds") int ... involvedIds) {
        this.personId = personId;
        this.amount = amount;
        this.description = description;
        this.involvedIds = involvedIds;
        this.isSettlement = isSettlement;
        this.isDeleted = isDeleted;
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        personId = source.getPersonId();
        amount = source.getAmount().value;
        description = source.getDescription();
        involvedIds = source.getInvolved();
        isDeleted = source.isDeleted();
        isSettlement = source.isSettlement();
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {
        // Users shouldn't be able to enter values too massive.
        if (!isSettlement && !Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount amount = new Amount(this.amount);
        Expense res;
        if (involvedIds == null) {
            res = new Expense(personId, amount, description, isSettlement);
        } else {
            res = new Expense(personId, amount, description, isSettlement, involvedIds);
        }

        if (isDeleted) {
            res.delete();
        }

        return res;
    }
}
