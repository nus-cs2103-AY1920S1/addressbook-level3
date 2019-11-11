package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ORDER_JSON_TEST2;
import static seedu.address.testutil.TypicalOrders.ORDER_JSON_TEST3;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DataBook;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;

public class JsonOrderBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonOrderBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readOrderBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readOrderBook(null));
    }

    private java.util.Optional<ReadOnlyDataBook<Order>> readOrderBook(String filePath) throws Exception {
        return new JsonOrderBookStorage(Paths.get(filePath)).readOrderBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readOrderBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readOrderBook("notJsonFormatOrderBook.json"));
    }

    @Test
    public void readOrderBook_invalidOrderOrderBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readOrderBook("invalidOrderOrderBook.json"));
    }

    @Test
    public void readOrderBook_invalidAndValidOrderOrderBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readOrderBook("invalidAndValidOrderOrderBook.json"));
    }

    @Test
    public void readAndSaveOrderBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempOrderBook.json");
        DataBook<Order> original = getTypicalOrderBook();
        JsonOrderBookStorage jsonOrderBookStorage = new JsonOrderBookStorage(filePath);

        // Save in new file and read back
        jsonOrderBookStorage.saveOrderBook(original, filePath);
        ReadOnlyDataBook readBack = jsonOrderBookStorage.readOrderBook(filePath).get();
        assertEquals(original, new DataBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.add(ORDER_JSON_TEST2);
        original.remove(ORDERONE);
        jsonOrderBookStorage.saveOrderBook(original, filePath);
        readBack = jsonOrderBookStorage.readOrderBook(filePath).get();
        assertEquals(original, new DataBook<Order>(readBack));

        // Save and read without specifying file path
        original.add(ORDER_JSON_TEST3);
        jsonOrderBookStorage.saveOrderBook(original); // file path not specified
        readBack = jsonOrderBookStorage.readOrderBook().get(); // file path not specified
        assertEquals(original, new DataBook(readBack));

    }

    @Test
    public void saveOrderBook_nullOrderBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveOrderBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code OrderBook} at the specified {@code filePath}.
     */
    private void saveOrderBook(ReadOnlyDataBook orderBook, String filePath) {
        try {
            new JsonOrderBookStorage(Paths.get(filePath))
                    .saveOrderBook(orderBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveOrderBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveOrderBook(new DataBook(), null));
    }
}