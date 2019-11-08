package seedu.ichifund.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_BUS;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_ALLOWANCE;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_BUS;

import org.junit.jupiter.api.Test;

import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.testutil.TransactionBuilder;

public class TransactionTest {

    @Test
    public void equals() {
        // same values -> returns true
        Transaction allowanceCopy = new TransactionBuilder(TRANSACTION_ALLOWANCE).build();
        assertTrue(TRANSACTION_ALLOWANCE.equals(allowanceCopy));

        // same object -> returns true
        assertTrue(TRANSACTION_ALLOWANCE.equals(TRANSACTION_ALLOWANCE));

        // null -> returns false
        assertFalse(TRANSACTION_ALLOWANCE.equals(null));

        // different type -> returns false
        assertFalse(TRANSACTION_ALLOWANCE.equals(5));

        // different transaction -> returns false
        assertFalse(TRANSACTION_ALLOWANCE.equals(TRANSACTION_BUS));

        // different description -> returns false
        Transaction editedAllowance = new TransactionBuilder(TRANSACTION_ALLOWANCE)
                .withDescription(VALID_DESCRIPTION_BUS).build();
        assertFalse(TRANSACTION_ALLOWANCE.equals(editedAllowance));

        // different amount -> returns false
        editedAllowance = new TransactionBuilder(TRANSACTION_ALLOWANCE).withAmount(VALID_AMOUNT_BUS).build();
        assertFalse(TRANSACTION_ALLOWANCE.equals(editedAllowance));

        // different category -> returns false
        editedAllowance = new TransactionBuilder(TRANSACTION_ALLOWANCE).withCategory(VALID_CATEGORY_BUS).build();
        assertFalse(TRANSACTION_ALLOWANCE.equals(editedAllowance));

        // different date -> returns false
        editedAllowance = new TransactionBuilder(TRANSACTION_ALLOWANCE).withDate(TRANSACTION_BUS.getDate()).build();
        assertFalse(TRANSACTION_ALLOWANCE.equals(editedAllowance));

        // different transaction type -> returns false
        editedAllowance = new TransactionBuilder(TRANSACTION_ALLOWANCE)
                .withTransactionType(VALID_TRANSACTION_TYPE_BUS).build();
        assertFalse(TRANSACTION_ALLOWANCE.equals(editedAllowance));

        // different repeater unique id -> returns false
        editedAllowance = new Transaction(TRANSACTION_ALLOWANCE.getDescription(), TRANSACTION_ALLOWANCE.getAmount(),
                TRANSACTION_ALLOWANCE.getCategory(), TRANSACTION_ALLOWANCE.getDate(),
                TRANSACTION_ALLOWANCE.getTransactionType(), new RepeaterUniqueId("100"));
        assertFalse(TRANSACTION_ALLOWANCE.equals(editedAllowance));
    }
}
