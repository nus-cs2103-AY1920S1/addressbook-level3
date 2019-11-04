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
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.ReadOnlyAppSettings;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.storage.StorageManager;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.appsettings.JsonAppSettingsStorage;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.globalstatistics.JsonGlobalStatisticsStorage;
import seedu.address.storage.statistics.JsonWordBankStatisticsListStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.JsonUserPrefsStorage;
import seedu.address.storage.wordbanks.JsonWordBankListStorage;

class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    private Path temporaryFolder =
            Paths.get("src", "test", "data", "LogicManagerTest");


    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    void setUp() throws DataConversionException, IllegalValueException {
        JsonWordBankListStorage wordBankListStorage =
                new JsonWordBankListStorage(temporaryFolder, true);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        WordBankStatisticsListStorage wordBankStatisticsListStorage =
                new JsonWordBankStatisticsListStorage(Path.of("dummyWbStats"));
        GlobalStatisticsStorage globalStatisticsStorage =
                new JsonGlobalStatisticsStorage(Path.of("dummyWbStats"));
        AppSettingsStorage appSettingsStorage =
                new JsonAppSettingsStorage(Path.of("dummyWbStats"));
        StorageManager storage = new StorageManager(wordBankListStorage,
                userPrefsStorage, wordBankStatisticsListStorage,
                globalStatisticsStorage, appSettingsStorage);

        WordBankList wordBankList = (WordBankList) wordBankListStorage.getWordBankList().get();
        WordBankStatisticsList wordBankStatisticsList = wordBankStatisticsListStorage.getWordBankStatisticsList();
        GlobalStatistics globalStatistics = globalStatisticsStorage.getGlobalStatistics();
        ReadOnlyUserPrefs readOnlyUserPrefs = new UserPrefs();
        ReadOnlyAppSettings readOnlyAppSettings = new AppSettings();

        model = new ModelManager(wordBankList, wordBankStatisticsList,
                globalStatistics, readOnlyUserPrefs, readOnlyAppSettings);
        logic = new LogicManager(model, storage);
    }

    @Test
    void execute_invalidCommandFormat_throwsParseException() {
        /** SHA-256 hash of "invalid command". */
        String invalidCommand = "8A1954FCC1065A01AE1A6F3527120EA90E3F4BDF262A4A0A0D41374572BEE66E";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }


    @Test
    void execute_commandExecutionError_throwsCommandException() {
        /** SHA-256 hash of "invalid bank. */
        String invalidBankName = "B0C7AE034AB804B9EB28E787D4CB76761FAE47377CE81062647F2084F7EB2D79";

        String bankCommand =
                "select " + invalidBankName;

        // Attempting to load a non-existent bank.
        assertCommandException(bankCommand, MESSAGE_INVALID_WORD_BANK_NAME);
    }

    @Test
    void execute_validCommand_success() throws Exception {
        String bankSample = "select sample";
        logic.execute(bankSample);
        String homeCommand = SwitchToHomeCommand.COMMAND_WORD;
        String openCommand = SwitchToOpenCommand.COMMAND_WORD;
        CommandResult homeResult = logic.execute(homeCommand);
        assertEquals(SwitchToHomeCommand.MESSAGE_HOME_ACKNOWLEDGEMENT, homeResult.getFeedbackToUser());
        CommandResult openResult = logic.execute(openCommand);
        assertEquals(String.format(SwitchToOpenCommand.MESSAGE_HOME_ACKNOWLEDGEMENT,
                model.getCurrentWordBank()), openResult.getFeedbackToUser());
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
    //        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredCardList().remove(0));
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
        private JsonWordBankListIoExceptionThrowingStub(Path filePath, boolean isSampleCreated)
                throws DataConversionException, IllegalValueException {
            super(filePath, true);
        }

        private void saveWordBanks(ReadOnlyWordBank addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
