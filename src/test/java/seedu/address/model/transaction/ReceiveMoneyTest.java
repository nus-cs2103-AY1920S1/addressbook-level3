package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.LedgerOperationBuilder;
import seedu.address.testutil.PersonBuilder;

public class ReceiveMoneyTest {
    private Person joel;
    private LedgerOperationBuilder lob;

    @BeforeEach
    public void init() {
        lob = new LedgerOperationBuilder();
        joel = new PersonBuilder().withName(LedgerOperationBuilder.DEFAULT_NAME1).withAmount("100").build();
    }

    @Test
    public void handleBalance_validArguments_updatesBalance() {
        Amount initialPot = Amount.of(100);
        UniquePersonList list = lob.getPeople();
        LedgerOperation receiveFromJoel = lob.withAmount("100").withPerson(joel).addPerson(joel).asReceiveMoney();

        assertEquals(Amount.of(200), receiveFromJoel.handleBalance(initialPot, list));
        assertEquals(Amount.zero(), joel.getBalance());
    }

    @Test
    public void handleBalance_newPerson_addsNewPerson() {
        UniquePersonList personList = new UniquePersonList();
        LedgerOperation receiveFromJoel = lob.withAmount("100").withPerson(joel).asReceiveMoney();

        receiveFromJoel.handleBalance(Amount.zero(), personList);

        assertTrue(personList.contains(joel));
    }

    @Test
    public void handleBalance_oldPerson_updatesOldPerson() {
        LedgerOperation receiveFromJoel = lob.withAmount("100").withPerson(joel).addPerson(joel).asReceiveMoney();
        UniquePersonList personList = lob.getPeople();

        receiveFromJoel.handleBalance(Amount.zero(), personList);

        assertEquals(Amount.zero(), personList.get(joel).get().getBalance());
    }

    @Test
    public void getAmount_isPositive() {
        assertFalse(lob.withAmount("100").asReceiveMoney().getAmount().isNegative());
    }

    @Test
    public void equalsTest() {
        LedgerOperation hundredFromJoel = lob.withAmount("100")
                .withPerson(joel).withDescription("desc").withDate("10102019").asReceiveMoney();

        assertEquals(hundredFromJoel, lob.asReceiveMoney());

        LedgerOperationBuilder hundredFromNoel = new LedgerOperationBuilder(hundredFromJoel).withPerson("noel");
        assertNotEquals(hundredFromJoel, hundredFromNoel.asReceiveMoney());

        //different operation types
        assertNotEquals(hundredFromJoel, lob.asLendMoney());

        //different amount
        LedgerOperationBuilder tenFromJoel = new LedgerOperationBuilder(hundredFromJoel).withAmount("10");
        assertNotEquals(hundredFromJoel, tenFromJoel.asReceiveMoney());

        //different date
        LedgerOperationBuilder yesterday = new LedgerOperationBuilder(hundredFromJoel).withDate("09102019");
        assertNotEquals(hundredFromJoel, yesterday.asReceiveMoney());

        //different description
        LedgerOperationBuilder hundredFromJoell = new LedgerOperationBuilder(hundredFromJoel).withDescription("descr");
        assertNotEquals(hundredFromJoel, hundredFromJoell.asReceiveMoney());

        //different person, same name
        LedgerOperationBuilder hundredFromJoel2 = new LedgerOperationBuilder(hundredFromJoel)
                .withPerson(LedgerOperationBuilder.DEFAULT_NAME1);
        assertNotEquals(hundredFromJoel, hundredFromJoel2.asReceiveMoney());
    }
}
