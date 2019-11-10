package budgetbuddy.model.loan;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.person.Person;
import budgetbuddy.testutil.loanutil.DebtorBuilder;
import budgetbuddy.testutil.loanutil.TypicalDebtors;

public class DebtorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // all fields null
        assertThrows(NullPointerException.class, () -> new Debtor(null, null));

        // debtor field null
        assertThrows(NullPointerException.class, () -> new DebtorBuilder().withDebtor(null).build());

        // creditors field null
        assertThrows(NullPointerException.class, () -> new DebtorBuilder()
            .withCreditors(null, null).build());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Debtor johnCopy = new DebtorBuilder(TypicalDebtors.JOHN).build();
        assertEquals(TypicalDebtors.JOHN, johnCopy);

        // same object -> returns true
        assertEquals(TypicalDebtors.JOHN, TypicalDebtors.JOHN);

        // null -> returns false
        assertNotEquals(null, TypicalDebtors.JOHN);

        // different type -> returns false
        assertNotEquals(5, TypicalDebtors.JOHN);

        // different debtor -> returns false
        Debtor editedJohn = new DebtorBuilder(TypicalDebtors.JOHN).withDebtor("Mary").build();
        assertNotEquals(TypicalDebtors.JOHN, editedJohn);

        // different creditors -> return false
        editedJohn = new DebtorBuilder(TypicalDebtors.JOHN)
                .withCreditors(
                        TypicalDebtors.MARY.getCreditors().keySet().stream()
                                .map(Person::toString)
                                .collect(Collectors.toList()),
                        TypicalDebtors.MARY.getCreditors().values().stream()
                                .map(Amount::toLong)
                                .collect(Collectors.toList()))
                .build();
        assertNotEquals(TypicalDebtors.JOHN, editedJohn);
    }
}
