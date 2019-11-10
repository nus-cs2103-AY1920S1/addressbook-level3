package seedu.moneygowhere.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import seedu.moneygowhere.commons.util.DateUtil;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;
import seedu.moneygowhere.model.currency.UniqueCurrencyList;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.reminder.ReminderList;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.spending.SpendingList;
import seedu.moneygowhere.model.util.CurrencyDataUtil;

/**
 * Wraps all data at the address-book level
 */
public class SpendingBook implements ReadOnlySpendingBook {

    private final SpendingList spendings;
    private final Budget budget;
    private final ReminderList reminders;
    private UniqueCurrencyList currencies;
    private ObjectProperty<Currency> currencyInUse;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        spendings = new SpendingList();
        budget = new Budget(1000);
        reminders = new ReminderList();
        currencies = new UniqueCurrencyList();
        for (Currency currency : CurrencyDataUtil.getSampleCurrencies()) {
            currencies.add(currency);
        }
        currencyInUse = new SimpleObjectProperty<>(CurrencyDataUtil.getDefaultCurrency());
    }

    public SpendingBook() {}

    /**
     * Creates a SpendingBook using Spending entries in the {@code toBeCopied}
     */
    public SpendingBook(ReadOnlySpendingBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Spending list with {@code spendings}.
     * {@code spendings} must not contain duplicate spendings.
     */
    public void setSpendings(List<Spending> spendings) {
        this.spendings.setSpendings(spendings);
    }

    /**
     * Resets the existing data of this {@code SpendingBook} with {@code newData}.
     */
    public void resetData(ReadOnlySpendingBook newData) {
        requireNonNull(newData);

        setCurrencies(newData.getCurrencies());
        setCurrencyInUse(newData.getCurrencyInUse());
        setSpendings(newData.getSpendingList());
        setBudget(newData.getBudget());
        setReminders(newData.getReminderList());
    }

    //// Spending-level operations

    /**
     * Returns true if a Spending with the same identity as {@code Spending} exists in MoneyGoWhere.
     */
    public boolean hasSpending(Spending spending) {
        requireNonNull(spending);
        return spendings.contains(spending);
    }

    /**
     * Adds a Spending to the MoneyGoWhere list.
     */
    public void addSpending(Spending p) {
        spendings.add(p);
        budget.addSpending(p);
    }

    //@@author jonathantjendana
    /**
     * Adds multiple Spending to the MoneyGoWhere list.
     */
    public void addSpending(List<Spending> spendingList) {
        spendings.add(spendingList);
        budget.addSpending(spendingList);
    }

    //@@author
    /**
     * Replaces the given Spending {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the MoneyGoWhere list.
     * The Spending identity of {@code editedPerson} must not be the same as another existing
     * Spending in the MoneyGoWhere list.
     */
    public void setSpending(Spending target, Spending editedSpending) {
        requireNonNull(editedSpending);
        budget.deleteSpending(target);
        budget.addSpending(editedSpending);
        spendings.setSpending(target, editedSpending);
    }

    /**
     * Removes {@code key} from this {@code SpendingBook}.
     * {@code key} must exist in the MoneyGoWhere list.
     */
    public void removeSpending(Spending key) {
        spendings.remove(key);
        budget.deleteSpending(key);
    }

    //// Reminder-level operations

    /**
     * Replaces the contents of the Reminder list with {@code reminders}.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Returns true if a Reminder with the same identity as {@code Reminder} exists in MoneyGoWhere.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a Reminder to the reminder list.
     */
    public void addReminder(Reminder r) {
        reminders.add(r);
    }

    /**
     * Removes {@code key} from this {@code  Reminderlist}.
     * {@code key} must exist in the Reminder list.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    //// Currency-level operations
    //@@author Nanosync
    /**
     * Replaces the contents of the Currency list with {@code currencies}.
     */
    public void setCurrencies(List<Currency> currencies) {
        requireNonNull(currencies);
        this.currencies.setCurrencies(currencies);
    }

    /**
     * Sets the currency in use. It must be present in {@code currencies}.
     */
    public void setCurrencyInUse(Currency currency) {
        requireNonNull(currency);
        this.currencyInUse.setValue(currency);
    }

    /**
     * Adds a Currency to the currency list.
     */
    public void addCurrency(Currency c) {
        requireNonNull(c);
        currencies.add(c);
    }

    //// Budget related operations
    //@@author austinsantoso
    public void updateBudget() {
        budget.initialize(DateUtil.getTodayDate(), getSpendingList());
    }

    @Override
    public Budget getBudget() {
        return budget;
    }

    //@@author austinsantoso
    /**
     * Replaces the {@code Budget} in the MoneyGoWhere.
     */
    public void setBudget(Budget budget) {
        this.budget.setBudget(budget);
    }

    //@@author austinsantoso
    /**
     * Replaces the {@code Budget} in the MoneyGoWhere.
     */
    public void setBudgetAmount(Budget budget) {
        this.budget.setBudgetAmount(budget);
    }

    //@@author austinsantoso
    /**
     * Resets the Budget sum to 0.
     */
    public void clearBudgetSum() {
        budget.clearBudgetSum();
    }


    //// util methods
    @Override
    public String toString() {
        return spendings.asUnmodifiableObservableList().size() + " spendings";
        // TODO: refine later
    }

    //@@author Nanosync
    @Override
    public ObservableList<Spending> getSpendingList() {
        return spendings.asUnmodifiableObservableList();
    }

    //@@author minpyaemoe
    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    //@@author Nanosync
    @Override
    public ObservableList<Currency> getCurrencies() {
        return currencies.asUnmodifiableObservableList();
    }

    @Override
    public Currency getCurrencyInUse() {
        return currencyInUse.getValue();
    }

    /**
     * Registers a currency changed listener.
     * @param currencyChangeListener Currency changed listener
     */
    @Override
    public void registerCurrencyChangedListener(ChangeListener<Currency> currencyChangeListener) {
        requireNonNull(currencyChangeListener);
        currencyInUse.addListener(currencyChangeListener);
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpendingBook // instanceof handles nulls
                && spendings.equals(((SpendingBook) other).spendings)
                && reminders.equals(((SpendingBook) other).reminders)
                && currencies.equals(((SpendingBook) other).currencies)
                && currencyInUse.getValue().equals(((SpendingBook) other).currencyInUse.getValue())
                && budget.equals(((SpendingBook) other).budget));
    }

    @Override
    public int hashCode() {
        return Objects.hash(spendings, reminders, budget, currencies);
    }
}
