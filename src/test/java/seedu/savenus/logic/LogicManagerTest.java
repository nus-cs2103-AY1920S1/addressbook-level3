package seedu.savenus.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX;
import static seedu.savenus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_CHICKEN_RICE;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CHICKEN_RICE;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.food.Food;
import seedu.savenus.storage.JsonMenuStorage;
import seedu.savenus.storage.JsonUserPrefsStorage;
import seedu.savenus.storage.StorageManager;
import seedu.savenus.testutil.FoodBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonMenuStorage addressBookStorage =
                new JsonMenuStorage(temporaryFolder.resolve("addressBook.json"));
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
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonMenuIoExceptionThrowingStub
        JsonMenuStorage addressBookStorage =
                new JsonMenuIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMenu.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_CHICKEN_RICE
                + PRICE_DESC_CHICKEN_RICE + DESCRIPTION_DESC_CHICKEN_RICE
                + CATEGORY_DESC_CHICKEN_RICE + LOCATION_DESC_CHICKEN_RICE
                + OPENING_HOURS_DESC_CHICKEN_RICE + RESTRICTIONS_DESC_CHICKEN_RICE;
        Food expectedFood = new FoodBuilder(CHICKEN_RICE).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addFood(expectedFood);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredfoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFoodList().remove(0));
    }

    @Test
    public void getMenu_succesfullGet() {
        assertTrue(logic.getMenu() instanceof ReadOnlyMenu);
    }

    @Test
    public void getPurchaseHistory_successfulGet() {
        assertTrue(logic.getPurchaseHistory() instanceof ObservableList);
    }

    @Test
    public void getMenuFilePath_successfulGet() {
        assertTrue(logic.getMenuFilePath() instanceof Path);
    }

    @Test
    public void getGuiSettings_successfulGet() {
        assertTrue(logic.getGuiSettings() instanceof GuiSettings);
    }

    @Test
    public void setGuiSettings_success() {
        Logic newLogic = logic;
        logic.setGuiSettings(logic.getGuiSettings());
        assertEquals(logic, newLogic);
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
        Model expectedModel = new ModelManager(model.getMenu(), new UserPrefs());
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
    private static class JsonMenuIoExceptionThrowingStub extends JsonMenuStorage {
        private JsonMenuIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMenu(ReadOnlyMenu addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
