package seedu.system.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.system.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.system.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.system.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.system.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.system.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.system.testutil.Assert.assertThrows;
import static seedu.system.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.commands.outofsession.AddPersonCommand;
import seedu.system.logic.commands.outofsession.ListPersonCommand;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;
import seedu.system.model.person.Person;
import seedu.system.storage.JsonSerializableData;
import seedu.system.storage.JsonSystemStorage;
import seedu.system.storage.JsonUserPrefsStorage;
import seedu.system.storage.StorageManager;
import seedu.system.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonSystemStorage jsonSystemStorage =
            new JsonSystemStorage(
                temporaryFolder.resolve("tempPersons.json"),
                temporaryFolder.resolve("tempCompetitions.json"),
                temporaryFolder.resolve("tempParticipations.json")
            );
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(jsonSystemStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deletePerson 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListPersonCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListPersonCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonSystemIoExceptionThrowingStub
        JsonSystemStorage addressBookStorage =
            new JsonSystemIoExceptionThrowingStub(
                temporaryFolder.resolve("ioExceptionPersons.json"),
                temporaryFolder.resolve("ioExceptionCompetitions.json"),
                temporaryFolder.resolve("ioExceptionParticipations.json")
            );
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddPersonCommand.COMMAND_WORD + NAME_DESC_AMY + DOB_DESC_AMY + GENDER_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
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
        Model expectedModel =
            new ModelManager(model.getPersons(), model.getCompetitions(), model.getParticipations(), new UserPrefs());
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
    private static class JsonSystemIoExceptionThrowingStub extends JsonSystemStorage {
        private JsonSystemIoExceptionThrowingStub(
            Path personDataFilePath,
            Path competitionDataFilePath,
            Path participationDataFilePath
        ) {
            super(personDataFilePath, competitionDataFilePath, participationDataFilePath);
        }

        @Override
        public void saveData(JsonSerializableData jsonSerializableData, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
