package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Expense;
import seedu.address.model.person.ExpenseReminder;
import seedu.address.model.person.Income;
import seedu.address.model.person.Wish;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();
    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();
    private final List<JsonAdaptedWish> wishes = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final List<JsonAdaptedExpenseReminder> expenseReminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        this.expenses.addAll(expenses);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
        incomes.addAll(source.getIncomeList().stream().map(JsonAdaptedIncome::new).collect(Collectors.toList()));
        wishes.addAll(source.getWishList().stream().map(JsonAdaptedWish::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
        expenseReminders.addAll(
                source.getExpenseReminderList().stream().map(
                        JsonAdaptedExpenseReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            addressBook.addExpense(expense);
        }
        for (JsonAdaptedIncome jsonAdaptedIncome : incomes) {
            Income income = jsonAdaptedIncome.toModelType();
            addressBook.addIncome(income);
        }
        for (JsonAdaptedWish jsonAdaptedWish: wishes) {
            Wish wish = jsonAdaptedWish.toModelType();
            addressBook.addWish(wish);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget: budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            addressBook.addBudget(budget);
        }

        for (JsonAdaptedExpenseReminder jsonAdaptedExpenseReminder : expenseReminders) {
            ExpenseReminder reminder = jsonAdaptedExpenseReminder.toModelType();
            addressBook.addExpenseReminder(reminder);
        }
        return addressBook;
    }

}
