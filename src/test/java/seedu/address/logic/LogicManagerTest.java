package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_WORD_BANK_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.switches.SwitchToHomeCommand;
import seedu.address.logic.commands.switches.SwitchToOpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.storage.StorageManager;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.appsettings.JsonAppSettingsStorage;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.globalstatistics.JsonGlobalStatisticsStorage;
import seedu.address.storage.statistics.JsonWordBankStatisticsListStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.JsonUserPrefsStorage;
import seedu.address.storage.wordbanks.JsonWordBankListStorage;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    private Path temporaryFolder =
            Paths.get("src", "test", "data", "LogicManagerTest");


    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() throws DataConversionException, IllegalValueException {
        JsonWordBankListStorage addressBookStorage =
                new JsonWordBankListStorage(temporaryFolder);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        WordBankStatisticsListStorage wordBankStatisticsListStorage =
               new JsonWordBankStatisticsListStorage(Path.of("dummyWbStats"));
        GlobalStatisticsStorage globalStatisticsStorage =
               new JsonGlobalStatisticsStorage(Path.of("dummyWbStats"));
        AppSettingsStorage appSettingsStorage =
               new JsonAppSettingsStorage(Path.of("dummyWbStats"));
        StorageManager storage = new StorageManager(addressBookStorage,
                    userPrefsStorage, wordBankStatisticsListStorage,
                    globalStatisticsStorage, appSettingsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "8A1954FCC1065A01AE1A6F3527120EA90E3F4BDF262A4A0A0D41374572BEE66E";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }


    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String bankCommand =
                "bank 88F5D9517A2CBA49A301C160429580888B5D500BCB017C75A1F510AE5D4247E7";
        assertCommandException(bankCommand, MESSAGE_INVALID_WORD_BANK_NAME);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String bankSample = "bank sample";
        logic.execute(bankSample);
        String homeCommand = SwitchToHomeCommand.COMMAND_WORD;
        String openCommand = SwitchToOpenCommand.COMMAND_WORD;
        CommandResult homeResult = logic.execute(homeCommand);
        assertEquals(SwitchToHomeCommand.MESSAGE_HOME_ACKNOWLEDGEMENT, homeResult.getFeedbackToUser());
        CommandResult openResult = logic.execute(openCommand);
        assertEquals(SwitchToOpenCommand.MESSAGE_HOME_ACKNOWLEDGEMENT, openResult.getFeedbackToUser());
        assertEquals(model, model);
    }

    //    todo: storage test
    //    @Test
    //    public void execute_storageThrowsIoException_throwsCommandException() {
    //        // Setup LogicManager with JsonWordBankListIoExceptionThrowingStub
    //        JsonWordBankListStorage addressBookStorage = new JsonWordBankListIoExceptionThrowingStub(
    //                temporaryFolder.resolve("ioExceptionAddressBook.json"));
    //        JsonUserPrefsStorage userPrefsStorage =
    //                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
    //        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
    //        logic = new LogicManager(model, storage);
    //
    //        // Execute add command
    //        String addCommand = AddCommand.COMMAND_WORD + WORD_DESC_ABRA + MEANING_DESC_ABRA;
    //        Card expectedPerson = new CardBuilder(ABRA).withTags().build();
    //        ModelManager expectedModel = new ModelManager();
    //        expectedModel.addWordBank(expectedPerson);
    //        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
    //        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    //  }

    //    @Test
    //    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
    //        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    //    }


    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    /*
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {

    }
    */

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
        Model expectedModel = new ModelManager();
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonWordBankListIoExceptionThrowingStub extends JsonWordBankListStorage {
        private JsonWordBankListIoExceptionThrowingStub(Path filePath)
                throws DataConversionException, IllegalValueException {
            super(filePath);
        }

        private void saveWordBanks(ReadOnlyWordBank addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
