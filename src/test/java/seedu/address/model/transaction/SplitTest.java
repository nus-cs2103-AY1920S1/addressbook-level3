package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.LedgerOperationBuilder;
import seedu.address.testutil.PersonBuilder;

public class SplitTest {
    @Test
    public void handleBalance_validArguments_updatesBalance() {
        Person a = new PersonBuilder().withName("a").build();
        Person b = new PersonBuilder().withName("b").build();
        Person c = new PersonBuilder().withName("c").build();
        LedgerOperationBuilder lob = new LedgerOperationBuilder().addPerson(a).addPerson(b).addPerson(c)
                .withAmount("400");
        LedgerOperation split = lob.asSplit(1, 1, 1, 1);

        Amount newBalance = split.handleBalance(Amount.zero(), lob.getPeople());

        assertEquals(Amount.of(-300), newBalance);
        assertEquals(Amount.of(100), a.getBalance());
        assertEquals(Amount.of(100), b.getBalance());
        assertEquals(Amount.of(100), c.getBalance());
    }

    @Test
    public void handleBalance_directTransfer_success() {
        Person a = new PersonBuilder().withName("a").build();
        LedgerOperationBuilder sendBuilder = new LedgerOperationBuilder().addPerson(a)
                .withAmount("10");
        LedgerOperation send = sendBuilder.asSplit(0, 1);

        Amount newBalance = send.handleBalance(Amount.zero(), sendBuilder.getPeople());

        assertEquals(Amount.of(10), a.getBalance());
        assertEquals(Amount.of(-10), newBalance);
    }

    @Test
    public void isValidSharesLengthTest() {
        LedgerOperationBuilder lob = new LedgerOperationBuilder().addPerson("a").addPerson("b").addPerson("c");

        List<Integer> threeShares = IntStream.of(1, 2, 3).boxed().collect(Collectors.toList());
        assertFalse(Split.isValidSharesLength(threeShares, lob.getPeople()));

        List<Integer> fourShares = IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList());
        assertTrue(Split.isValidSharesLength(fourShares, lob.getPeople()));

        List<Integer> fiveShares = IntStream.of(1, 2, 3, 4, 5).boxed().collect(Collectors.toList());
        assertFalse(Split.isValidSharesLength(fiveShares, lob.getPeople()));
    }
}
