package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX =
        "The transaction index provided is invalid";
    public static final String MESSAGE_INVALID_COMMAND_USAGE = "Invalid command usage! \n%1$s";
    public static final String MESSAGE_TRANSACTIONS_LISTED_OVERVIEW = "%1$d transactions listed!";
    public static final String MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX = "The budget index provided is invalid";
    public static final String MESSAGE_INVALID_LEDGER_DISPLAYED_INDEX = "The ledger index provided is invalid";
    public static final String MESSAGE_INVALID_PROJECTION_DISPLAYED_INDEX = "The projection index provided is invalid";

    public static final String MESSAGE_AMOUNT_OVERFLOW = "Amount cannot exceed 1 million (i.e. 1000000)";
    public static final String MESSAGE_AMOUNT_NEGATIVE = "Amount cannot be negative";
    public static final String MESSAGE_AMOUNT_ZERO = "Amount cannot be zero";
    public static final String MESSAGE_AMOUNT_EMPTY = "Amount field cannot be left empty";

    protected static String getMessageUnknownCommand() {
        return Messages.MESSAGE_UNKNOWN_COMMAND;
    }

    protected static String getMessageInvalidCommandFormat() {
        return Messages.MESSAGE_INVALID_COMMAND_FORMAT;
    }

    protected static String getMessageInvalidTransactionDisplayedIndex() {
        return Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
    }

    protected static String getMessageInvalidCommandUsage() {
        return Messages.MESSAGE_INVALID_COMMAND_USAGE;
    }

    protected static String getMessageTransactionsListedOverview() {
        return Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
    }

    protected static String getMessageInvalidBudgetDisplayedIndex() {
        return Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX;
    }

    protected static String getMessageInvalidProjectionDisplayedIndex() {
        return Messages.MESSAGE_INVALID_PROJECTION_DISPLAYED_INDEX;
    }
}
