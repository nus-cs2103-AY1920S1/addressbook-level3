package seedu.module.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.module.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.module.logic.commands.DeleteCommand.MESSAGE_MODULE_NOT_FOUND;
import static seedu.module.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.module.logic.commands.AddCommand;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.ListCommand;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.UserPrefs;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.Module;
import seedu.module.model.module.TrackedModule;
import seedu.module.storage.JsonModuleBookStorage;
import seedu.module.storage.JsonUserPrefsStorage;
import seedu.module.storage.StorageManager;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ModuleBookBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;

    @BeforeEach
    public void beforeEach() {
        // Set up model
        model = new ModelManager();
        model.setModuleBook(new ModuleBookBuilder().build());

        // Set up storage
        JsonModuleBookStorage moduleBookStorage =
                new JsonModuleBookStorage(temporaryFolder.resolve("moduleBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(moduleBookStorage, userPrefsStorage);

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
        assertCommandException(deleteCommand, MESSAGE_MODULE_NOT_FOUND);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Set up model
        ArchivedModuleList archivedModules = new ArchivedModuleList();
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        archivedModules.add(archivedModule);
        ModuleBook moduleBook = new ModuleBookBuilder().build();

        // Setup LogicManager with JsonmoduleBookIoExceptionThrowingStub
        JsonModuleBookStorage moduleBookStorage =
                new JsonModuleBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionModuleBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(moduleBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + " " + archivedModule.getModuleCode();
        TrackedModule expectedModule = new TrackedModule(archivedModule);
        ModelManager expectedModel = new ModelManager();
        expectedModel.setModuleBook(moduleBook);
        expectedModel.addModule(expectedModule);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    // TODO: Fix this test
    @Disabled("Archived List is modifiable!")
    @Test
    public void getDisplayedList_modifyList_throwsUnsupportedOperationException() {
        model.displayArchivedList();
        assertThrows(UnsupportedOperationException.class, () -> logic.getDisplayedList().remove(0));
    }

    @Test
    public void getDisplayedModule_withoutSet_returnEmptyOptional() {
        Optional<Module> expectedModule = Optional.empty();
        assertEquals(expectedModule, model.getDisplayedModule());
    }

    @Test
    public void getDisplayedModule_withSet_returnModule() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        Optional<Module> expectedModule = Optional.of(archivedModule);

        model.setDisplayedModule(archivedModule);
        assertEquals(expectedModule, model.getDisplayedModule());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
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
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getModuleBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
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
    private static class JsonModuleBookIoExceptionThrowingStub extends JsonModuleBookStorage {
        private JsonModuleBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveModuleBook(ReadOnlyModuleBook moduleBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
