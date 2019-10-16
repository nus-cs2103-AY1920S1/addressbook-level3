package seedu.billboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.TAXES;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalArchiveExpenses;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalArchiveWrapper;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.archive.Archive;
import seedu.billboard.testutil.Assert;

public class ArchiveWrapperTest {

    private final ArchiveWrapper archiveWrapper = new ArchiveWrapper(getTypicalArchiveExpenses());

    @Test
    public void constructor_nullExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ArchiveWrapper(null));
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
    public void addArchive_nullExpense_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> archiveWrapper.addArchive(null));
    }

    @Test
    public void addArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.addArchiveExpense(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void addArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveWrapper.addArchiveExpense(null, TAXES));
    }


    // other tests =============================================================================
    @Test
    public void getArchiveExpenseList_modifyList_throwsUnsupportedOperationException() {
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

}
