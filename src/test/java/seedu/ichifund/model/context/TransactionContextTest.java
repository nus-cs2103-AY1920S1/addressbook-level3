package seedu.ichifund.model.context;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.testutil.DateBuilder;
import seedu.ichifund.testutil.TransactionBuilder;

public class TransactionContextTest {

    @Test
    public void constructor_fromTransaction_correctFields() {
        TransactionContext transactionContext = new TransactionContext(Optional.of(new TransactionBuilder().build()));
        assertFalse(transactionContext.hasCategory());
        assertFalse(transactionContext.hasTransactionType());
        assertTrue(transactionContext.getMonth().equals(new Month(DateBuilder.DEFAULT_MONTH)));
        assertTrue(transactionContext.getYear().equals(new Year(DateBuilder.DEFAULT_YEAR)));
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionContext(null));
        assertThrows(NullPointerException.class, () ->
                new TransactionContext(null, new Year("2019"), Optional.empty(), Optional.empty()));
        assertThrows(NullPointerException.class, () ->
                new TransactionContext(new Month("1"), null, Optional.empty(), Optional.empty()));
        assertThrows(NullPointerException.class, () ->
                new TransactionContext(new Month("1"), new Year("2019"), null, Optional.empty()));
        assertThrows(NullPointerException.class, () ->
                new TransactionContext(new Month("1"), new Year("2019"), Optional.empty(), null));
    }

    @Test
    public void getCategory_emptyCategory() {
        TransactionContext transactionContext = new TransactionContext(Optional.of(new TransactionBuilder().build()));
        assertTrue(transactionContext.getCategory().equals(Category.CATEGORY_DEFAULT));
    }

    @Test
    public void getTransactionType_emptyTransactionType() {
        TransactionContext transactionContext = new TransactionContext(Optional.of(new TransactionBuilder().build()));
        assertTrue(transactionContext.getTransactionType().equals(TransactionType.TRANSACTION_TYPE_DEFAULT));
    }

    @Test
    public void getAccommodatingContext() {
        TransactionContext transactionContext = new TransactionContext(new Month("1"), new Year("2000"),
                Optional.of(new Category("food")), Optional.of(new TransactionType("in")));
        TransactionContext newTransactionContext = transactionContext.getAccommodatingContext(new TransactionBuilder()
                .withDate(new DateBuilder()
                        .withMonth("5")
                        .withYear("3000")
                        .build())
                .withCategory("transportation")
                .withTransactionType("exp")
                .build());
        assertTrue(newTransactionContext.getMonth().equals(new Month("5")));
        assertTrue(newTransactionContext.getYear().equals(new Year("3000")));
        assertTrue(newTransactionContext.getCategory().equals(Category.CATEGORY_DEFAULT));
        assertTrue(newTransactionContext.getTransactionType().equals(TransactionType.TRANSACTION_TYPE_DEFAULT));
    }
}
