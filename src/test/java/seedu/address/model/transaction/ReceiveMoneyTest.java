package seedu.address.model.transaction;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.LedgerOperationBuilder;
import seedu.address.testutil.PersonBuilder;

//import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiveMoneyTest {
    @Test
    public void handleBalance_validArguments_updatesBalance() {
        Person joel = new PersonBuilder().withName(LedgerOperationBuilder.DEFAULT_NAME1).withAmount("100").build();
        LedgerOperationBuilder lob = new LedgerOperationBuilder();
        UniquePersonList list = lob.getPeople();
        LedgerOperation takeJoel100 = lob.withAmount("100").withPerson(joel).addPerson(joel).asLendMoney();

        //        assertEquals(lendJoel100.handleBalance(Amount.of(100), list), Amount.zero());
        //        assertEquals(Amount.of(200), joel.getBalance());
    }

    @Test
    public void handleBalance_invalidPerson_throwsMismatchException() {

    }

    @Test
    public void equalsTest() {

    }
}
