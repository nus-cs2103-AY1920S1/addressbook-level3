package seedu.ifridge.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.storage.JsonGroceryListStorage;
import seedu.ifridge.storage.JsonTemplateListStorage;
import seedu.ifridge.storage.JsonUserPrefsStorage;
import seedu.ifridge.storage.StorageManager;
import seedu.ifridge.storage.shoppinglist.JsonBoughtItemStorage;
import seedu.ifridge.storage.shoppinglist.JsonShoppingItemStorage;
import seedu.ifridge.storage.wastelist.JsonWasteListStorage;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonGroceryListStorage groceryListStorage =
                new JsonGroceryListStorage(temporaryFolder.resolve("grocerylist.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonTemplateListStorage templateListStorage =
                new JsonTemplateListStorage(temporaryFolder.resolve("templateList.json"));
        JsonWasteListStorage wasteListStorage =
                new JsonWasteListStorage(temporaryFolder.resolve("wastelist.json"));
        JsonShoppingItemStorage shoppingListStorage =
                new JsonShoppingItemStorage(temporaryFolder.resolve("shoppingList.json"));
        JsonBoughtItemStorage boughtListStorage =
                new JsonBoughtItemStorage(temporaryFolder.resolve("boughtList.json"));
        StorageManager storage = new StorageManager(groceryListStorage, userPrefsStorage, templateListStorage,
                wasteListStorage, shoppingListStorage, boughtListStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    /*@Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }*/

    /*@Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonGroceryListIoExceptionThrowingStub
        JsonGroceryListStorage groceryListStorage =
                new JsonGroceryListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionGroceryList.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY;
        GroceryItem expectedFood = new GroceryItemBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addGroceryItem(expectedFood);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }*/

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredGroceryItemList().remove(0));
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
        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList());
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
    private static class JsonGroceryListIoExceptionThrowingStub extends JsonGroceryListStorage {
        private JsonGroceryListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveGroceryList(ReadOnlyGroceryList groceryList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
