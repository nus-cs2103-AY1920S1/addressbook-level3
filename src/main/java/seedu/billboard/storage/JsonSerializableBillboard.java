package seedu.billboard.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * An Immutable Billboard that is serializable to JSON format.
 */
@JsonRootName(value = "expenses")
class JsonSerializableBillboard {

    public static final String MESSAGE_DUPLICATE_EXPENSE = "Expenses list contains duplicate expense(s).";

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBillboard} with the given expenses.
     */
    @JsonCreator
    public JsonSerializableBillboard(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        this.expenses.addAll(expenses);
    }

    /**
     * Converts a given {@code ReadOnlyBillboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBillboard}.
     */
    public JsonSerializableBillboard(ReadOnlyBillboard source) {
        expenses.addAll(source.getExpenses().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Billboard into the model's {@code Billboard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Billboard toModelType() throws IllegalValueException {
        Billboard billboard = new Billboard();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (billboard.hasExpense(expense)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXPENSE);
            }

            List<String> tagNames = expense.getTags().stream()
                    .map(x -> x.tagName).collect(Collectors.toList());
            Set<Tag> tags = billboard.retrieveTags(tagNames);
            Expense edited = new Expense(expense.getName(), expense.getDescription(), expense.getAmount(),
                    expense.getCreated(), tags, expense.getArchiveName());
            billboard.incrementCount(tags);

            billboard.addExpense(edited);
        }
        return billboard;
    }

}
