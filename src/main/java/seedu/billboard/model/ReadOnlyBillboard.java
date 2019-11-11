package seedu.billboard.model;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.recurrence.RecurrenceList;
import seedu.billboard.model.tag.Tag;

/**
 * Unmodifiable view of a Billboard
 */
public interface ReadOnlyBillboard {

    /**
     * Returns an unmodifiable view of the expense list.
     */
    ObservableList<Expense> getExpenses();

    RecurrenceList getRecurrences();

    Map<Tag, Integer> getCountManager();

    Map<String, Tag> getUniqueTagList();

    List<Expense> filterArchiveExpenses();

    ReadOnlyBillboard removeArchiveExpenses();


}
