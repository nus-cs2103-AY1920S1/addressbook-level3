package seedu.ifridge.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.storage.JsonGroceryListStorage;
import seedu.ifridge.storage.JsonTemplateListStorage;
import seedu.ifridge.storage.JsonUserPrefsStorage;
import seedu.ifridge.storage.StorageManager;
import seedu.ifridge.storage.shoppinglist.JsonBoughtListStorage;
import seedu.ifridge.storage.shoppinglist.JsonShoppingListStorage;
import seedu.ifridge.storage.unitdictionary.JsonUnitDictionaryStorage;
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
        JsonShoppingListStorage shoppingListStorage =
                new JsonShoppingListStorage(temporaryFolder.resolve("shoppingList.json"));
        JsonBoughtListStorage boughtListStorage =
                new JsonBoughtListStorage(temporaryFolder.resolve("boughtList.json"));
        JsonUnitDictionaryStorage unitDictionaryStorage =
                new JsonUnitDictionaryStorage(temporaryFolder.resolve("unitDictionary.json"));
        StorageManager storage = new StorageManager(groceryListStorage, userPrefsStorage, templateListStorage,
                wasteListStorage, shoppingListStorage, boughtListStorage, unitDictionaryStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredGroceryItemList().remove(0));
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
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

}
