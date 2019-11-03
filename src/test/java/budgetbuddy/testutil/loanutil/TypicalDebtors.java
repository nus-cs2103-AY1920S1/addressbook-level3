package budgetbuddy.testutil.loanutil;

import java.util.List;

import budgetbuddy.model.loan.Debtor;

/**
 * A utility class containing a list of {@code Debtor} objects to be used in tests.
 */
public class TypicalDebtors {

    public static final Debtor JOHN = new DebtorBuilder().withDebtor("John")
            .withCreditors(List.of("Mary", "Peter"), List.of(3000L, 50000L)).build();
    public static final Debtor MARY = new DebtorBuilder().withDebtor("Mary")
            .withCreditors(List.of("Peter", "Zed", "Gary"), List.of(40000L, 930L, 7000L)).build();
    public static final Debtor ZED = new DebtorBuilder().withDebtor("Zed")
            .withCreditors(List.of("Gary"), List.of(1L)).build();

    public static final List<Debtor> DEBTOR_LIST = List.of(JOHN, MARY, ZED);

    private TypicalDebtors() {} // prevents instantiation
}
