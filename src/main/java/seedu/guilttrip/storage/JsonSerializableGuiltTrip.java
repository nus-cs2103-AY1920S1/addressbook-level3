package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;

/**
 * An Immutable GuiltTrip that is serializable to JSON format.
 */
@JsonRootName(value = "guilttrip")
class JsonSerializableGuiltTrip {

    public static final String MESSAGE_WRONG_CATEGORY = "Data file load error due to non existent category. ";

    private final List<JsonAdaptedCategory> listofExpenseCategories = new ArrayList<>();
    private final List<JsonAdaptedCategory> listofIncomeCategories = new ArrayList<>();
    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();
    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();
    private final List<JsonAdaptedWish> wishes = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private final List<JsonAdaptedAutoExpense> autoExpenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGuiltTrip} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGuiltTrip(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses,
                                     @JsonProperty("listofExpenseCategories") List<JsonAdaptedCategory>
                                             listofExpenseCategories,
                                     @JsonProperty("listofIncomeCategories") List<JsonAdaptedCategory>
                                             listofIncomeCategories) {
        this.expenses.addAll(expenses);
        this.listofExpenseCategories.addAll(listofExpenseCategories);
        this.listofIncomeCategories.addAll(listofIncomeCategories);
    }

    /**
     * Converts a given {@code ReadOnlyGuiltTrip} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableGuiltTrip}.
     */
    public JsonSerializableGuiltTrip(ReadOnlyGuiltTrip source) {
        listofExpenseCategories
                .addAll(source.getExpenseCategoryList().stream()
                        .map(JsonAdaptedCategory::new).collect(Collectors.toList()));
        listofIncomeCategories
                .addAll(source.getIncomeCategoryList().stream()
                        .map(JsonAdaptedCategory::new).collect(Collectors.toList()));
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
        incomes.addAll(source.getIncomeList().stream().map(JsonAdaptedIncome::new).collect(Collectors.toList()));
        wishes.addAll(source.getWishList().stream().map(JsonAdaptedWish::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
        reminders.addAll(source.getReminderList().stream()
                .filter(reminder -> !reminder.getStatus().equals(Reminder.Status.exceeded))
                .map(JsonAdaptedReminder::new).collect(Collectors.toList()));
        autoExpenses.addAll(
                source.getAutoExpenseList().stream().map(JsonAdaptedAutoExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this guilttrip book into the model's {@code GuiltTrip} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GuiltTrip toModelType() throws IllegalValueException {
        GuiltTrip guiltTrip = new GuiltTrip(false);

        for (JsonAdaptedCategory jsonAdaptedCategory : listofExpenseCategories) {
            Category category = jsonAdaptedCategory.toModelType();
            guiltTrip.addCategory(category);
        }

        for (JsonAdaptedCategory jsonAdaptedCategory : listofIncomeCategories) {
            Category category = jsonAdaptedCategory.toModelType();
            guiltTrip.addCategory(category);
        }

        ReminderMapper mapper = new ReminderMapper(reminders);


        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (!guiltTrip.getCategoryList().contains(expense.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            if (expense.hasReminder()) {
                mapper.mapEntry(expense);
            }
            guiltTrip.addExpense(expense);
        }

        for (JsonAdaptedIncome jsonAdaptedIncome : incomes) {
            Income income = jsonAdaptedIncome.toModelType();
            if (!guiltTrip.getCategoryList().contains(income.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            if (income.hasReminder()) {
                mapper.mapEntry(income);
            }
            guiltTrip.addIncome(income);
        }

        for (JsonAdaptedWish jsonAdaptedWish : wishes) {
            Wish wish = jsonAdaptedWish.toModelType();
            if (!guiltTrip.getCategoryList().contains(wish.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            if (wish.hasReminder()) {
                mapper.mapEntry(wish);
            }
            guiltTrip.addWish(wish);
        }

        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (!guiltTrip.getCategoryList().contains(budget.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            guiltTrip.addBudget(budget);
        }

        for (JsonAdaptedAutoExpense jsonAdaptedAutoExpense : autoExpenses) {
            AutoExpense autoExpense = jsonAdaptedAutoExpense.toModelType();
            if (!guiltTrip.getCategoryList().contains(autoExpense.getCategory())) {
                throw new IllegalValueException(MESSAGE_WRONG_CATEGORY);
            }
            guiltTrip.addAutoExpense(autoExpense);
        }

        for (Reminder reminder : mapper.getReminders()) {
            guiltTrip.addReminder(reminder);
        }

        for (Condition condition : mapper.getConditions()) {
            guiltTrip.addCondition(condition);
        }

        return guiltTrip;
    }

}
