package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboardWithArchiveExpenses;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddArchiveCommand}.
 */
public class AddArchiveCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboardWithArchiveExpenses(), new UserPrefs());
    }

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void constructor_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(null, INDEX_FIRST_EXPENSE));
    }

    @Test
    public void constructor_nullArchiveNameAndExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(null, null));
    }


    @Test
    public void execute_nullModel_throwsNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(VALID_ARCHIVE_TAXES,
                INDEX_FIRST_EXPENSE).execute(null));
    }

    @Test
    public void equals() {
        AddArchiveCommand firstAddArchiveCommand = new AddArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        AddArchiveCommand secondAddArchiveCommand = new AddArchiveCommand(VALID_ARCHIVE_DINNER, INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertEquals(firstAddArchiveCommand, firstAddArchiveCommand);

        // same values -> returns true
        AddArchiveCommand firstAddArchiveCommandCopy = new AddArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        assertEquals(firstAddArchiveCommand, firstAddArchiveCommandCopy);

        // different types -> returns false
        assertNotEquals(1, firstAddArchiveCommand);

        // null -> returns false
        assertNotEquals(null, firstAddArchiveCommand);

        // different expense -> returns false
        assertNotEquals(firstAddArchiveCommand, secondAddArchiveCommand);
    }
}
