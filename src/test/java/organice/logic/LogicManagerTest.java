package organice.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_DOCTOR_AMY;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_AMY;
import static organice.testutil.TypicalPersons.DONOR_ELLE;
import static organice.testutil.TypicalPersons.DONOR_FIONA;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_CARL;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import organice.logic.commands.AddCommand;
import organice.logic.commands.CommandResult;
import organice.logic.commands.ListCommand;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.ReadOnlyAddressBook;
import organice.model.UserPrefs;
import organice.model.person.MatchedDonor;
import organice.model.person.MatchedPatient;
import organice.model.person.Person;
import organice.storage.JsonAddressBookStorage;
import organice.storage.JsonUserPrefsStorage;
import organice.storage.StorageManager;
import organice.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.LIST_OF_PERSONS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
                + PHONE_DESC_DOCTOR_AMY;
        Person expectedPerson = new PersonBuilder(DOCTOR_AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getMatchList_matchDonors_allContentsAreMatches() {
        model.addPerson(DONOR_IRENE_DONOR);
        model.addPerson(PATIENT_IRENE);
        model.addPerson(DONOR_ELLE);
        model.addPerson(DONOR_FIONA);

        model.matchDonors(PATIENT_IRENE);
        ObservableList<Person> listOfMatches = model.getMatchList();
        boolean isAllMatchedDonor = listOfMatches.stream().allMatch(match -> match instanceof MatchedDonor);

        assertTrue(isAllMatchedDonor);
    }

    @Test
    public void getMatchList_matchAllPatients_allContentsAreMatches() {
        model.addPerson(DONOR_IRENE_DONOR);
        model.addPerson(PATIENT_IRENE);
        model.addPerson(PATIENT_CARL);
        model.addPerson(DONOR_ELLE);
        model.addPerson(DONOR_FIONA);

        model.matchAllPatients();
        ObservableList<Person> listOfMatches = model.getMatchList();
        boolean isAllMatchedPatient = listOfMatches.stream().allMatch(match -> match instanceof MatchedPatient);

        assertTrue(isAllMatchedPatient);
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
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
