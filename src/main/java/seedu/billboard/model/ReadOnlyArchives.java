package seedu.billboard.model;

import javafx.collections.ObservableList;
import seedu.billboard.model.expense.Expense;

import java.util.HashMap;

/**
 * Unmodifiable view of the archives
 */
public interface ReadOnlyArchives {

    /**
     * Returns an unmodifiable view of the expense list of the given archive.
     */
    ObservableList<Expense> getArchiveExpenses(String archiveName);

    /**
     * Returns a HashMap containing all archives as an unmodifiable view of each expense list.
     */
    HashMap<String, ObservableList<Expense>> getArchives();
}
