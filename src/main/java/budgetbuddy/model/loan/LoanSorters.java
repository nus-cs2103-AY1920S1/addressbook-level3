package budgetbuddy.model.loan;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A container class to hold comparators for sorting the loan list.
 */
public class LoanSorters {

    /**
     * An enumeration to qualify which sorters are availble for the loan list.
     */
    public enum SortBy {
        PERSON, AMOUNT, DATE;

        /**
         * Returns true if the given string corresponds to a value in {@code SortBy}.
         */
        public static boolean contains(String toTest) {
            return Arrays.stream(SortBy.values())
                    .map(SortBy::toString)
                    .anyMatch(sortByStr -> sortByStr.equals(toTest));
        }
    }

    public static final Comparator<Loan> DATE_NEWEST =
            Comparator.comparing(Loan::getDate).reversed();
    public static final Comparator<Loan> PERSON =
            Comparator.comparing(loan -> loan.getPerson().getName().toString());
    public static final Comparator<Loan> AMOUNT_ASC =
            Comparator.comparingLong(loan -> loan.getAmount().toLong());
}
