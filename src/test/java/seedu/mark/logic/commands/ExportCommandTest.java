package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;
import seedu.mark.storage.Storage;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ExportCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
    private Storage storage = new StorageStubAllowsSave();

    @Test
    public void execute_validFile_success() {
        Path filePath = Path.of("data", "myValidFile");
        ExportCommand command = new ExportCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);
        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unableToSave_exceptionThrown() {
        // simulate problem when saving to file
        ExportCommand command = new ExportCommand(Path.of("problem"));
        assertCommandFailure(command, model, storage, ExportCommand.MESSAGE_EXPORT_FAILURE);
    }

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

    /**
     * A Storage Stub that allows saveMark to be called.
     */
    public static final class StorageStubAllowsSave implements Storage {
        @Override
        public Path getUserPrefsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMarkFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<ReadOnlyMark> readMark() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<ReadOnlyMark> readMark(Path filePath) throws IOException, DataConversionException {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void saveMark(ReadOnlyMark mark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveMark(ReadOnlyMark mark, Path filePath) throws IOException {
            requireNonNull(mark);
            requireNonNull(filePath);

            // test handling of IOException
            if (filePath.endsWith("problem")) {
                throw new IOException();
            }
        }
    }
}
