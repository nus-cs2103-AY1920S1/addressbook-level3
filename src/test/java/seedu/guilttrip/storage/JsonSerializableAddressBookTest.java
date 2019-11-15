package seedu.guilttrip.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.guilttrip.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.commons.exceptions.IllegalValueException;
//import seedu.guilttrip.commons.util.JsonUtil;
//import seedu.guilttrip.model.GuiltTrip;
//import seedu.guilttrip.testutil.TypicalEntries;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    /**
    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableGuiltTrip dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableGuiltTrip.class).get();
        GuiltTrip addressBookFromFile = dataFromFile.toModelType();
        GuiltTrip typicalPersonsAddressBook = TypicalEntries.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGuiltTrip dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableGuiltTrip.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableGuiltTrip dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableGuiltTrip.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableGuiltTrip.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }
    **/

}
