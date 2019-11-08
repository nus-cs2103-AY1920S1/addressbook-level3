package seedu.ichifund.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.commons.util.JsonUtil;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.testutil.TypicalFundBook;

public class JsonSerializableFundBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFundBookTest");
    private static final Path TYPICAL_FUNDBOOK_FILE = TEST_DATA_FOLDER.resolve("typicalFundBook.json");
    private static final Path INVALID_FUNDBOOK_FILE = TEST_DATA_FOLDER.resolve("invalidFundBook.json");
    private static final Path DUPLICATE_BUDGET_FILE = TEST_DATA_FOLDER.resolve("duplicateFundBook.json");

    @Test
    public void toModelType_typicalFundBookFile_success() throws Exception {
        JsonSerializableFundBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_FUNDBOOK_FILE,
                JsonSerializableFundBook.class).get();
        FundBook fundBookFromFile = dataFromFile.toModelType();
        FundBook typicalFundBook = TypicalFundBook.getTypicalFundBook();
        assertEquals(fundBookFromFile, typicalFundBook);
    }

    @Test
    public void toModelType_invalidFundBookFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFundBook dataFromFile = JsonUtil.readJsonFile(INVALID_FUNDBOOK_FILE,
                JsonSerializableFundBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBudgets_throwsIllegalValueException() throws Exception {
        JsonSerializableFundBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BUDGET_FILE,
                JsonSerializableFundBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFundBook.MESSAGE_DUPLICATE_BUDGET,
                dataFromFile::toModelType);
    }

}
