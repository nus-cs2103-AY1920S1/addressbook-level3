package seedu.guilttrip.model;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_NONEXISTENT_CATEGORY;
import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.guilttrip.commons.util.InvalidationListenerManager;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.AutoExpenseList;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.BudgetList;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.CategoryList;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.ExpenseList;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.IncomeList;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.entry.WishList;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.ReminderList;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.ConditionsManager;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.model.util.SampleDataUtil;
import seedu.guilttrip.ui.UiManager;

/**
 * Wraps all data at the guilttrip-book level Duplicates are not allowed (by
 * .isSamePerson comparison)
 */
public class GuiltTrip implements ReadOnlyGuiltTrip {
    private final CategoryList categoryList;
    private final ExpenseList expenses;
    private final IncomeList incomes;
    private final BudgetList budgets;
    private final WishList wishes;
    private final AutoExpenseList autoExpenses;
    private final ReminderList reminders;
    private final ConditionsManager conditions;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other
     * ways to avoid duplication among constructors.
     */
    {
        categoryList = new CategoryList();
        expenses = new ExpenseList();
        incomes = new IncomeList();
        budgets = new BudgetList();
        wishes = new WishList();
        autoExpenses = new AutoExpenseList();
        reminders = new ReminderList();
        conditions = new ConditionsManager();
    }

    /**
     * Creates an GuiltTrip using the Persons in the {@code toBeCopied}
     */
    public GuiltTrip(ReadOnlyGuiltTrip toBeCopied) {
        this(false);
        resetData(toBeCopied);
    }

    /**
     * Creates an GuiltTrip, and decides whether there is a need to retain the original Categories or load from Sample
     * Data.
     *
     * @param shouldReloadCategories the boolean whether the categories should be reloaded.
     */
    public GuiltTrip(boolean shouldReloadCategories) {
        if (shouldReloadCategories) {
            Category[] listOfCategories = SampleDataUtil.getSampleCategories();
            for (Category sampleCategory : listOfCategories) {
                this.addCategory(sampleCategory);
            }
        }
    }

    public void linkReminderListToUi(UiManager uiManager) {
        this.reminders.linkToUi(uiManager);
    }

    //// list overwrite operations
    /**
     * Replaces the contents of entries with {@code entry}. {@code entry} must not
     * contain duplicate entries.
     */
    public void setCategories(List<Category> expenseCategories, List<Category> incomeCategories) {
        this.categoryList.setCategories(expenseCategories, incomeCategories);
        indicateModified();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses.setEntries(expenses);
        indicateModified();
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes.setEntries(incomes);
        indicateModified();
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes.setEntries(wishes);
        indicateModified();
    }

    public void setAutoExpenses(List<AutoExpense> autoExpenses) {
        this.autoExpenses.setEntries(autoExpenses);
        indicateModified();
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders.setEntries(reminders);
        indicateModified();
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions.setEntries(conditions);
        indicateModified();
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets.setEntries(budgets);
        indicateModified();
    }


    /**
     * Update Budgets based on updated list of expenses
     *
     * @param filteredExpenses filtered list of expenses
     */
    public void updateBudgets(FilteredList<Expense> filteredExpenses) {
        for (Budget budget : budgets) {
            budget.setSpent(filteredExpenses);
        }
        budgets.updateBudgets();
    }


    /**
     * When Wishes and WishReminders are read from list, new instances are created.
     * As such editing a wish after loading a file may result in EntrySpecificCondition not updating wish accordingly.
     * This attempts to guilttrip that.
     */
    private void mapReminderToEntry() {
    }

    /**
     * Resets the existing data of this {@code GuiltTrip} with {@code newData}.
     */
    public void resetData(ReadOnlyGuiltTrip newData) {
        requireNonNull(newData);
        setCategories(newData.getExpenseCategoryList(), newData.getIncomeCategoryList());
        setExpenses(newData.getExpenseList());
        setIncomes(newData.getIncomeList());
        setWishes(newData.getWishList());
        setBudgets(newData.getBudgetList());
        setAutoExpenses(newData.getAutoExpenseList());
        setReminders(newData.getReminderList());
        setConditions(newData.getConditionList());
    }

    //// entry-level operations
    public void selectReminder(Reminder reminder) {
        reminders.setReminderSelected(reminder);
    }

    public Reminder getReminderSelected() {
        return reminders.getReminderSelected();
    }
    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the
     * guilttrip book.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the
     * guilttrip book.
     */

    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return incomes.contains(income);
    }

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the
     * guilttrip book.
     */
    public boolean hasCategory(Category category) {
        requireNonNull(category);
        return categoryList.contains(category);
    }

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the
     * guilttrip book.
     */
    public boolean hasAutoExpense(AutoExpense category) {
        requireNonNull(category);
        return autoExpenses.contains(category);
    }

    /**
     * Returns true if a budget with the same identity as {@code entry} exists in the
     * guilttrip book.
     */
    public boolean hasBudget(Budget entry) {
        requireNonNull(entry);
        return budgets.contains(entry);
    }

    /**
     * Returns true if a wish with the same identity as {@code entry} exists in the
     * guilttrip book.
     */
    public boolean hasWish(Wish entry) {
        requireNonNull(entry);
        return wishes.contains(entry);
    }
    /**
     * Returns true if a generalReminder with the same identity as {@code generalReminder} exists
     * in the guilttrip book.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Returns true if a generalReminder with the same identity as {@code generalReminder} exists
     * in the guilttrip book.
     */
    public boolean hasCondition(Condition condition) {
        requireNonNull(condition);
        return conditions.contains(condition);
    }

    /**
     * Populates the CategoryList with the specified categories.
     */
    public void addCategory(Category category) {
        categoryList.add(category);
        indicateModified();
    }

    /**
     * Adds a entry to the guilttrip book.
     * The entry must not already exist in the guilttrip book.
     */
    public void addEntry(Entry entry) {
        if (entry instanceof Expense) {
            addExpense((Expense) entry);
        } else if (entry instanceof Income) {
            addIncome((Income) entry);
        } else if (entry instanceof Wish) {
            addWish((Wish) entry);
        } else if (entry instanceof Budget) {
            addBudget((Budget) entry);
        } else if (entry instanceof AutoExpense) {
            addAutoExpense((AutoExpense) entry);
        }
    }

    /**
     * Adds a specified Expense to the finance app. Checks whether the category of the to be added Expense is valid.
     *
     * * @param expense the specified Expense to be added.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        conditions.addEntryUpdate(expense);
        indicateModified();
    }

    /**
     * Adds the specified Income to the finance app. Additional check for starting stage loading from data file.
     * Checks whether the category of the to be added Income is valid.
     *
     * @param income the specified Income to be added.
     */
    public void addIncome(Income income) {
        incomes.add(income);
        conditions.addEntryUpdate(income);
        indicateModified();
    }

    /**
     * Adds the specified Income to the finance app. Checks whether the category of the to be added budget is valid.
     * @param budget the specified Income to be added.
     */
    public void addBudget(Budget budget) {
        budgets.add(budget);
        conditions.addEntryUpdate(budget);
        indicateModified();
    }

    /**
     * Adds the specified Wish to the finance app.
     * Checks if the category of the to be added wish is valid.
     *
     *
     * @param wish the specified Wish to be added.
     */
    public void addWish(Wish wish) {
        wishes.add(wish);
        conditions.addEntryUpdate(wish);
        indicateModified();
    }

    /**
     * Adds the specified Condition to the guilttrip() app.
     * @param condition
     */
    public void addCondition(Condition condition) {
        conditions.add(condition);
        indicateModified();
    }

    /**
     * Adds the specified ExpenseTrackerReminder to the app.
     * @param reminder the specified GeneralReminder to be added.
     */
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        indicateModified();
    }

    /**
     * Adds the specified AutoExpense to the finance app.
     *
     * @param autoExpense the specified AutoExpense to be added.
     */
    public void addAutoExpense(AutoExpense autoExpense) {
        autoExpenses.add(autoExpense);
        conditions.addEntryUpdate(autoExpense);
        indicateModified();
    }

    //TODO:(MC) Is there a better way about this? ALso update the rest
    /**
     * Checks all the current entries for entries that have the same category and create an editedEntry. Long winded
     * method due to the fact of java restrictions on list inheritance.
     *
     * @param oldCategoryName is the original CategoryName which is about to be edited
     * @param newCategoryName is the new CategoryName which is about to replace the original
     * @param categoryType is the type of the Category
     */
    public void editCategoryNamesToNewName(String oldCategoryName, String newCategoryName, String categoryType) {
        if (categoryType.equalsIgnoreCase("Income")) {
            ObservableList<Income> tocheckIncomeList = this.getIncomeList();
            List<Income> filteredListOfIncome = tocheckIncomeList.stream()
                    .filter(t -> t.getCategory().categoryName.equalsIgnoreCase(oldCategoryName))
                    .collect(Collectors.toList());
            filteredListOfIncome.stream().forEach(t -> setIncome(t, t.modifiedCategory(newCategoryName)));
        } else {
            ObservableList<Expense> toCheckExpenseList = this.getExpenseList();
            ObservableList<Wish> toCheckWishList = this.getWishList();
            ObservableList<Budget> toCheckBudget = this.getBudgetList();
            ObservableList<AutoExpense> toCheckAutoExpense = this.getAutoExpenseList();
            List<Expense> filteredListOfExpense = toCheckExpenseList.stream()
                    .filter(t -> t.getCategory().categoryName.equalsIgnoreCase(oldCategoryName))
                    .collect(Collectors.toList());
            filteredListOfExpense.stream().forEach(t -> setExpense(t, t.modifiedCategory(newCategoryName)));
            List<Wish> filteredListOfWish = toCheckWishList.stream()
                    .filter(t -> t.getCategory().categoryName.equalsIgnoreCase(oldCategoryName))
                    .collect(Collectors.toList());
            filteredListOfWish.stream().forEach(t -> setWish(t, t.modifiedWish(newCategoryName)));
            List<AutoExpense> filteredListOfAutoExpense = toCheckAutoExpense.stream()
                    .filter(t -> t.getCategory().categoryName.equalsIgnoreCase(oldCategoryName))
                    .collect(Collectors.toList());
            filteredListOfAutoExpense.stream().forEach(t -> setAutoExpense(t, t.modifiedAutoExpense(newCategoryName)));
            List<Budget> filteredListOfBudget = toCheckBudget.stream()
                    .filter(t -> t.getCategory().categoryName.equalsIgnoreCase(oldCategoryName))
                    .collect(Collectors.toList());
            filteredListOfBudget.stream().forEach(t -> setBudget(t, t.modifiedBudget(newCategoryName)));
        }
        indicateModified();
    }

    /**
     * Replaces the given category with the new editedCategory. Modifies all the categories in the list of entries
     * that corresponds to changed category.
     */
    public void setCategory(Category target, Category editedCategory) {
        requireNonNull(editedCategory);
        String oldCategoryName = target.categoryName;
        String newCategoryName = editedCategory.categoryName;
        categoryList.setCategory(target, editedCategory);
        String categoryType = target.categoryType;
        editCategoryNamesToNewName(oldCategoryName, newCategoryName, categoryType);
        indicateModified();
    }

    /**
     * Replaces the given Expense {@code target} in the list with
     * {@code editedEntry}. {@code target} must exist in the guilttrip book. The
     * expense identity of {@code editedEntry} must not be the same as another
     * existing expense in the guilttrip book.
     */
    public void setExpense(Expense target, Expense editedEntry) {
        requireNonNull(editedEntry);
        expenses.setExpense(target, editedEntry);
        reminders.setEntryUpdate(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given Income {@code target} in the list with
     * {@code editedIncome}. {@code target} must exist in the guilttrip book. The
     * income identity of {@code editedEntry} must not be the same as another
     * existing income in the guilttrip book.
     */
    public void setIncome(Income target, Income editedEntry) {
        requireNonNull(editedEntry);
        incomes.setIncome(target, editedEntry);
        reminders.setEntryUpdate(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given Income {@code target} in the list with
     * {@code editedIncome}. {@code target} must exist in the guilttrip book. The
     * income identity of {@code editedEntry} must not be the same as another
     * existing income in the guilttrip book.
     */
    public void setWish(Wish target, Wish editedEntry) {
        requireNonNull(editedEntry);
        wishes.setWish(target, editedEntry);
        reminders.setEntryUpdate(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    public void setCondition(Condition target, Condition editedCondition) {
        requireNonNull(editedCondition);
        conditions.setCondition(target, editedCondition);
        indicateModified();
    }

    /**
     * Replaces the given GeneralReminder {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the guilttrip book.
     * The ExpenseTracer identity of {@code editedTracker}
     * must not be the same as another existing GeneralReminder in the guilttrip book.
     */
    public void setReminder(Reminder target, Reminder editedEntry) {
        requireNonNull(editedEntry);
        reminders.setReminder(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given Budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the finance tracker.
     * The budget identity of {@code editedEntry} must not be the same as another existing budget
     * in the finance tracker.
     */
    public void setBudget(Budget target, Budget editedEntry) {
        requireNonNull(editedEntry);
        budgets.setBudget(target, editedEntry);
        reminders.setEntryUpdate(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }


    /**
     * Replaces the given generalReminder {@code target} in the list with {@code editedTracker}.
     * {@code target} must exist in the guilttrip book.
     * The ExpenseTracer identity of {@code editedTracker}
     * must not be the same as another existing GeneralReminder in the guilttrip book.
     */
    /*public void setWishReminder(EntrySpecificCondition target, EntrySpecificCondition editedEntry) {
        requireNonNull(editedEntry);
        wishReminders.setWishReminder(target, editedEntry);
    }*/

    /**
     * Replaces the given AutoExpense {@code target} in the list with
     * {@code editedIncome}. {@code target} must exist in the guilttrip book. The
     * income identity of {@code editedEntry} must not be the same as another
     * existing income in the guilttrip book.
     */
    public void setAutoExpense(AutoExpense target, AutoExpense editedEntry) {
        requireNonNull(editedEntry);
        autoExpenses.setAutoExpense(target, editedEntry);
        reminders.setEntryUpdate(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Returns the truth value for whether the Category has any entries that currently exist in guilttrip.
     */
    public boolean categoryHasAnyEntries(Category category) {
        if (category.categoryType.equalsIgnoreCase("Income")) {
            ObservableList<Income> tocheckIncomeList = this.getIncomeList();
            return tocheckIncomeList.stream()
                    .anyMatch(t -> t.getCategory().categoryName.equalsIgnoreCase(category.categoryName));
        } else {
            ObservableList<Expense> toCheckExpenseList = this.getExpenseList();
            boolean hasEntriesInExpenseList = toCheckExpenseList.stream()
                    .anyMatch(t -> t.getCategory().categoryName.equalsIgnoreCase(category.categoryName));
            ObservableList<Budget> tocheckBudgetList = this.getBudgetList();
            boolean hasEntriesInBudgetList = tocheckBudgetList.stream()
                    .anyMatch(t -> t.getCategory().categoryName.equalsIgnoreCase(category.categoryName));
            ObservableList<Wish> toCheckWish = this.getWishList();
            boolean hasEntriesInWishList = toCheckWish.stream()
                    .anyMatch(t -> t.getCategory().categoryName.equalsIgnoreCase(category.categoryName));
            ObservableList<AutoExpense> toCheckAutoExpense = this.getAutoExpenseList();
            boolean hasEntriesInAutoList = toCheckAutoExpense.stream()
                    .anyMatch(t -> t.getCategory().categoryName.equalsIgnoreCase(category.categoryName));
            return hasEntriesInBudgetList || hasEntriesInExpenseList || hasEntriesInWishList || hasEntriesInAutoList;
        }
    }

    /**
     * Removes {@code key} from this {@code GuiltTrip}.
     * {@code key} must exist in the guilttrip book.
     */
    public void removeCategory(Category category) {
        categoryList.remove(category);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code GuiltTrip}.
     * {@code key} must exist in the guilttrip book.
     */
    public void removeEntry(Entry key) {
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code expenses}. {@code key} must exist in the
     * guilttrip book.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
        reminders.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code incomes}. {@code key} must exist in the
     * guilttrip book.
     */
    public void removeIncome(Income key) {
        incomes.remove(key);
        reminders.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code wishes}. {@code key} must exist in the
     * guilttrip book.
     */
    public void removeWish(Wish key) {
        checkArgument(hasCategory(key.getCategory()), MESSAGE_NONEXISTENT_CATEGORY);
        wishes.remove(key);
        reminders.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code budgets}.
     * {@code key} must exist in the guilttrip book.
     */
    public void removeBudget(Budget key) {
        budgets.remove(key);
        reminders.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code autoExpenses}. {@code key} must exist in
     * the guilttrip book.
     */
    public void removeAutoExpense(AutoExpense key) {
        autoExpenses.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code expensetracker}.
     * {@code key} must exist in the guilttrip book.
     */

    public void removeCondition(Condition key) {
        conditions.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code wishes}.
     * {@code key} must exist in the guilttrip book.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the finance tracker has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods
    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " entries";
        // TODO: refine later
    }

    @Override
    public CategoryList getCategoryList() {
        return categoryList;
    }

    @Override
    public ObservableList<Category> getIncomeCategoryList() {
        return categoryList.getInternalListForIncome();
    }

    @Override
    public ObservableList<Category> getExpenseCategoryList() {
        return categoryList.getInternalListForOtherEntries();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        return incomes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Wish> getWishList() {
        return wishes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<AutoExpense> getAutoExpenseList() {
        return autoExpenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Condition> getConditionList() {
        return conditions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Notification> getNotificationList() {
        return reminders.asUnmodifiableNotificationList();
    }

    /*@Override
    public ObservableList<EntrySpecificCondition> getWishReminderList() {
        return wishReminders.asUnmodifiableObservableList();
    }*/

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuiltTrip // instanceof handles nulls
                        && expenses.equals(((GuiltTrip) other).expenses));
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
