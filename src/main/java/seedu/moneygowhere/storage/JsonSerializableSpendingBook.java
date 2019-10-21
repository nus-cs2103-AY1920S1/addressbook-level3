package seedu.moneygowhere.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;

/**
 * An Immutable SpendingBook that is serializable to JSON format.
 */
@JsonRootName(value = "spendingbook")
class JsonSerializableSpendingBook {

    public static final String MESSAGE_DUPLICATE_SPENDING = "Spending list contains duplicate Spending entries.";

    private final List<JsonAdaptedSpending> spendings = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private String budget;

    /**
     * Constructs a {@code JsonSerializableSpendingBook} with the given spendings and reminders.
     */
    @JsonCreator
    public JsonSerializableSpendingBook(@JsonProperty("budget") String budget,
            @JsonProperty("spendings") List<JsonAdaptedSpending> spendings,
                                        @JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.spendings.addAll(spendings);
        this.reminders.addAll(reminders);
        this.budget = budget;
    }

    /**
     * Converts a given {@code ReadOnlySpendingBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSpendingBook}.
     */
    public JsonSerializableSpendingBook(ReadOnlySpendingBook source) {
        spendings.addAll(source.getSpendingList().stream().map(JsonAdaptedSpending::new).collect(Collectors.toList()));
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
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

        for (JsonAdaptedReminder jsonAdaptedReminder: reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            spendingBook.addReminder(reminder);
        }

        spendingBook.setBudget(new Budget(budget));

        return spendingBook;
    }

}
