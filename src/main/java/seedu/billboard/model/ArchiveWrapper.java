package seedu.billboard.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;

/**
 * Wraps all data at the archive level
 * Duplicate archives are not allowed
 */
public class ArchiveWrapper implements ReadOnlyArchiveWrapper {

    private final HashMap<String, Archive> archiveList;

    {
        archiveList = new HashMap<>();
    }

    public ArchiveWrapper(List<Expense> unfilteredExpenses) {
        requireNonNull(unfilteredExpenses);

        HashMap<String, List<Expense>> filterArchives = new HashMap<>();
        for (Expense archivedExpense : unfilteredExpenses) {
            String archiveName = archivedExpense.getArchiveName();
            if (!filterArchives.containsKey(archiveName)) {
                filterArchives.put(archiveName, new ArrayList<>());
            }
            filterArchives.get(archiveName).add(archivedExpense);
        }

        Set<String> archiveNames = filterArchives.keySet();
        for (String archiveName : archiveNames) {
            Archive archive = new Archive(archiveName, filterArchives.get(archiveName));
            archiveList.put(archiveName, archive);
        }

    }

    //==================== HashMap Overwrite operations ====================

    /**
     * Replaces the contents of the archives with {@code newArchives}.
     */
    public void setArchiveList(List<Archive> newArchiveList) {
        archiveList.clear();
        for (Archive archive : newArchiveList) {
            archiveList.put(archive.getArchiveName(), archive);
        }
    }

    /**
     * Resets the existing data of this {@code Billboard} with {@code newData}.
     */
    public void resetData(ReadOnlyArchiveWrapper newData) {
        requireNonNull(newData);
        setArchiveList(newData.getArchiveList());
    }

    //==================== Archive-Level operations ====================


    public Set<String> getArchiveNames() {
        return archiveList.keySet();
    }

    /**
     * Checks if the archiveWrapper has an archive with hte given archiveName.
     */
    public boolean hasArchive(String archiveName) {
        requireNonNull(archiveName);
        Set<String> archiveNames = getArchiveNames();
        return archiveNames.contains(archiveName);
    }

    public void addArchive(Archive newArchive) {
        archiveList.put(newArchive.getArchiveName(), newArchive);
    }

    public void removeArchive(String targetArchiveName) {
        archiveList.remove(targetArchiveName);
    }

    //==================== Expense-Level operations ====================

    /**
     * Returns true if an expense with the same identity as {@code expense} exists in the given archive.
     * The given {@code archiveName} must exist.
     */
    public boolean hasArchiveExpense(String archiveName, Expense expense) {
        requireNonNull(expense);
        Archive archive = archiveList.get(archiveName);
        return archive.contains(expense);
    }

    /**
     * Adds an expense to the given archive.
     */
    public void addArchiveExpense(String archiveName, Expense p) {
        archiveList.get(archiveName).add(p);
    }


    /**
     * Removes {@code key} from the given archive.
     * The given {@code archiveName} must exist.
     */
    public void removeArchiveExpense(String archiveName, Expense key) {
        archiveList.get(archiveName).remove(key);
    }

    //==================== Util Methods ====================

    @Override
    public ObservableList<Expense> getArchiveExpenses(String archiveName) {
        Archive archive = archiveList.get(archiveName);
        return archive.asUnmodifiableObservableList();
    }

    @Override
    public List<Archive> getArchiveList() {
        List<Archive> archives = new ArrayList<>();

        Set<String> archiveNames = getArchiveNames();
        for (String archiveName : archiveNames) {
            archives.add(archiveList.get(archiveName));
        }

        return archives;
    }

    @Override
    public List<Expense> getExpenseList() {
        List<Expense> expenses = new ArrayList<>();
        List<Archive> archives = getArchiveList();
        for (Archive archive : archives) {
            List<Expense> toBeCopied = archive.asUnmodifiableObservableList();
            for (Expense expense : toBeCopied) {
                expenses.add(expense);
            }
        }

        return expenses;
    }

    @Override
    public String toString() {
        return getArchiveNames().toString();
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveWrapper // instanceof handles nulls
                && archiveList.equals(((ArchiveWrapper) other).archiveList));
    }

    @Override
    public int hashCode() {
        return archiveList.hashCode();
    }
}
