package seedu.billboard.model.archive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.TAXES;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.exceptions.DuplicateExpenseException;
import seedu.billboard.model.expense.exceptions.ExpenseNotFoundException;


public class ArchiveTest {

    private final Archive archive = new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>());

    @Test
    public void constructor_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Archive(null, new ArrayList<>()));
    }

    @Test
    public void constructor_nullExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Archive(VALID_ARCHIVE_DINNER, null));
    }

    @Test
    public void setArchive_nullExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.setArchive(null));
    }

    @Test
    public void getArchiveName_correctArchiveNameReturned_success() {
        assertEquals(VALID_ARCHIVE_TAXES, archive.getArchiveName());
    }

    @Test
    public void getArchiveName_incorrectArchiveNameReturned_fail() {
        assertNotEquals(VALID_ARCHIVE_DINNER, archive.getArchiveName());
    }

    @Test
    public void toString_correctStringReturned_success() {
        assertEquals("Archive name: " + VALID_ARCHIVE_TAXES, archive.toString());
    }

    @Test
    public void toString_incorrectStringReturned_fail() {
        assertNotEquals("Archive name: " + VALID_ARCHIVE_DINNER, archive.toString());
    }

    @Test
    public void equals_sameArchive_success() {
        assertEquals(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()), archive);
    }

    @Test
    public void equals_differentArchive_fail() {
        assertNotEquals(new Archive(VALID_ARCHIVE_DINNER, new ArrayList<>()), archive);
    }

    @Test
    public void contains_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.contains(null));
    }

    @Test
    public void contains_expenseNotInList_returnsFalse() {
        assertFalse(archive.contains(BILLS));
    }

    @Test
    public void contains_expenseInList_returnsTrue() {
        archive.add(TAXES);
        assertTrue(archive.contains(TAXES));
    }

    @Test
    public void add_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.add(null));
    }

    @Test
    public void add_duplicateExpense_throwsDuplicateExpenseException() {
        archive.add(TAXES);
        assertThrows(DuplicateExpenseException.class, () -> archive.add(TAXES));
    }

    @Test
    public void setExpense_nullTargetExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.setExpense(null, TAXES));
    }

    @Test
    public void setExpense_nullEditedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.setExpense(TAXES, null));
    }

    @Test
    public void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> archive.setExpense(TAXES, TAXES));
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.remove(null));
    }

    @Test
    public void remove_expenseDoesNotExist_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> archive.remove(TAXES));
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        archive.add(TAXES);
        archive.remove(TAXES);
        Archive expectedArchiveList = new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>());
        assertEquals(expectedArchiveList, archive);
    }

    @Test
    public void setExpenses_nullUniqueArchive_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.setExpenses((Archive) null));
    }

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archive.setExpenses((List<Expense>) null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> archive
                .asUnmodifiableObservableList().remove(0));
    }
}
