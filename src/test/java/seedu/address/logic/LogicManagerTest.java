package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.patients.ListPatientCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.person.Person;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    public static final String DUMMY_IO_EXCEPTION_MESSAGE = "dummy exception";

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        StorageManager storage = new StorageManager(temporaryFolder.resolve("userPrefs.json"));
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListPatientCommand.COMMAND_WORD;
        String expectedResult = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0, "");
        assertCommandSuccess(listCommand, expectedResult, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        logic = new LogicManager(model, new StorageStub());

        // Execute add command
        String addCommand = RegisterPatientCommand.COMMAND_WORD
            + ID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPatient(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION_MESSAGE;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPatientList().remove(0));
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
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager();
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
}

class StorageStub implements Storage {

    @Override
    public UserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getPatientAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getPatientAppointmentBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getStaffAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getStaffDutyRosterBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyAddressBook> readPatientAddressBook() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void savePatientAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        throw new IOException(LogicManagerTest.DUMMY_IO_EXCEPTION_MESSAGE);
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readPatientAppointmentBook() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void savePatientAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        throw new IOException(LogicManagerTest.DUMMY_IO_EXCEPTION_MESSAGE);

    }

    @Override
    public Optional<ReadOnlyAddressBook> readStaffAddressBook() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveStaffAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        throw new IOException(LogicManagerTest.DUMMY_IO_EXCEPTION_MESSAGE);
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readStaffDutyRosterBook() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveStaffDutyRosterBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        throw new IOException(LogicManagerTest.DUMMY_IO_EXCEPTION_MESSAGE);
    }
}
