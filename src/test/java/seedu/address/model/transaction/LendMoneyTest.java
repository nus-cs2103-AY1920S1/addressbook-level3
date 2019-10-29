package seedu.address.model.transaction;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.LedgerOperationBuilder;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LendMoneyTest {
    @Test
    public void handleBalance_validPerson_validPersonList_updatesBalanceCorrectly() {
        Person joel = new PersonBuilder().withName(LedgerOperationBuilder.DEFAULT_NAME1).withAmount("100").build();
        LedgerOperationBuilder lob = new LedgerOperationBuilder();
        UniquePersonList list = lob.getPeople();
        LedgerOperation lendJoel100 = lob.withAmount("100").withPerson(joel).addPerson(joel).asLendMoney();

        assertEquals(lendJoel100.handleBalance(Amount.of(100), list), Amount.zero());
        assertEquals(Amount.of(200), joel.getBalance());
    }
}
