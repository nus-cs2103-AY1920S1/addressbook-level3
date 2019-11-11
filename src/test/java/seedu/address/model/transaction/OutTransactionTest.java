package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BankOperationBuilder;

public class OutTransactionTest {

    @Test
    public void validEquals_true() {
        BankAccountOperation testTransaction1 = new BankOperationBuilder()
                .withAmount("-100").withDate("20102019").withDescription("Milk").build();
        BankAccountOperation testTransaction2 = new BankOperationBuilder()
                .withAmount("-100").withDate("20102019").withDescription("Milk").build();
        BankAccountOperation testTransactionCategories1 = new BankOperationBuilder()
                .withAmount("-100").withDate("20102019").withDescription("owed")
                .withCategories("Friends").build();
        BankAccountOperation testTransactionCategories2 = new BankOperationBuilder()
                .withAmount("-100").withDate("20102019").withDescription("owed")
                .withCategories("Friends").build();

        assertTrue(testTransaction1.equals(testTransaction2));

        assertTrue(testTransactionCategories1.equals(testTransactionCategories2));

        assertTrue(testTransaction1.equals(testTransaction1));

        assertTrue(testTransactionCategories1.equals(testTransactionCategories1));
    }
    @Test
    public void validEquals_false() {
        BankAccountOperation testTransaction1 = new BankOperationBuilder()
                .withAmount("-100").withDate("20102019").withDescription("Milk").build();
        // different Amount
        BankAccountOperation testTransaction2 = new BankOperationBuilder()
                .withAmount("-200").withDate("20102019").withDescription("Milk").build();
        // different Date
        BankAccountOperation testTransaction3 = new BankOperationBuilder()
                .withAmount("-100").withDate("21102019").withDescription("Milk").build();
        // different Description
        BankAccountOperation testTransaction4 = new BankOperationBuilder()
                .withAmount("-100").withDate("20102019").withDescription("Coke").build();
        // different Amount and Date
        BankAccountOperation testTransaction5 = new BankOperationBuilder()
                .withAmount("-200").withDate("21102019").withDescription("Milk").build();
        // different Date and Description
        BankAccountOperation testTransaction6 = new BankOperationBuilder()
                .withAmount("-100").withDate("21102019").withDescription("Coke").build();
        // different Amount and Description
        BankAccountOperation testTransaction7 = new BankOperationBuilder()
                .withAmount("-200").withDate("20102019").withDescription("Coke").build();

        assertFalse(testTransaction1.equals(testTransaction2));

        assertFalse(testTransaction1.equals(testTransaction3));

        assertFalse(testTransaction1.equals(testTransaction4));

        assertFalse(testTransaction1.equals(testTransaction5));

        assertFalse(testTransaction1.equals(testTransaction6));
    }

}
