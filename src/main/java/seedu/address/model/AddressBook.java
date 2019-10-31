package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EXISTING_ENTRIES_CATEGORY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.address.commons.core.Messages.MESSAGE_NONEXISTENT_CATEGORY;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.AutoExpense;
import seedu.address.model.person.AutoExpenseList;
import seedu.address.model.person.Budget;
import seedu.address.model.person.BudgetList;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.ExpenseList;
import seedu.address.model.person.Income;
import seedu.address.model.person.IncomeList;
import seedu.address.model.person.UniqueEntryList;
import seedu.address.model.person.Wish;
import seedu.address.model.person.WishList;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.ReminderList;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.model.reminders.conditions.ConditionsManager;
import seedu.address.model.util.SampleDataUtil;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private final CategoryList categoryList;
    private final UniqueEntryList entries;
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
        entries = new UniqueEntryList();
        expenses = new ExpenseList();
        incomes = new IncomeList();
        budgets = new BudgetList();
        wishes = new WishList();
        autoExpenses = new AutoExpenseList();
        reminders = new ReminderList();
        conditions = new ConditionsManager();
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(false);
        resetData(toBeCopied);
    }

    /**
     * Creates an AddressBook, and decides whether there is a need to retain the original Categories or load from Sample
     * Data.
     *
     * @param shouldReloadCategories the boolean whether the categories should be reloaded.
     */
    public AddressBook(boolean shouldReloadCategories) {
        if (shouldReloadCategories == true) {
            Category[] listOfCategories = SampleDataUtil.getSampleCategories();
            for (Category sampleCategory : listOfCategories) {
                this.addCategory(sampleCategory);
            }
        }
    }

    //// list overwrite operations
    /**
     * Replaces the contents of entries with {@code entry}. {@code entry} must not
     * contain duplicate entries.
     */
    public void setCategories(List<Category> expenseCategories, List<Category> incomeCategories) {
        this.categoryList.setEntries(expenseCategories, incomeCategories);
        indicateModified();
    }

    public void setEntries(List<Entry> entries) {
        this.entries.setEntries(entries);
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


    public void updateBudgets() {
        budgets.updateBudgets();
    }


    /**
     * When Wishes and WishReminders are read from list, new instances are created.
     * As such editing a wish after loading a file may result in EntrySpecificCondition not updating wish accordingly.
     * This attempts to address that.
     */
    private void mapReminderToEntry() {
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setCategories(newData.getExpenseCategoryList(), newData.getIncomeCategoryList());
        setEntries(newData.getEntryList());
        setExpenses(newData.getExpenseList());
        setIncomes(newData.getIncomeList());
        setWishes(newData.getWishList());
        setBudgets(newData.getBudgetList());
        setAutoExpenses(newData.getAutoExpenseList());
        setReminders(newData.getReminderList());
        setConditions(newData.getConditionList());
    }

    //// person-level operations
    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the
     * address book.
     */
    public boolean hasCategory(Category category) {
        requireNonNull(category);
        return categoryList.contains(category);
    }

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the
     * address book.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return entries.contains(entry);
    }

    /**
     * Returns true if a budget with the same identity as {@code entry} exists in the
     * address book.
     */
    public boolean hasBudget(Budget entry) {
        requireNonNull(entry);
        return budgets.contains(entry);
    }

    /**
     * Returns true if a wish with the same identity as {@code entry} exists in the
     * address book.
     */
    public boolean hasWish(Wish entry) {
        requireNonNull(entry);
        return wishes.contains(entry);
    }
    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists
     * in the address book.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists
     * in the address book.
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
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
        conditions.addEntryUpdate(entry);
        indicateModified();
    }

    /**
     * Adds a specified Expense to the finance app.
     *
     * * @param expense the specified Expense to be added.
     */
    public void addExpense(Expense expense) {
        checkArgument(hasCategory(expense.getCategory()), MESSAGE_INVALID_CATEGORY);
        entries.add(expense);
        expenses.add(expense);
        conditions.addEntryUpdate(expense);
        indicateModified();
    }

    /**
     * Adds the specified Income to the finance app. Additional check for starting stage loading from data file.
     *
     * @param income the specified Income to be added.
     */
    public void addIncome(Income income) {
        checkArgument(hasCategory(income.getCategory()), MESSAGE_INVALID_CATEGORY);
        entries.add(income);
        incomes.add(income);
        conditions.addEntryUpdate(income);
        indicateModified();
    }

    /**
     * Adds the specified Income to the finance app.
     * @param budget the specified Income to be added.
     */
    public void addBudget(Budget budget) {
        checkArgument(hasCategory(budget.getCategory()), MESSAGE_INVALID_CATEGORY);
        budgets.add(budget);
        conditions.addEntryUpdate(budget);
        indicateModified();
    }

    /**
     * Adds the specified Wish to the finance app.
     * carries out check if category is valid here.
     *
     * @param wish the specified Wish to be added.
     */
    public void addWish(Wish wish) {
        checkArgument(hasCategory(wish.getCategory()), MESSAGE_INVALID_CATEGORY);
        wishes.add(wish);
        conditions.addEntryUpdate(wish);
        indicateModified();
    }

    /**
     * Adds the specified Conditin to the guiltTrip() app.
     * @param condition
     */
    public void addCondition(Condition condition) {
        conditions.add(condition);
        indicateModified();
    }

    /**
     * Adds the specified ExpenseTrackerReminder to the app.
     * @param reminder the specified Reminder to be added.
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
        checkArgument(hasCategory(autoExpense.getCategory()), MESSAGE_INVALID_CATEGORY);
        entries.add(autoExpense);
        autoExpenses.add(autoExpense);
        conditions.addEntryUpdate(autoExpense);
        indicateModified();
    }

    //TODO:(MC) Is there a better way about this? ALso update the rest

    /**
     *
     * @param oldCategoryName is the original CategoryName which is about to be edited
     * @param newCategoryName is the new CategoryName which is about to replace the original
     * @param categoryType is the type of the Category
     *
     * Checks all the current entries for entries that have the same category and create an editedEntry.
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
            List<Expense> filteredListOfExpense = toCheckExpenseList.stream()
                    .filter(t -> t.getCategory().categoryName.equalsIgnoreCase(oldCategoryName))
                    .collect(Collectors.toList());
            filteredListOfExpense.stream().forEach(t -> setExpense(t, t.modifiedCategory(newCategoryName)));
        }

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
     * Replaces the given person {@code target} in the list with
     * {@code editedPerson}. {@code target} must exist in the address book. The
     * person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        checkArgument(hasCategory(editedEntry.getCategory()), MESSAGE_INVALID_CATEGORY);
        entries.setEntry(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given Expense {@code target} in the list with
     * {@code editedEntry}. {@code target} must exist in the address book. The
     * expense identity of {@code editedEntry} must not be the same as another
     * existing expense in the address book.
     */
    public void setExpense(Expense target, Expense editedEntry) {
        requireNonNull(editedEntry);
        checkArgument(hasCategory(editedEntry.getCategory()), MESSAGE_INVALID_CATEGORY);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given Income {@code target} in the list with
     * {@code editedIncome}. {@code target} must exist in the address book. The
     * income identity of {@code editedEntry} must not be the same as another
     * existing income in the address book.
     */
    public void setIncome(Income target, Income editedEntry) {
        requireNonNull(editedEntry);
        checkArgument(hasCategory(editedEntry.getCategory()), MESSAGE_INVALID_CATEGORY);
        incomes.setIncome(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given Income {@code target} in the list with
     * {@code editedIncome}. {@code target} must exist in the address book. The
     * income identity of {@code editedEntry} must not be the same as another
     * existing income in the address book.
     */
    public void setWish(Wish target, Wish editedEntry) {
        requireNonNull(editedEntry);
        checkArgument(hasCategory(editedEntry.getCategory()), MESSAGE_INVALID_CATEGORY);
        wishes.setWish(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    public void setCondition(Condition target, Condition editedCondition) {
        requireNonNull(editedCondition);
        conditions.setCondition(target, editedCondition);
        indicateModified();
    }

    /**
     * Replaces the given Reminder {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the address book.
     * The ExpenseTracer identity of {@code editedTracker}
     * must not be the same as another existing Reminder in the address book.
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
        checkArgument(hasCategory(editedEntry.getCategory()), MESSAGE_INVALID_CATEGORY);
        budgets.setBudget(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedTracker}.
     * {@code target} must exist in the address book.
     * The ExpenseTracer identity of {@code editedTracker}
     * must not be the same as another existing Reminder in the address book.
     */
    /*public void setWishReminder(EntrySpecificCondition target, EntrySpecificCondition editedEntry) {
        requireNonNull(editedEntry);
        wishReminders.setWishReminder(target, editedEntry);
    }*/

    /**
     * Replaces the given AutoExpense {@code target} in the list with
     * {@code editedIncome}. {@code target} must exist in the address book. The
     * income identity of {@code editedEntry} must not be the same as another
     * existing income in the address book.
     */
    public void setAutoExpense(AutoExpense target, AutoExpense editedEntry) {
        requireNonNull(editedEntry);
        checkArgument(hasCategory(editedEntry.getCategory()), MESSAGE_INVALID_CATEGORY);
        autoExpenses.setAutoExpense(target, editedEntry);
        entries.setEntry(target, editedEntry);
        conditions.setEntryUpdate(target, editedEntry);
        indicateModified();
    }

    /**
     * Returns the truth value for whether the Category has any entries that currently exist in guiltTrip.
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
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCategory(Category category) {
        checkArgument(!categoryHasAnyEntries(category), MESSAGE_EXISTING_ENTRIES_CATEGORY);
        categoryList.remove(category);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEntry(Entry key) {
        entries.remove(key);
        conditions.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code expenses}. {@code key} must exist in the
     * address book.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
        entries.remove(key);
        conditions.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code incomes}. {@code key} must exist in the
     * address book.
     */
    public void removeIncome(Income key) {
        incomes.remove(key);
        entries.remove(key);
        conditions.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code wishes}. {@code key} must exist in the
     * address book.
     */
    public void removeWish(Wish key) {
        checkArgument(hasCategory(key.getCategory()), MESSAGE_NONEXISTENT_CATEGORY);
        wishes.remove(key);
        conditions.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code budgets}.
     * {@code key} must exist in the address book.
     */
    public void removeBudget(Budget key) {
        budgets.remove(key);
        conditions.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code autoExpenses}. {@code key} must exist in
     * the address book.
     */
    public void removeAutoExpense(AutoExpense key) {
        autoExpenses.remove(key);
        entries.remove(key);
        conditions.deleteEntryUpdate(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code expensetracker}.
     * {@code key} must exist in the address book.
     */

    public void removeCondition(Condition key) {
        conditions.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code wishes}.
     * {@code key} must exist in the address book.
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
        return entries.asUnmodifiableObservableList().size() + " entries";
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
    public ObservableList<Entry> getEntryList() {
        return entries.asUnmodifiableObservableList();
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

    /*@Override
    public ObservableList<EntrySpecificCondition> getWishReminderList() {
        return wishReminders.asUnmodifiableObservableList();
    }*/

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                        && entries.equals(((AddressBook) other).entries));
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }
}
