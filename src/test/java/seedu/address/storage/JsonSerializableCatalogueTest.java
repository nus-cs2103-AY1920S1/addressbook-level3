package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Catalogue;
import seedu.address.testutil.TypicalBooks;

public class JsonSerializableCatalogueTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalBookCatalogue.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidBookCatalogue.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateBookCatalogue.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableCatalogue dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableCatalogue.class).get();
        Catalogue catalogueFromFile = dataFromFile.toModelType();
        Catalogue typicalPersonsCatalogue = TypicalBooks.getTypicalCatalogue();
        assertEquals(catalogueFromFile, typicalPersonsCatalogue);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCatalogue dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableCatalogue.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableCatalogue dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableCatalogue.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCatalogue.MESSAGE_DUPLICATE_BOOK,
                dataFromFile::toModelType);
    }

}
