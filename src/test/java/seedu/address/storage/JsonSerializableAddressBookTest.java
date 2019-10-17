package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAnswerables;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_ANSWERABLES_FILE = TEST_DATA_FOLDER.resolve("typicalAnswerableTestBank.json");
    private static final Path INVALID_ANSWERABLE_FILE = TEST_DATA_FOLDER.resolve("invalidAnswerableTestBank.json");
    private static final Path DUPLICATE_ANSWERABLE_FILE = TEST_DATA_FOLDER.resolve("duplicateAnswerableTestBank.json");

    @Test
    public void toModelType_typicalAnswerablesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ANSWERABLES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAnswerablesAddressBook = TypicalAnswerables.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalAnswerablesAddressBook);
    }

    @Test
    public void toModelType_invalidAnswerableFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ANSWERABLE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAnswerables_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ANSWERABLE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_ANSWERABLE,
                dataFromFile::toModelType);
    }

}
