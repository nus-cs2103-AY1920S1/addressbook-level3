package seedu.billboard.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;

/**
 * Unmodifiable view of the archives
 */
public interface ReadOnlyArchiveWrapper {

    /**
     * Returns an unmodifiable view of the expense list of the given archive.
     */
    ObservableList<Expense> getArchiveExpenses(String archiveName);

    /**
     * Returns a list containing all archives.
     */
    List<Archive> getArchiveList();

    /**
     * Returns a list containing all archived expenses.
     */
    List<Expense> getExpenseList();
}
