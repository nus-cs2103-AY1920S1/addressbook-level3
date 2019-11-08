package budgetbuddy.storage.loans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.commons.util.JsonUtil;
import budgetbuddy.model.LoansManager;
import budgetbuddy.testutil.loanutil.TypicalDebtors;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class JsonSerializableLoansManagerTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableLoansManagerTest");
    private static final Path TYPICAL_LOANS_MANAGER_FILE =
            TEST_DATA_FOLDER.resolve("typicalLoansManager.json");
    private static final Path INVALID_LOANS_MANAGER_FILE =
            TEST_DATA_FOLDER.resolve("invalidLoansManager.json");
    private static final Path DUPLICATE_LOANS_MANAGER_FILE =
            TEST_DATA_FOLDER.resolve("duplicateLoansManager.json");

    @Test
    public void toModelType_typicalLoansManager_success() throws DataConversionException, IllegalValueException {
        JsonSerializableLoansManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_LOANS_MANAGER_FILE,
                JsonSerializableLoansManager.class).get();
        LoansManager loansManagerFromFile = dataFromFile.toModelType();
        LoansManager typicalLoansManager = new LoansManager(TypicalLoans.LOAN_LIST, TypicalDebtors.DEBTOR_LIST);
        assertEquals(typicalLoansManager, loansManagerFromFile);
    }

    @Test
    public void toModelType_invalidLoansManager_throwsIllegalValueException() throws DataConversionException {
        JsonSerializableLoansManager dataFromFile = JsonUtil.readJsonFile(INVALID_LOANS_MANAGER_FILE,
                JsonSerializableLoansManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateLoansManager_throwsIllegalValueException() throws DataConversionException {
        JsonSerializableLoansManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LOANS_MANAGER_FILE,
                JsonSerializableLoansManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
