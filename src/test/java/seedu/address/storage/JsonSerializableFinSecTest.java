package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.FinSec;
import seedu.address.testutil.TypicalObjects;

public class JsonSerializableFinSecTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableFinSecTest");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    private static final Path TYPICAL_CLAIMS_FILE = TEST_DATA_FOLDER.resolve("typicalClaimsFinSec.json");
    private static final Path TYPICAL_APPROVED_CLAIMS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalApprovedClaimsFinSec.json");

    private static final Path TYPICAL_INCOMES_FILE = TEST_DATA_FOLDER.resolve("typicalIncomesFinSec.json");
    private static final Path TYPICAL_COMMANDITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalCommandItemsFinSec.json");
    private static final Path TYPICAL_SUGGESTIONS_FILE = TEST_DATA_FOLDER.resolve("typicalSuggestionsFinSec.json");

    //@@author{weigenie}
    @Test
    public void toModelType_typicalClaimsFile_success() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLAIMS_FILE,
                JsonSerializableFinSec.class).get();
        FinSec finSecFromFile = dataFromFile.toModelType();
        FinSec typicalClaimsFinSec = TypicalObjects.getTypicalFinSecWithNoApprovedClaims();
        assertEquals(finSecFromFile, typicalClaimsFinSec);
    }

    //@@author{weigenie}
    @Test
    public void toModelType_typicalApprovedClaimsFile_success() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPROVED_CLAIMS_FILE,
                JsonSerializableFinSec.class).get();
        FinSec finSecFromFile = dataFromFile.toModelType();
        FinSec typicalClaimsFinSec = TypicalObjects.getTypicalFinSecWithApprovedClaims();
        assertEquals(finSecFromFile, typicalClaimsFinSec);
    }

    //@@author{joshuaseetss}
    @Test
    public void toModelType_typicalIncomesFile_success() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLAIMS_FILE,
                JsonSerializableFinSec.class).get();
        FinSec finSecFromFile = dataFromFile.toModelType();
        FinSec typicalClaimsFinSec = TypicalObjects.getTypicalFinSec();
        assertEquals(finSecFromFile, typicalClaimsFinSec);
    }

    //@@author{joshuaseetss}
    @Test
    public void toModelType_typicalSuggestionsFile_success() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLAIMS_FILE,
                JsonSerializableFinSec.class).get();
        FinSec finSecFromFile = dataFromFile.toModelType();
        FinSec typicalClaimsFinSec = TypicalObjects.getTypicalFinSec();
        assertEquals(finSecFromFile, typicalClaimsFinSec);
    }

    //@@author{joshuaseetss}
    @Test
    public void toModelType_typicalCommandItemsFile_success() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLAIMS_FILE,
                JsonSerializableFinSec.class).get();
        FinSec finSecFromFile = dataFromFile.toModelType();
        FinSec typicalClaimsFinSec = TypicalObjects.getTypicalFinSec();
        assertEquals(finSecFromFile, typicalClaimsFinSec);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableFinSec.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableFinSec dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableFinSec.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFinSec.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
