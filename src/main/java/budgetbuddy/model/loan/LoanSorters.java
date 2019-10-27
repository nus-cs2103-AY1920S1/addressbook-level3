package budgetbuddy.model.loan;

import java.util.Comparator;

public class LoanSorters {

    public static final Comparator<Loan> DATE_NEWEST =
            Comparator.comparing(Loan::getDate).reversed();
    public static final Comparator<Loan> PERSON =
            Comparator.comparing(loan -> loan.getPerson().getName().toString());
    public static final Comparator<Loan> AMOUNT_ASC =
            Comparator.comparingLong(loan -> loan.getAmount().toLong());
}
