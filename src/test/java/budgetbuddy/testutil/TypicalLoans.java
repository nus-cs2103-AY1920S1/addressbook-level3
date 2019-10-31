package budgetbuddy.testutil;

import java.util.Date;

import budgetbuddy.model.loan.Loan;

/**
 * A utility class containing a list of {@code Loan} objects to be used in tests.
 */
public class TypicalLoans {

    public static final Loan JOHN_OUT_UNPAID = new LoanBuilder().withPerson("John").withDirection("OUT")
            .withAmount(10000L).withDescription("For dinner.").withDate(new Date()).withStatus("UNPAID")
            .build();
    public static final Loan PETER_OUT_PAID = new LoanBuilder().withPerson("Peter").withDirection("OUT")
            .withAmount(4000L).withDescription("For lunch.").withDate(new Date()).withStatus("PAID")
            .build();
    public static final Loan MARY_IN_UNPAID = new LoanBuilder().withPerson("Mary").withDirection("IN")
            .withAmount(420L).withDescription("For supper.").withDate(new Date()).withStatus("UNPAID")
            .build();
    public static final Loan ZED_OUT_PAID = new LoanBuilder().withPerson("Zed").withDirection("IN")
            .withAmount(66666L).withDescription("For the midnight snack.").withDate(new Date()).withStatus("PAID")
            .build();

    private TypicalLoans() {} // prevents instantiation
}
