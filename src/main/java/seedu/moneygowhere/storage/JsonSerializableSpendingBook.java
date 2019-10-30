package seedu.moneygowhere.storage;

import static seedu.moneygowhere.storage.JsonAdaptedCurrency.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.currency.Currency;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;

/**
 * An Immutable SpendingBook that is serializable to JSON format.
 */
@JsonRootName(value = "spendingbook")
class JsonSerializableSpendingBook {

    private final List<JsonAdaptedSpending> spendings = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private final List<JsonAdaptedCurrency> currencies = new ArrayList<>();
    private JsonAdaptedBudget budget;
    private String currency;

    /**
     * Constructs a {@code JsonSerializableSpendingBook} with the given spendings and reminders.
     */
    @JsonCreator
    public JsonSerializableSpendingBook(@JsonProperty("budget") JsonAdaptedBudget budget,
            @JsonProperty("currency") String currency,
            @JsonProperty("currencies") Set<JsonAdaptedCurrency> currencies,
            @JsonProperty("spendings") List<JsonAdaptedSpending> spendings,
            @JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.currencies.addAll(currencies);
        this.currency = currency;
        this.budget = budget;
        this.spendings.addAll(spendings);
        this.reminders.addAll(reminders);
    }

    /**
     * Converts a given {@code ReadOnlySpendingBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSpendingBook}.
     */
    public JsonSerializableSpendingBook(ReadOnlySpendingBook source) {
        currencies.addAll(source.getCurrencies().stream().map(JsonAdaptedCurrency::new).sorted()
                .collect(Collectors.toList()));
        currency = source.getCurrencyInUse().name;

        spendings.addAll(source.getSpendingList().stream().map(JsonAdaptedSpending::new).collect(Collectors.toList()));
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
        budget = new JsonAdaptedBudget(source.getBudget());
    }

    /**
     * Converts this MoneyGoWhere into the model's {@code SpendingBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SpendingBook toModelType() throws IllegalValueException {
        SpendingBook spendingBook = new SpendingBook();

        Currency currencyInUse = null;
        for (JsonAdaptedCurrency jsonAdaptedCurrency: currencies) {
            Currency currency = jsonAdaptedCurrency.toModelType();

            if (currency.name.equals(this.currency)) {
                currencyInUse = currency;
            }

            spendingBook.addCurrency(currency);
        }

        if (currencyInUse == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Currency"));
        }

        spendingBook.setCurrencyInUse(currencyInUse);

        for (JsonAdaptedSpending jsonAdaptedSpending : spendings) {
            Spending spending = jsonAdaptedSpending.toModelType();
            spendingBook.addSpending(spending);
        }

        for (JsonAdaptedReminder jsonAdaptedReminder: reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            spendingBook.addReminder(reminder);
        }

        spendingBook.setBudget(budget.toModelType());

        return spendingBook;
    }

}
