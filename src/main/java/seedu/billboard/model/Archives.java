package seedu.billboard.model;

import javafx.collections.ObservableList;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the archive level
 * Duplicate archives are not allowed
 */
public class Archives implements ReadOnlyArchives {

    private final HashMap<String, ExpenseList> archives;

    {
        archives = new HashMap<>();
    }

    public Archives(){}

    public Archives(ReadOnlyArchives toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //==================== HashMap Overwrite operations ====================

    /**
     * Replaces the contents of the archives with {@code newArchives}.
     */
    public void setArchives(HashMap<String, ObservableList<Expense>> newArchives) {
        Set<String> archiveNames = newArchives.keySet();
        this.archives.clear();
        for (String archiveName : archiveNames) {
            this.archives.put(archiveName, new ExpenseList());
            this.archives.get(archiveName).setExpenses(newArchives.get(archiveName));
        }
    }

    /**
     * Resets the existing data of this {@code Billboard} with {@code newData}.
     */
    public void resetData(ReadOnlyArchives newData) {
        requireNonNull(newData);
        setArchives(newData.getArchives());
    }

    //==================== Archive-Level operations ====================

    public Set<String> getArchiveNames() {
        return archives.keySet();
    }

    public boolean hasArchive(String archiveName) {
        Set<String> archiveNames = getArchiveNames();
        return archiveNames.contains(archiveName);
    }

    public void addArchive(String archiveName, List<Expense> archive) {
        archives.put(archiveName, new ExpenseList());
        archives.get(archiveName).setExpenses(archive);
    }

    public void removeArchive(String archiveName) {
        archives.remove(archiveName);
    }

    //==================== Expense-Level operations ====================

    /**
     * Returns true if an expense with the same identity as {@code expense} exists in the given archive.
     * The given {@code archiveName} must exist.
     */
    public boolean hasArchiveExpense(String archiveName, Expense expense) {
        requireNonNull(expense);
        ExpenseList expenses = archives.get(archiveName);
        return expenses.contains(expense);
    }

    /**
     * Adds an expense to the given archive.
     */
    public void addArchiveExpense(String archiveName, Expense p) {
        archives.get(archiveName).add(p);
    }

    /**
     * Replaces the given expense {@code target} in the given archive with {@code editedExpense}.
     * The given {@code archiveName} must exist.
     * {@code target} must exist in the archive.
     */
    public void setArchiveExpense(String archiveName, Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);
        ExpenseList expenses = archives.get(archiveName);
        expenses.setExpense(target, editedExpense);
    }

    /**
     * Removes {@code key} from the given archive.
     * The given {@code archiveName} must exist.
     */
    public void removeArchiveExpense(String archiveName, Expense key) {
        ExpenseList expenses = archives.get(archiveName);
        expenses.remove(key);
    }

    //==================== Util Methods ====================

    @Override
    public ObservableList<Expense> getArchiveExpenses(String archiveName) {
        return archives.get(archiveName).asUnmodifiableObservableList();
    }

    @Override
    public HashMap<String, ObservableList<Expense>> getArchives() {
        HashMap<String, ObservableList<Expense>> resultArchives = new HashMap<>();
        Set<String> archiveNames = getArchiveNames();

        for (String archiveName : archiveNames) {
            ObservableList<Expense> archive = getArchiveExpenses(archiveName);
            resultArchives.put(archiveName, archive);
        }

        return resultArchives;
    }

    @Override
    public String toString() {
        return getArchiveNames().toString();
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Archives // instanceof handles nulls
                && archives.equals(((Archives) other).archives));
    }

    @Override
    public int hashCode() {
        return archives.hashCode();
    }
}
