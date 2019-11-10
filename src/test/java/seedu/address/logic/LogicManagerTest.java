package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOOK_1;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.borrowerrecords.JsonBorrowerRecordsStorage;
import seedu.address.storage.catalog.JsonCatalogStorage;
import seedu.address.storage.loanrecords.JsonLoanRecordsStorage;
import seedu.address.testutil.BookBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCatalogStorage catalogStorage =
                new JsonCatalogStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonLoanRecordsStorage loanRecordsStorage =
                new JsonLoanRecordsStorage(temporaryFolder.resolve("loanRecords.json"));
        JsonBorrowerRecordsStorage borrowerRecordsStorage =
                new JsonBorrowerRecordsStorage(temporaryFolder.resolve("borrowerRecords.json"));
        StorageManager storage = new StorageManager(
                userPrefsStorage, loanRecordsStorage, catalogStorage, borrowerRecordsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonCatalogIoExceptionThrowingStub
        JsonCatalogStorage catalogStorage =
                new JsonCatalogIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCatalog.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonBorrowerRecordsStorage borrowerRecordsStorage =
                new JsonBorrowerRecordsStorage(temporaryFolder.resolve("ioExceptionBorrowerRecords.json"));
        JsonLoanRecordsStorage loanRecordsStorage =
                new JsonLoanRecordsStorage(temporaryFolder.resolve("ioExceptionLoanRecords.json"));
        StorageManager storage = new StorageManager(
                userPrefsStorage, loanRecordsStorage, catalogStorage, borrowerRecordsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + TITLE_DESC_BOOK_1 + SERIAL_NUMBER_DESC_BOOK_1
                + AUTHOR_DESC_BOOK_1;
        Book expectedBook = new BookBuilder(BOOK_1).withGenres().build();
        ReversibleCommand expectedAddCommand = new AddCommand(expectedBook);
        ModelManager expectedModel = new ModelManager();
        expectedModel.addBook(expectedBook);
        expectedModel.commitCommand(expectedAddCommand);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredBookList().remove(0));
    }

    @Test
    public void getCatalog_success() {
        assertEquals(logic.getCatalog(), model.getCatalog());
    }

    @Test
    public void getFilteredBookList_success() {
        assertEquals(logic.getFilteredBookList(), model.getFilteredBookList());
    }

    @Test
    public void getCatalogFilePath_success() {
        assertEquals(logic.getCatalogFilePath(), model.getCatalogFilePath());
    }

    @Test
    public void getGuiSettings_success() {
        assertEquals(logic.getGuiSettings(), model.getGuiSettings());
    }

    @Test
    public void setGuiSettings_success() {
        GuiSettings guiSettings = new GuiSettings(2.2, 2.2, 1, 3, false);
        model.setGuiSettings(guiSettings);
        assertEquals(logic.getGuiSettings(), guiSettings);
        assertNotEquals(logic.getGuiSettings(), new GuiSettings());
    }
    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(
                model.getCatalog(), model.getLoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonCatalogIoExceptionThrowingStub extends JsonCatalogStorage {
        private JsonCatalogIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCatalog(ReadOnlyCatalog addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }

    }
}
