package seedu.savenus.storage.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.BAK_KUT_TEH;
import static seedu.savenus.testutil.TypicalMenu.TONKATSU_RAMEN;
import static seedu.savenus.testutil.TypicalPurchaseHistory.getTypicalPurchaseHistory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.purchase.ReadOnlyPurchaseHistory;
import seedu.savenus.model.purchase.TimeOfPurchase;

//@@author raikonen
public class JsonPurchaseHistoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonPurchaseHistoryStorageTest");

    @TempDir
    public Path testFolder;

    private java.util.Optional<ReadOnlyPurchaseHistory> readPurchaseHistory(String filePath) throws Exception {
        return new JsonPurchaseHistoryStorage(Paths.get(filePath))
                .readPurchaseHistory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readPurchaseHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPurchaseHistory(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPurchaseHistory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(
                DataConversionException.class, () -> readPurchaseHistory("notJsonFormatPurchaseHistory.json"));
    }

    @Test
    public void readAndSavePurchaseHistory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMenu.json");
        PurchaseHistory original = getTypicalPurchaseHistory();
        JsonPurchaseHistoryStorage jsonPurchaseHistoryStorage = new JsonPurchaseHistoryStorage(filePath);

        // Save in new file and read back
        jsonPurchaseHistoryStorage.savePurchaseHistory(original, filePath);
        ReadOnlyPurchaseHistory readBack = jsonPurchaseHistoryStorage.readPurchaseHistory(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite existing file, and read back
        original.addPurchase(new Purchase(TONKATSU_RAMEN, new TimeOfPurchase("1570680000000")));
        jsonPurchaseHistoryStorage.savePurchaseHistory(original, filePath);
        readBack = jsonPurchaseHistoryStorage.readPurchaseHistory(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.addPurchase(new Purchase(BAK_KUT_TEH, new TimeOfPurchase("1570280000000")));
        jsonPurchaseHistoryStorage.savePurchaseHistory(original); // file path not specified
        readBack = jsonPurchaseHistoryStorage.readPurchaseHistory().get(); // file path not specified
        assertEquals(original, readBack);
    }

    @Test
    public void saveMenu_nullPurchaseHistory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePurchaseHistory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code PurchaseHistory} at the specified {@code filePath}.
     */
    private void savePurchaseHistory(PurchaseHistory purchaseHistory, String filePath) {
        try {
            new JsonPurchaseHistoryStorage(Paths.get(filePath))
                    .savePurchaseHistory(purchaseHistory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePurchaseHistory(new PurchaseHistory(), null));
    }

}
