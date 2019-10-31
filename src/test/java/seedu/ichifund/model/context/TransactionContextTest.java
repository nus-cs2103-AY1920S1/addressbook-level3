package seedu.ichifund.model.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.testutil.DateBuilder;
import seedu.ichifund.testutil.TransactionBuilder;

public class TransactionContextTest {

    @Test
    public void constructor_fromTransaction_correctFields() {
        TransactionContext transactionContext = new TransactionContext(Optional.of(new TransactionBuilder().build()));
        assertFalse(transactionContext.hasCategory());
        assertFalse(transactionContext.hasTransactionType());
        assertTrue(transactionContext.getMonth().equals(DateBuilder.DEFAULT_MONTH));
        assertTrue(transactionContext.getYear().equals(DateBuilder.DEFAULT_YEAR));
    }
}