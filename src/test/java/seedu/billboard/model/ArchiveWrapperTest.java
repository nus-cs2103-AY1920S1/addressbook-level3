package seedu.billboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.FOOTBALL;
import static seedu.billboard.testutil.TypicalExpenses.GUCCI_SLIDES;
import static seedu.billboard.testutil.TypicalExpenses.IPHONE11;
import static seedu.billboard.testutil.TypicalExpenses.KPOP_LIGHT_STICK;
import static seedu.billboard.testutil.TypicalExpenses.TAXES;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalArchiveExpenses;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalArchiveWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.exceptions.ExpenseNotFoundException;
import seedu.billboard.testutil.Assert;
import seedu.billboard.testutil.ExpenseBuilder;

public class ArchiveWrapperTest {

    private ArchiveWrapper archiveWrapper;

    @BeforeEach
    public void setUp() {
        archiveWrapper = new ArchiveWrapper(getTypicalArchiveExpenses());
    }

    @Test
    public void constructor_nullExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ArchiveWrapper((HashMap<String, Archive>) null));
    }

    // reset tests =============================================================================
    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> archiveWrapper.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyArchiveWrapper_replacesData() {
        ArchiveWrapper newData = getTypicalArchiveWrapper();
        archiveWrapper.resetData(newData);
        assertEquals(newData, archiveWrapper);
    }

    // has tests =============================================================================
    @Test
    public void hasArchive_nullExpense_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> archiveWrapper.hasArchive(null));
    }

    @Test
    public void hasArchive_archiveNotInArchiveWrapper_returnsFalse() {
        assertFalse(archiveWrapper.hasArchive(VALID_ARCHIVE_TAXES));
    }

    @Test
    public void hasArchive_archiveInArchiveWrapper_returnsTrue() {
        archiveWrapper.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        assertTrue(archiveWrapper.hasArchive(VALID_ARCHIVE_TAXES));
    }

    @Test
    public void hasArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.hasArchiveExpense(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void hasArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.hasArchiveExpense(null, TAXES));
    }

    @Test
    public void hasArchiveExpense_expenseNotInArchive_returnsFalse() {
        assertFalse(archiveWrapper.hasArchiveExpense("luxury", TAXES));
    }

    @Test
    public void hasArchiveExpense_expenseInArchive_returnsTrue() {
        archiveWrapper.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        archiveWrapper.addArchiveExpense(VALID_ARCHIVE_TAXES, TAXES);
        assertTrue(archiveWrapper.hasArchiveExpense(VALID_ARCHIVE_TAXES, TAXES));
    }

    // add tests =============================================================================
    @Test
    public void addArchive_nullArchive_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.addArchive(null));
    }

    @Test
    public void addArchive_validArchive_success() {
        Expense taxes = new ExpenseBuilder().withArchiveName(VALID_ARCHIVE_TAXES).build();
        archiveWrapper.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>(Arrays.asList(taxes))));
        ArchiveWrapper expectedArchiveWrapper = new ArchiveWrapper(new ArrayList<>(
                Arrays.asList(IPHONE11, GUCCI_SLIDES, KPOP_LIGHT_STICK, FOOTBALL, taxes)));

        assertEquals(expectedArchiveWrapper, archiveWrapper);
    }

    @Test
    public void addArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.addArchiveExpense(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void addArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.addArchiveExpense(null, TAXES));
    }

    // remove tests =============================================================================

    @Test
    public void removeArchive_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.removeArchive(null));
    }

    @Test
    public void removeArchive_existingArchiveName_success() {
        archiveWrapper.removeArchive("luxury");
        ArchiveWrapper expectedArchiveWrapper = new ArchiveWrapper(
                new ArrayList<>(Arrays.asList(KPOP_LIGHT_STICK, FOOTBALL)));
        assertEquals(expectedArchiveWrapper, archiveWrapper);
    }

    @Test
    public void removeArchive_nonExistentArchiveName_success() {
        archiveWrapper.removeArchive(VALID_ARCHIVE_TAXES);
        ArchiveWrapper expectedArchiveWrapper = new ArchiveWrapper(getTypicalArchiveExpenses());
        assertEquals(expectedArchiveWrapper, archiveWrapper);
    }

    @Test
    public void removeArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.removeArchiveExpense(null, KPOP_LIGHT_STICK));
    }

    @Test
    public void removeArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.removeArchiveExpense("luxury", null));
    }

    @Test
    public void removeArchiveExpense_validArchiveNameAndExpense_throwsNullPointerException() {
        archiveWrapper.removeArchiveExpense("hobbies", KPOP_LIGHT_STICK);
        ArchiveWrapper expectedArchiveWrapper = new ArchiveWrapper(
                new ArrayList<>(Arrays.asList(IPHONE11, GUCCI_SLIDES, FOOTBALL)));
        assertEquals(expectedArchiveWrapper, archiveWrapper);
    }

    @Test
    public void removeArchiveExpense_nonExistentExpenseInExistingArchive_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> archiveWrapper
                .removeArchiveExpense("luxury", KPOP_LIGHT_STICK));
    }

    @Test
    public void removeArchiveExpense_nonExistentArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper
                .removeArchiveExpense(VALID_ARCHIVE_TAXES, KPOP_LIGHT_STICK));
    }

    // other tests =============================================================================
    @Test
    public void getArchiveExpenses_modifyList_throwsUnsupportedOperationException() {
        archiveWrapper.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        archiveWrapper.addArchiveExpense(VALID_ARCHIVE_TAXES, TAXES);
        assertThrows(UnsupportedOperationException.class, () ->
                archiveWrapper.getArchiveExpenses(VALID_ARCHIVE_TAXES).remove(0));
    }

    @Test
    public void getArchiveNames_validArchiveNames_success() {
        assertEquals(getTypicalArchiveWrapper().getArchiveNames(), archiveWrapper.getArchiveNames());
    }

    @Test
    public void getExpenseList_nonEmptyArchives_success() {
        assertEquals(getTypicalArchiveWrapper().getExpenseList(), archiveWrapper.getExpenseList());
    }

    @Test
    public void getExpenseList_emptyArchives_success() {
        ArchiveWrapper newData = new ArchiveWrapper(new ArrayList<>());
        archiveWrapper.resetData(newData);
        assertEquals(new ArrayList<>(), archiveWrapper.getExpenseList());
    }

    @Test
    public void getArchiveNames_noArchiveNames_success() {
        ArchiveWrapper newData = new ArchiveWrapper(new ArrayList<>());
        archiveWrapper.resetData(newData);
        assertEquals(new HashSet<>(), newData.getArchiveNames());
    }

    @Test
    public void getClone_success() {
        ArchiveWrapper aw = getTypicalArchiveWrapper();
        ArchiveWrapper cloned = aw.getClone();
        assertTrue(aw.equals(cloned));
    }

}
