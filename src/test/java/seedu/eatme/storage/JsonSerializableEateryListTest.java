package seedu.eatme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.util.JsonUtil;
import seedu.eatme.model.EateryList;
import seedu.eatme.testutil.TypicalEateries;

public class JsonSerializableEateryListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEateryListTest");
    private static final Path TYPICAL_OPEN_EATERIES_FILE = TEST_DATA_FOLDER
            .resolve("typicalOpenEateriesEateryList.json");
    private static final Path TYPICAL_CLOSE_EATERIES_FILE = TEST_DATA_FOLDER
            .resolve("typicalCloseEateriesEateryList.json");
    private static final Path INVALID_EATERY_FILE = TEST_DATA_FOLDER.resolve("invalidEateryEateryList.json");
    private static final Path DUPLICATE_EATERY_FILE = TEST_DATA_FOLDER.resolve("duplicateEateryEateryList.json");

    @Test
    public void toModelType_typicalOpenEateriesFile_success() throws Exception {
        JsonSerializableEateryList dataFromFile = JsonUtil.readJsonFile(TYPICAL_OPEN_EATERIES_FILE,
                JsonSerializableEateryList.class).get();
        EateryList eateryListFromFile = dataFromFile.toModelType();
        EateryList typicalOpenEateriesEateryList = TypicalEateries.getTypicalOpenEateryList();
        assertEquals(eateryListFromFile, typicalOpenEateriesEateryList);
    }

    @Test
    public void toModelType_typicalCloseEateriesFile_success() throws Exception {
        JsonSerializableEateryList dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLOSE_EATERIES_FILE,
                JsonSerializableEateryList.class).get();
        EateryList eateryListFromFile = dataFromFile.toModelType();
        EateryList typicalCloseEateriesEateryList = TypicalEateries.getTypicalCloseEateryList();
        assertEquals(eateryListFromFile, typicalCloseEateriesEateryList);
    }

}
