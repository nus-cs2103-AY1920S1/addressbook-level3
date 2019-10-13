package seedu.ichifund.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.commons.util.JsonUtil;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.testutil.TypicalPersons;

public class JsonSerializableFundBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFundBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsFundBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonFundBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonFundBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableFundBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableFundBook.class).get();
        FundBook fundBookFromFile = dataFromFile.toModelType();
        FundBook typicalPersonsFundBook = TypicalPersons.getTypicalFundBook();
        assertEquals(fundBookFromFile, typicalPersonsFundBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFundBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableFundBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableFundBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableFundBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFundBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
