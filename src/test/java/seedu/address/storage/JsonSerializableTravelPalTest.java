package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TravelPal;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableTravelPalTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTravelPalTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test @Disabled
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableTravelPal dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableTravelPal.class).get();
        TravelPal travelPalFromFile = dataFromFile.toModelType();
        TravelPal typicalPersonsTravelPal = TypicalPersons.getTypicalAddressBook();
        assertEquals(travelPalFromFile, typicalPersonsTravelPal);
    }

    @Test @Disabled
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTravelPal dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableTravelPal.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test @Disabled
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableTravelPal dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableTravelPal.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTravelPal.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
