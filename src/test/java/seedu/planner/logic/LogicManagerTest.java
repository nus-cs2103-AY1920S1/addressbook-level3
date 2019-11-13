package seedu.planner.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.planner.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.contact.TypicalContacts.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.listcommand.ListContactCommand;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.parser.exceptions.ParseException;

import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.contact.Contact;
import seedu.planner.storage.JsonUserPrefsStorage;
import seedu.planner.storage.StorageManager;
import seedu.planner.storage.accommodation.JsonAccommodationStorage;
import seedu.planner.storage.activity.JsonActivityStorage;
import seedu.planner.storage.contact.JsonContactStorage;
import seedu.planner.storage.day.JsonItineraryStorage;
import seedu.planner.testutil.contact.ContactBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAccommodationStorage accommodationStorage =
                new JsonAccommodationStorage(temporaryFolder.resolve("accommodation.json"));
        JsonActivityStorage activityStorage =
                new JsonActivityStorage(temporaryFolder.resolve("activity.json"));
        JsonContactStorage contactStorage =
                new JsonContactStorage(temporaryFolder.resolve("contact.json"));
        JsonItineraryStorage itineraryStorage =
                new JsonItineraryStorage(temporaryFolder.resolve("itinerary.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(accommodationStorage, activityStorage, contactStorage,
                itineraryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    /*
    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteContactCommand = "delete " + DeleteContactCommand.SECOND_COMMAND_WORD + " 9";
        assertCommandException(deleteContactCommand, MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }
    */

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListContactCommand.COMMAND_WORD + " " + ListContactCommand.SECOND_COMMAND_WORD;
        assertCommandSuccess(listCommand, ListContactCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAccommodationIoExceptionThrowingStub
        JsonAccommodationStorage accommodationStorage =
                new JsonAccommodationIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAccommodation.json"));
        JsonActivityStorage activityStorage =
                new JsonActivityStorage(temporaryFolder.resolve("ioExceptionActivity.json"));
        JsonContactStorage contactStorage =
                new JsonContactStorage(temporaryFolder.resolve("ioExceptionContact.json"));
        JsonItineraryStorage itineraryStorage =
                new JsonItineraryStorage(temporaryFolder.resolve("ioExceptionItinerary.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(accommodationStorage, activityStorage, contactStorage,
                itineraryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addContactCommand = AddContactCommand.COMMAND_WORD + " " + AddContactCommand.SECOND_COMMAND_WORD + " "
                + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        Contact expectedContact = new ContactBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addContact(expectedContact);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addContactCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredContactList().remove(0));
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
        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), new UserPrefs());
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
     * A stub class to throw an {@code IOException} when the save method is called, for accommodation storage.
     */
    private static class JsonAccommodationIoExceptionThrowingStub extends JsonAccommodationStorage {
        private JsonAccommodationIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAccommodation(ReadOnlyAccommodation accommodation, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
