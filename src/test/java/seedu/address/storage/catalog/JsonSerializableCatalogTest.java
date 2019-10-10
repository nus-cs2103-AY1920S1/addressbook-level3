package seedu.address.storage.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Catalog;
import seedu.address.storage.catalog.JsonSerializableCatalog;
import seedu.address.testutil.TypicalBooks;

public class JsonSerializableCatalogTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCatalogTest");
    private static final Path TYPICAL_BOOKS_FILE = TEST_DATA_FOLDER.resolve("typicalBookCatalog.json");
    private static final Path INVALID_BOOKS_FILE = TEST_DATA_FOLDER.resolve("invalidBookCatalog.json");
    private static final Path DUPLICATE_BOOK_FILE = TEST_DATA_FOLDER.resolve("duplicateBookCatalog.json");

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        JsonSerializableCatalog dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKS_FILE,
                JsonSerializableCatalog.class).get();
        Catalog catalogFromFile = dataFromFile.toModelType();
        Catalog typicalPersonsCatalog = TypicalBooks.getTypicalCatalog();
        assertEquals(catalogFromFile, typicalPersonsCatalog);
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCatalog dataFromFile = JsonUtil.readJsonFile(INVALID_BOOKS_FILE,
                JsonSerializableCatalog.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBooks_throwsIllegalValueException() throws Exception {
        JsonSerializableCatalog dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BOOK_FILE,
                JsonSerializableCatalog.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCatalog.MESSAGE_DUPLICATE_BOOK,
                dataFromFile::toModelType);
    }

}
