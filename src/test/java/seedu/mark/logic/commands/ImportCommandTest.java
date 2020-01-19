package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;
import static seedu.mark.testutil.TypicalBookmarks.CARL;
import static seedu.mark.testutil.TypicalBookmarks.DANIEL;
import static seedu.mark.testutil.TypicalBookmarks.ELLE;
import static seedu.mark.testutil.TypicalBookmarks.FIONA;
import static seedu.mark.testutil.TypicalBookmarks.GEORGE;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarks;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalFolderStructure;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

/**
 * Contains integration tests (interaction with the Model) for {@code ImportCommand}.
 */
public class ImportCommandTest {

    private static final Path PATH_NON_EXISTENT_FILE = Path.of("data", "bookmarks", "nonExistentFile");
    private static final Path PATH_INVALID_FORMAT_FILE = Path.of("invalidFormatFile");
    private static final Path PATH_PROBLEM_FILE = Path.of("problemFile");
    private static final Path PATH_VALID_FILE = Path.of("data", "validFile");
    private static final Path PATH_NO_FOLDER_FILE = Path.of("data", "validFileNoFolders");
    private static final Path PATH_NO_BOOKMARK_FILE = Path.of("data", "validFileNoBookmarks");

    private Model model = new ModelManager(new Mark(), new UserPrefs());
    private Storage storage = new StorageStubAllowsRead();

    /**
     * Converts the list of bookmarks into a multi-line String, where each
     * line has 4 spaces of indentation.
     */
    private static String makeIndentedString(List<Bookmark> bookmarks) {
        String newlineAndIndent = "\n    ";
        return bookmarks.stream().map(Bookmark::toString)
                .map(newlineAndIndent::concat)
                .reduce("", String::concat);
    }

    /**
     * Sets the {@code Folder} of all bookmarks in the given list to the import folder.
     */
    private static List<Bookmark> setFolderAsImportFolder(List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(ImportCommand.MarkImporter::setFolderAsImportFolder)
                .collect(Collectors.toList());
    }

    /**
     * Sets the {@code Folder} of all bookmarks in the given list to the root folder.
     */
    private static List<Bookmark> setFolderAsRootFolder(List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(bookmark -> new Bookmark(bookmark.getName(), bookmark.getUrl(), bookmark.getRemark(),
                        Folder.ROOT_FOLDER, bookmark.getTags(), bookmark.getCachedCopies()))
                .collect(Collectors.toList());
    }

    @Test
    public void execute_invalidFile_exceptionThrown() {
        // file does not exist
        Path filePath = PATH_NON_EXISTENT_FILE;
        ImportCommand command = new ImportCommand(filePath);
        String expectedMessage = String.format(ImportCommand.MESSAGE_FILE_NOT_FOUND, filePath);
        assertCommandFailure(command, model, storage, expectedMessage);

        // file contains wrong data format
        filePath = PATH_INVALID_FORMAT_FILE;
        command = new ImportCommand(filePath);
        expectedMessage = String.format(ImportCommand.MESSAGE_FILE_FORMAT_INCORRECT, filePath);
        assertCommandFailure(command, model, storage, expectedMessage);

        // problem while reading file
        filePath = PATH_PROBLEM_FILE;
        command = new ImportCommand(filePath);
        assertCommandFailure(command, model, storage, ImportCommand.MESSAGE_IMPORT_FAILURE);
    }

    @Test
    public void execute_validFileEmptyMark_success() {
        Path filePath = PATH_VALID_FILE;
        ImportCommand command = new ImportCommand(filePath);

        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, filePath);

        // set up expected model with appropriate state
        Model expectedModel = new ModelManager(new Mark(), new UserPrefs());
        Mark expectedMark = new Mark();
        expectedMark.setBookmarks(setFolderAsImportFolder(getTypicalBookmarks())); // strip folders
        expectedMark.addFolder(Folder.IMPORT_FOLDER, Folder.ROOT_FOLDER);
        expectedModel.setMark(expectedMark);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(command, model, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFileAllDuplicates_modelNotChanged() {
        ImportCommand command = new ImportCommand(PATH_VALID_FILE);

        Model initialModel = new ModelManager(getTypicalMark(), new UserPrefs());

        String expectedMessage = String.format(ImportCommand.MESSAGE_NO_BOOKMARKS_TO_IMPORT,
                makeIndentedString(getTypicalBookmarks()));
        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

        assertCommandSuccess(command, initialModel, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFileNoBookmarksToImport_modelNotChanged() {
        ImportCommand command = new ImportCommand(PATH_NO_BOOKMARK_FILE);

        Model initialModel = new ModelManager(getTypicalMark(), new UserPrefs());

        String expectedMessage = String.format(ImportCommand.MESSAGE_NO_BOOKMARKS_TO_IMPORT, "");
        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

        assertCommandSuccess(command, initialModel, storage, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFileDuplicateBookmarksNoFolders_duplicatesSkipped() {
        Path filePath = PATH_NO_FOLDER_FILE;
        ImportCommand command = new ImportCommand(filePath);

        // initial model: 3 bookmarks in root folder
        List<Bookmark> existingBookmarks = setFolderAsRootFolder(Arrays.asList(ALICE, BENSON, CARL));
        Mark markWithSomeBookmarks = new Mark();
        existingBookmarks.forEach(markWithSomeBookmarks::addBookmark);
        Model initialModel = new ModelManager(markWithSomeBookmarks, new UserPrefs());

        // expected message
        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS_WITH_DUPLICATES, filePath,
                makeIndentedString(existingBookmarks));

        // expected model: 3 bookmarks in root folder, 4 bookmarks in import folder
        Model expectedModel = new ModelManager(markWithSomeBookmarks, new UserPrefs());
        Mark expectedMark = new Mark();
        List<Bookmark> expectedBookmarks = existingBookmarks.stream().collect(Collectors.toList());
        expectedBookmarks.addAll(setFolderAsImportFolder(Arrays.asList(DANIEL, ELLE, FIONA, GEORGE)));
        expectedMark.setBookmarks(expectedBookmarks);
        expectedMark.addFolder(Folder.IMPORT_FOLDER, Folder.ROOT_FOLDER);
        expectedModel.setMark(expectedMark);
        expectedModel.saveMark(expectedMessage);

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
     * A Storage Stub that allows readMark(Path) to be called.
     */
    public static final class StorageStubAllowsRead extends StorageStub {
        @Override
        public Optional<ReadOnlyMark> readMark(Path filePath) throws IOException, DataConversionException {
            // note: should match test case #execute_invalidFile_exceptionThrown()
            if (filePath.equals(PATH_PROBLEM_FILE)) {
                throw new IOException();
            } else if (filePath.equals(PATH_INVALID_FORMAT_FILE)) {
                throw new DataConversionException(new CommandException("Invalid data format"));
            } else if (filePath.equals(PATH_NON_EXISTENT_FILE)) {
                return Optional.empty();
            } else if (filePath.equals(PATH_VALID_FILE)) {
                return Optional.of(getTypicalMark());
            } else if (filePath.equals(PATH_NO_FOLDER_FILE)) {
                Mark mark = new Mark();
                mark.setBookmarks(setFolderAsRootFolder(getTypicalBookmarks()));
                return Optional.of(mark);
            } else if (filePath.equals(PATH_NO_BOOKMARK_FILE)) {
                Mark mark = new Mark();
                mark.setFolderStructure(getTypicalFolderStructure());
                return Optional.of(mark);
            } else {
                throw new AssertionError("This method should be called with a specific type of path.");
            }
        }
    }
}
