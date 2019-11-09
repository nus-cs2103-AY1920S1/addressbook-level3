package calofit.logic;

import static calofit.commons.core.Messages.MESSAGE_INVALID_MEAL_INDEX;
import static calofit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import calofit.logic.commands.AddCommand;
import calofit.logic.commands.CommandResult;
import calofit.logic.commands.CommandTestUtil;
import calofit.logic.commands.ListCommand;
import calofit.logic.commands.exceptions.CommandException;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.model.dish.Dish;
import calofit.storage.CalorieBudgetStorage;
import calofit.storage.DishDatabaseStorage;
import calofit.storage.MealLogStorage;
import calofit.storage.StorageManager;
import calofit.storage.UserPrefsStorage;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        DishDatabaseStorage dishDatabaseStorage = Mockito.mock(DishDatabaseStorage.class);
        MealLogStorage mealLogStorage = Mockito.mock(MealLogStorage.class);
        UserPrefsStorage userPrefsStorage = Mockito.mock(UserPrefsStorage.class);
        CalorieBudgetStorage calorieBudgetStorage = Mockito.mock(CalorieBudgetStorage.class);
        StorageManager storage = new StorageManager(dishDatabaseStorage, mealLogStorage,
            userPrefsStorage, calorieBudgetStorage);
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
        assertCommandException(deleteCommand, String.format(MESSAGE_INVALID_MEAL_INDEX, 9));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonDishDatabaseIoExceptionThrowingStub
        DishDatabaseStorage dishDatabaseStorage = Mockito.mock(DishDatabaseStorage.class);
        try {
            Mockito.doThrow(DUMMY_IO_EXCEPTION).when(dishDatabaseStorage).saveDishDatabase(Mockito.any());
            Mockito.doThrow(DUMMY_IO_EXCEPTION).when(dishDatabaseStorage)
                .saveDishDatabase(Mockito.any(), Mockito.any());
        } catch (IOException e) {
            //This is an artifact of Mockito using the same method call to configure the object.
            //We are only configuring behaviour, not actually calling the method.
        }

        MealLogStorage mealLogStorage = Mockito.mock(MealLogStorage.class);
        UserPrefsStorage userPrefsStorage = Mockito.mock(UserPrefsStorage.class);
        CalorieBudgetStorage calorieBudgetStorage = Mockito.mock(CalorieBudgetStorage.class);

        StorageManager storage = new StorageManager(dishDatabaseStorage, mealLogStorage,
            userPrefsStorage, calorieBudgetStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + CommandTestUtil.NAME_DESC_DUCK_RICE
                + CommandTestUtil.CALORIE_DESC_1000;
        Dish expectedDish = new DishBuilder(TypicalDishes.DUCK_RICE).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addDish(expectedDish);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;

        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredDishList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDishList().remove(0));
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
        Model expectedModel = new ModelManager(model.getDishDatabase(), new UserPrefs());
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
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }
}
