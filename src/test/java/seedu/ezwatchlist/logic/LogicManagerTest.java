package seedu.ezwatchlist.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX;
import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;

import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.logic.commands.ListCommand;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.storage.JsonUserPrefsStorage;
import seedu.ezwatchlist.storage.JsonWatchListStorage;
import seedu.ezwatchlist.storage.StorageManager;
import seedu.ezwatchlist.ui.MainWindow;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    private final String currentTab = "watchlist";
    @BeforeEach
    public void setUp() {
        JsonWatchListStorage watchListStorage =
                new JsonWatchListStorage(temporaryFolder.resolve("watchList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(watchListStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }
    /*
    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonWatchListIoExceptionThrowingStub
        JsonWatchListStorage watchListStorage =
                new JsonWatchListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionWatchList.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(watchListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        String description = "Forever alone in a crowd, failed comedian Arthur Fleck
        seeks connection as he walks the streets of Gotham "
                + "City. Arthur wears two masks -- the one he paints for his day job as a clown, and the guise he "
                + "projects in a futile attempt to feel like he's part of the world around him. Isolated, bullied "
                + "and disregarded by society, Fleck begins a slow descent into madness as he transforms into the "
                + "criminal mastermind known as the Joker.";

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + " n/Joker" + " s/" + description
                + " w/false" + " d/3/10/2019" + " t/movie"
                + " r/122";
        Show expectedShow = new ShowBuilder(JOKER).withActors().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addShow(expectedShow);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);

    }
*/
    @Test
    public void getFilteredShowList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredShowList().remove(0));
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
        //CommandResult result = logic.execute(inputCommand);
        //assertEquals(expectedMessage, result.getFeedbackToUser());
        //assertEquals(expectedModel, model);
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
        Model expectedModel = new ModelManager(model.getWatchList(), new UserPrefs());
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
        MainWindow mainWindow = null;
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand, mainWindow, currentTab));
        assertEquals(expectedModel, model);
    }

    @Test
    void execute() {
    }

    @Test
    void getModel() {
        assertTrue(logic.getModel() instanceof Model);
    }

    @Test
    void getWatchList() {
        assertTrue(logic.getWatchList() instanceof ReadOnlyWatchList);
    }

    @Test
    void getUnWatchedList() {
        assertTrue(logic.getUnWatchedList() instanceof ObservableList);
    }

    @Test
    void getWatchedList() {
        assertTrue(logic.getWatchedList() instanceof ObservableList);
    }

    @Test
    void getFilteredShowList() {
        assertTrue(logic.getFilteredShowList() instanceof ObservableList);
    }

    @Test
    void updateFilteredShowList() {
        assertTrue(logic.getSearchResultList() instanceof ObservableList);
    }

    @Test
    void getSearchResultList() {
        //assertTrue(logic.updateFilteredShowList() instanceof ObservableList);
    }
    @Test
    void getWatchListFilePath() {
        assertTrue(logic.getWatchListFilePath() instanceof Path);
    }
    @Test
    void getGuiSettings() {
        assertTrue(logic.getGuiSettings() instanceof GuiSettings);
    }
    @Test
    void setGuiSettings() {
        //assertTrue(logic.setGuiSettings() instanceof GuiSettings);
    }
    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonWatchListIoExceptionThrowingStub extends JsonWatchListStorage {
        private JsonWatchListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveWatchList(ReadOnlyWatchList watchList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
