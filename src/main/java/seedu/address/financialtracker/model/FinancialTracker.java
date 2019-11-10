package seedu.address.financialtracker.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Encapsulates expense lists into financial tracker and allow accessing expense list accordingly by indicating
 * which country that the user wanna access.
 */
public class FinancialTracker {

    private String currentCountry;
    private final ObservableMap<String, ExpenseList> expenseListMap = FXCollections.observableHashMap();
    private final ObservableMap<String, ExpenseList> internalUnmodifiableExpenseListMap =
            FXCollections.unmodifiableObservableMap(expenseListMap);

    public FinancialTracker() {
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            expenseListMap.put(obj.getDisplayCountry(), new ExpenseList(obj.getDisplayCountry()));
        }
        currentCountry = "Singapore"; //Singapore as default
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    /**
     * Sets the comparator method in an expense list.
     * @param comparator comparator types specified in string.
     */
    public void setComparator(String comparator) {
        expenseListMap.get(currentCountry).setComparator(comparator);
    }

    /**
     * Changes current country key.
     * @param country the country to change
     */
    public void setCurrentCountry(String country) {
        if (expenseListMap.containsKey(country)) {
            currentCountry = country;
        } else {
            currentCountry = "Singapore";
        }

    }

    /**
     * Adds an expense into the expense list associated with current country key.
     */
    public void addExpense(Expense expense) throws CommandException {
        assert expenseListMap.get(currentCountry) != null;
        expense.setCountry(new Country(currentCountry));
        expenseListMap.get(currentCountry).addExpense(expense, true);
    }

    /**
     * Adds an expense into the expense list associated with another country key.
     * Used by storage
     */
    public void addExpense(Expense expense, Country country) throws CommandException {
        expenseListMap.get(country.value).addExpense(expense, false);
    }

    /**
     * Deletes an expense from the expense list associated with current coountry key.
     */
    public void deleteExpense(int index) {
        assert expenseListMap.get(currentCountry) != null;
        expenseListMap.get(currentCountry).deleteExpense(index);
    }

    /**
     * Returns an underlying Map.
     */
    public ObservableMap<String, ExpenseList> getInternalUnmodifiableExpenseListMap() {
        return internalUnmodifiableExpenseListMap;
    }

    /**
     * Edits an expense in the expense list associated with current country key.
     */
    public void setExpense(Expense expenseToEdit, Expense editedExpense) throws CommandException {
        requireNonNull(editedExpense);
        editedExpense.setCountry(new Country(currentCountry));
        expenseListMap.get(currentCountry).setExpense(expenseToEdit, editedExpense);
    }

    /**
     * Returns statistics of all the expense lists in a {@code HashMap} form.
     */
    public HashMap<String, Double> getSummaryMap() {
        HashMap<String, Double> summaryMap = new HashMap<>();
        double total = 0;
        for (String key : internalUnmodifiableExpenseListMap.keySet()) {
            if (!internalUnmodifiableExpenseListMap.get(key).isEmpty()) {
                double summary = internalUnmodifiableExpenseListMap.get(key).getSummary();
                summaryMap.put(key, summary);
                total += summary;
            }
        }
        summaryMap.put("Total", total);
        return summaryMap;
    }

    /**
     * Undo previous user action.
     */
    public void undo() throws CommandException {
        expenseListMap.get(currentCountry).undo();
    }

    /**
     * Clears the expense list associated with current country.
     */
    public void clearExpenseList() {
        expenseListMap.get(currentCountry).clearExpenseList();
    }
}
