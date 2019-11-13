package budgetbuddy.model.loan.exceptions;

/**
 * Signals that an identical loan (except for status) already exists in the list.
 */
public class DuplicateLoanException extends RuntimeException {}
