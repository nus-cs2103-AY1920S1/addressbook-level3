package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ExportCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void equals() {
        Path firstFilePath = Path.of("data");
        Path secondFilePath = Path.of("data", "two", "three");

        ExportCommand exportFirstCommand = new ExportCommand(firstFilePath);
        ExportCommand exportSecondCommand = new ExportCommand(secondFilePath);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        ExportCommand exportFirstCommandCopy = new ExportCommand(firstFilePath);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    // TODO: add more tests for execute (e.g. invalidPath, validPath)
}
