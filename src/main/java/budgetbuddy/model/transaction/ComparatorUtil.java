package budgetbuddy.model.transaction;

import java.util.Comparator;

/**
 * Useful comparators for sorting transactions
 */
public class ComparatorUtil {
    public static final Comparator<Transaction> SORT_BY_DESCENDING_DATE = (
        t1, t2) -> t1.getLocalDate().compareTo(t2.getLocalDate());
    public static final Comparator<Transaction> SORT_BY_ASCENDING_DATE = (
        t1, t2) -> t2.getLocalDate().compareTo(t1.getLocalDate());
    public static final Comparator<Transaction> SORT_BY_DESCENDING_AMOUNT = (
        t1, t2) -> t2.getAmount().compareTo(t1.getAmount());
    public static final Comparator<Transaction> SORT_BY_ASCENDING_AMOUNT = (
        t1, t2) -> t1.getAmount().compareTo(t2.getAmount());
    public static final Comparator<Transaction> SORT_BY_DESCENDING_DESCRIPTION = (
        t1, t2) -> t2.getDescription().compareTo(t1.getDescription());
    public static final Comparator<Transaction> SORT_BY_ASCENDING_DESCRIPTION = (
        t1, t2) -> t1.getDescription().compareTo(t2.getDescription());
}
