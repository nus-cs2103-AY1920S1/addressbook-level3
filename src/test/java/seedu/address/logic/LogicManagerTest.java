package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.listcommand.ListCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.statistic.Statistic;
import seedu.address.statistic.StatisticManager;
import seedu.address.storage.JsonCustomerBookStorage;
import seedu.address.storage.JsonOrderBookStorage;
import seedu.address.storage.JsonPhoneBookStorage;
import seedu.address.storage.JsonScheduleBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Statistic statistic = new StatisticManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCustomerBookStorage customerBookStorage =
                new JsonCustomerBookStorage(temporaryFolder.resolve("customerBook.json"));
        JsonPhoneBookStorage phoneBookStorage =
                new JsonPhoneBookStorage(temporaryFolder.resolve("phoneBook.json"));
        JsonScheduleBookStorage scheduleBookStorage =
                new JsonScheduleBookStorage(temporaryFolder.resolve("scheduleBook.json"));
        JsonOrderBookStorage orderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("orderBook.json"));

        JsonOrderBookStorage archivedOrderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("archivedOrderBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));

        StorageManager storage = new StorageManager(customerBookStorage, phoneBookStorage,
                scheduleBookStorage, orderBookStorage, archivedOrderBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage, statistic);
        logic = new LogicManager(model, storage, new StatisticManager());
    }


    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_validCommand_success() throws CommandException, ParseException {
        String listCommand = ListCustomerCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCustomerCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
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
        Model expectedModel = new ModelManager(new UserPrefs());
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
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

}
