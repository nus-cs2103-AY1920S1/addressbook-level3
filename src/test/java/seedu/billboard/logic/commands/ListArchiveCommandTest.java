package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboardWithArchiveExpenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

public class ListArchiveCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboardWithArchiveExpenses(), new UserPrefs());
    }

    @Test
    public void constructor_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ListArchiveCommand(null));
    }

    @Test
    public void execute_invalidArchiveName_throwsCommandException() {
        ListArchiveCommand listArchiveCommand = new ListArchiveCommand("");
        assertCommandFailure(listArchiveCommand, model, Messages.MESSAGE_INVALID_ARCHIVE_NAME);
    }

    @Test
    public void execute_existingArchiveName_throwsCommandException() {
        ListArchiveCommand listArchiveCommand = new ListArchiveCommand("lalala");
        assertCommandFailure(listArchiveCommand, model, Messages.MESSAGE_NONEXISTENT_ARCHIVE_ENTERED);
    }

    @Test
    public void equals() {
        ListArchiveCommand firstListArchiveCommand = new ListArchiveCommand(VALID_ARCHIVE_TAXES);
        ListArchiveCommand secondListArchiveCommand = new ListArchiveCommand(VALID_ARCHIVE_DINNER);

        // same object -> returns true
        assertEquals(firstListArchiveCommand, firstListArchiveCommand);

        // same values -> returns true
        ListArchiveCommand firstListArchiveCommandCopy = new ListArchiveCommand(VALID_ARCHIVE_TAXES);
        assertEquals(firstListArchiveCommand, firstListArchiveCommandCopy);

        // different types -> returns false
        assertNotEquals(1, firstListArchiveCommand);

        // null -> returns false
        assertNotEquals(null, firstListArchiveCommand);

        // different expense -> returns false
        assertNotEquals(firstListArchiveCommand, secondListArchiveCommand);
    }
}
