package seedu.address.model.transaction;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BankOperationBuilder;

public class InTransactionTest {
    // private InTransaction expectedTransaction = new InTransaction();

    @Test
    public void validInTransaction_arguments() {
        // expectedTransaction has no categories
        BankAccountOperation expectedTransaction = new BankOperationBuilder()
                .withAmount("100").withDate("20102019").withDescription("Milk").build();
        BankAccountOperation expectedTransactionCategories = new BankOperationBuilder()
                .withAmount("100").withDate("20102019").withDescription("owed")
                .withCategories("Friends").build();


    }
}
