package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySpendingBook;
import seedu.address.model.SpendingBook;
import seedu.address.model.budget.Budget;
import seedu.address.model.spending.Spending;

/**
 * An Immutable SpendingBook that is serializable to JSON format.
 */
@JsonRootName(value = "spendingbook")
class JsonSerializableSpendingBook {

    public static final String MESSAGE_DUPLICATE_SPENDING = "Spending list contains duplicate Spending entries.";

    private final List<JsonAdaptedSpending> spendings = new ArrayList<>();
    private String budget;

    /**
     * Constructs a {@code JsonSerializableSpendingBook} with the given spendings.
     */
    @JsonCreator
    public JsonSerializableSpendingBook(@JsonProperty("budget") String budget,
            @JsonProperty("spendings") List<JsonAdaptedSpending> spendings) {
        this.spendings.addAll(spendings);
        this.budget = budget;
    }

    /**
     * Converts a given {@code ReadOnlySpendingBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSpendingBook}.
     */
    public JsonSerializableSpendingBook(ReadOnlySpendingBook source) {
        spendings.addAll(source.getSpendingList().stream().map(JsonAdaptedSpending::new).collect(Collectors.toList()));
        budget = "" + source.getBudget().getValue();
    }

    /**
     * Converts this MoneyGoWhere into the model's {@code SpendingBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SpendingBook toModelType() throws IllegalValueException {
        SpendingBook spendingBook = new SpendingBook();
        for (JsonAdaptedSpending jsonAdaptedSpending : spendings) {
            Spending spending = jsonAdaptedSpending.toModelType();
            if (spendingBook.hasSpending(spending)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SPENDING);
            }
            spendingBook.addSpending(spending);
        }

        spendingBook.setBudget(new Budget(budget));

        return spendingBook;
    }

}
