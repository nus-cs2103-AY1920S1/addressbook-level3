package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;
import static seedu.mark.testutil.TypicalBookmarks.CARL;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarks;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * Contains integration tests (interaction with the Model) for {@code ImportCommand}.
 */
public class ImportCommandTest {
    private Model model = new ModelManager(new Mark(), new UserPrefs());
    private Storage storage = new StorageStubAllowsRead();

    @Test
    public void execute_invalidFile_exceptionThrown() {
        // file does not exist
        Path filePath = Path.of("data", "bookmarks", "nonExistentFile");
        ImportCommand command = new ImportCommand(filePath);
        String expectedMessage = String.format(ImportCommand.MESSAGE_FILE_NOT_FOUND, filePath);
        assertCommandFailure(command, model, storage, expectedMessage);

        // file contains wrong data format
        filePath = Path.of("invalidFormatFile");
        command = new ImportCommand(filePath);
        expectedMessage = String.format(ImportCommand.MESSAGE_FILE_FORMAT_INCORRECT, filePath);
        assertCommandFailure(command, model, storage, expectedMessage);

        // problem while reading file
        filePath = Path.of("problemFile");
        command = new ImportCommand(filePath);
        assertCommandFailure(command, model, storage, ImportCommand.MESSAGE_IMPORT_FAILURE);
    }

    @Test
    public void execute_validFileNoDuplicates_success() {
        Path filePath = Path.of("data", "validFile");
        ImportCommand command = new ImportCommand(filePath);

        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, filePath);

        // set up expected model with appropriate state
        Model expectedModel = new ModelManager(new Mark(), new UserPrefs());
        Mark expectedMark = new Mark();
        expectedMark.setBookmarks(getTypicalBookmarks());
        expectedModel.setMark(expectedMark);
        expectedModel.saveMark();

        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFileSomeDuplicates_duplicatesSkipped() {
        Path filePath = Path.of("data", "validFile");
        ImportCommand command = new ImportCommand(filePath);

        List<Bookmark> existingBookmarks = Arrays.asList(ALICE, BENSON, CARL);
        Mark markWithSomeBookmarks = new Mark();
        existingBookmarks.forEach(markWithSomeBookmarks::addBookmark);
        Model initialModel = new ModelManager(markWithSomeBookmarks, new UserPrefs());

        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS_WITH_DUPLICATES, filePath,
                ImportCommand.toIndentedString(existingBookmarks));

        // set up expected model with appropriate state
        Model expectedModel = new ModelManager(markWithSomeBookmarks, new UserPrefs());
        Mark expectedMark = new Mark();
        expectedMark.setBookmarks(getTypicalBookmarks());
        expectedModel.setMark(expectedMark);
        expectedModel.saveMark();

        assertCommandSuccess(command, initialModel, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFileAllDuplicates_modelNotChanged() {
        Path filePath = Path.of("data", "validFile");
        ImportCommand command = new ImportCommand(filePath);

        Model initialModel = new ModelManager(getTypicalMark(), new UserPrefs());
        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS_WITH_DUPLICATES, filePath,
                ImportCommand.toIndentedString(getTypicalBookmarks()));

        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

        assertCommandSuccess(command, initialModel, storage, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Path firstFilePath = Path.of("data");
        Path secondFilePath = Path.of("data", "two", "three");

        ImportCommand importFirstCommand = new ImportCommand(firstFilePath);
        ImportCommand importSecondCommand = new ImportCommand(secondFilePath);

        // same object -> returns true
        assertTrue(importFirstCommand.equals(importFirstCommand));

        // same values -> returns true
        ImportCommand importFirstCommandCopy = new ImportCommand(firstFilePath);
        assertTrue(importFirstCommand.equals(importFirstCommandCopy));

        // different types -> returns false
        assertFalse(importFirstCommand.equals(1));

        // null -> returns false
        assertFalse(importFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(importFirstCommand.equals(importSecondCommand));
    }

    /**
     * A Storage Stub that allows readMark to be called.
     */
    public static final class StorageStubAllowsRead implements Storage {
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
            // note: should match test case #execute_invalidFile_exceptionThrown()
            if (filePath.endsWith("problemFile")) {
                throw new IOException();
            } else if (filePath.endsWith("invalidFormatFile")) {
                throw new DataConversionException(new CommandException("Invalid data format"));
            } else if (filePath.endsWith("nonExistentFile")) {
                return Optional.empty();
            }
            return Optional.of(getTypicalMark());
        }

        @Override
        public void saveMark(ReadOnlyMark mark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveMark(ReadOnlyMark mark, Path filePath) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
