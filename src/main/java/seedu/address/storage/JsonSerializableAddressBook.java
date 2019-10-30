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
import seedu.address.model.person.Category;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;
import seedu.address.model.person.Wish;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.storage.conditions.JsonAdaptedCondition;


/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_WRONG_CATEGORY = "Data file load error due to non existent category. ";

    private final List<JsonAdaptedCategory> listofExpenseCategories = new ArrayList<>();
    private final List<JsonAdaptedCategory> listofIncomeCategories = new ArrayList<>();
    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();
    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();
    private final List<JsonAdaptedWish> wishes = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private final List<JsonAdaptedCondition> conditions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses,
                                       @JsonProperty("listofExpenseCategories") List<JsonAdaptedCategory>
                                               listofExpenseCategories,
                                       @JsonProperty("listofIncomeCategories") List<JsonAdaptedCategory>
                                               listofIncomeCategories) {
        this.expenses.addAll(expenses);
        this.listofExpenseCategories.addAll(listofExpenseCategories);
        this.listofIncomeCategories.addAll(listofIncomeCategories);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        listofExpenseCategories.addAll(source.getExpenseCategoryList().stream().map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
        listofIncomeCategories.addAll(source.getIncomeCategoryList().stream().map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
        incomes.addAll(source.getIncomeList().stream().map(JsonAdaptedIncome::new).collect(Collectors.toList()));
        wishes.addAll(source.getWishList().stream().map(JsonAdaptedWish::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
        conditions
                .addAll(source.getConditionList().stream().map(JsonAdaptedCondition::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedCategory jsonAdaptedCategory : listofExpenseCategories) {
            Category category = jsonAdaptedCategory.toModelType();
            addressBook.addCategory(category);
        }

        for (JsonAdaptedCategory jsonAdaptedCategory : listofIncomeCategories) {
            Category category = jsonAdaptedCategory.toModelType();
            addressBook.addCategory(category);
        }

        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (!addressBook.getCategoryList().contains(expense.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            addressBook.addExpense(expense);
        }

        for (JsonAdaptedIncome jsonAdaptedIncome : incomes) {
            Income income = jsonAdaptedIncome.toModelType();
            if (!addressBook.getCategoryList().contains(income.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            addressBook.addIncome(income);
        }

        for (JsonAdaptedWish jsonAdaptedWish : wishes) {
            Wish wish = jsonAdaptedWish.toModelType();
            if (!addressBook.getCategoryList().contains(wish.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            addressBook.addWish(wish);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget: budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (!addressBook.getCategoryList().contains(budget.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            addressBook.addBudget(budget);
        }

        ReminderConditionMapper mapper = new ReminderConditionMapper(reminders, conditions);
        for (Reminder reminder : mapper.getReminders()) {
            addressBook.addReminder(reminder);
        }

        for (Condition condition : mapper.getConditions()) {
            addressBook.addCondition(condition);
        }

        return addressBook;
    }

}
