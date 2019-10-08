package calofit.storage;

import calofit.commons.exceptions.IllegalValueException;
import calofit.commons.util.JsonUtil;
import calofit.model.AddressBook;
import calofit.testutil.Assert;
import calofit.testutil.TypicalDishes;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_DISHES_FILE = TEST_DATA_FOLDER.resolve("typicalDishesAddressBook.json");
    private static final Path INVALID_MEAL_FILE = TEST_DATA_FOLDER.resolve("invalidDishAddressBook.json");
    private static final Path DUPLICATE_MEAL_FILE = TEST_DATA_FOLDER.resolve("duplicateDishAddressBook.json");

    @Test
    public void toModelType_typicalDishesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_DISHES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalDishesAddressBook = TypicalDishes.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalDishesAddressBook);
    }

    @Test
    public void toModelType_duplicateDishes_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEAL_FILE,
                JsonSerializableAddressBook.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_DISHES,
                dataFromFile::toModelType);
    }

}
