package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

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

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedLedgerOperations nullLedger = new JsonAdaptedLedgerOperations(
            "19112019", "100", null, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, nullLedger::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLedgerOperations nullLedger = new JsonAdaptedLedgerOperations(
            null, "100", "world", new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, nullLedger::toModelType);
    }

    @Test
    public void toModelType_emptyPeople_throwsIllegalValueException() {
        JsonAdaptedLedgerOperations nullLedger = new JsonAdaptedLedgerOperations(
            "19112019", "100", "world", new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, nullLedger::toModelType);
    }
}
