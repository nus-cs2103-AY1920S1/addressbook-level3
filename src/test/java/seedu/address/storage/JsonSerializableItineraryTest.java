package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Itinerary;
import seedu.address.testutil.TypicalContacts;

public class JsonSerializableItineraryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableItineraryTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalContactsItinerary.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve("invalidContactItinerary.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER.resolve("duplicateContactItinerary.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableItinerary dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableItinerary.class).get();
        Itinerary itineraryFromFile = dataFromFile.toModelType();
        Itinerary typicalContactsItinerary = TypicalContacts.getTypicalItinerary();
        assertEquals(itineraryFromFile, typicalContactsItinerary);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializableItinerary dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializableItinerary.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializableItinerary dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializableItinerary.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableItinerary.MESSAGE_DUPLICATE_CONTACT,
                dataFromFile::toModelType);
    }

}
