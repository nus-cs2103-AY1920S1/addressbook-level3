// @@author chrischenhui
package seedu.address.storage.wordbanks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.TypicalCards;

class JsonSerializableWordBankTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonSerializableWordBankTest");
    private static final Path TYPICAL_WORD_BANK_FILE = TEST_DATA_FOLDER.resolve("sample.json");
    private static final Path INVALID_WORD_BANK_FILE = TEST_DATA_FOLDER.resolve("invalidWordBank.json");
    private static final Path DUPLICATE_CARDS_IN_WORD_BANK_FILE =
            TEST_DATA_FOLDER.resolve("duplicateCardsInWordBank.json");

    @Test
    void toModelType_typicalWordBankFile_success() throws Exception {
        JsonSerializableWordBank dataFromFile = JsonUtil.readJsonFile(TYPICAL_WORD_BANK_FILE,
                JsonSerializableWordBank.class).get();
        WordBank wordBankFromFile = dataFromFile.toModelTypeWithName("sample");
        WordBank typicalWordBank = TypicalCards.getTypicalWordBank();
        assertEquals(wordBankFromFile, typicalWordBank);
    }

    @Test
    void toModelType_invalidWordBankFile_throwsNullPointerException() throws Exception {
        JsonSerializableWordBank dataFromFile = JsonUtil.readJsonFile(INVALID_WORD_BANK_FILE,
                JsonSerializableWordBank.class).get();
        assertThrows(NullPointerException.class, dataFromFile::toModelType);
    }

    @Test
    void toModelType_duplicateCardsInWordBank_throwsNullPointerException() throws Exception {
        JsonSerializableWordBank dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CARDS_IN_WORD_BANK_FILE,
                JsonSerializableWordBank.class).get();
        assertThrows(NullPointerException.class, dataFromFile::toModelType);
    }
}
