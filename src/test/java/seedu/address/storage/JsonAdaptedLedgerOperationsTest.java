package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.testutil.LedgerOperationBuilder;

public class JsonAdaptedLedgerOperationsTest {
    @Test
    public void toModelType_validBudgetDetails_returnsBudget() throws Exception {
        LedgerOperation ledgerOperation = new LedgerOperationBuilder().asReceiveMoney();
        JsonAdaptedLedgerOperations jsonAdaptedLedgerOperations = new JsonAdaptedLedgerOperations(ledgerOperation);
        assertEquals(ledgerOperation, jsonAdaptedLedgerOperations.toModelType());
    }
}
