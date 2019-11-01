package seedu.ichifund.testutil;

import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.ichifund.model.transaction.Transaction;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns a full add transaction command string for adding the {@code transaction}.
     */
    public static String getAddTransactionCommand(Transaction transaction) {
        return AddTransactionCommand.COMMAND_WORD + " " + getTransactionDetails(transaction);
    }

    /**
     * Returns the part of transactionCommand string for the given {@code transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + transaction.getDescription().toString() + " ");
        sb.append(PREFIX_AMOUNT + transaction.getAmount().toString() + " ");
        sb.append(PREFIX_DAY + transaction.getDay().toString() + " ");
        sb.append(PREFIX_MONTH + transaction.getMonth().toString() + " ");
        sb.append(PREFIX_YEAR + transaction.getYear().toString() + " ");
        sb.append(PREFIX_CATEGORY + transaction.getCategory().toString() + " ");
        sb.append(PREFIX_TRANSACTION_TYPE + transaction.getTransactionType().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of transactionCommand string for the given {@code EditTransactionDescriptor}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.toString()).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString()).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY)
                .append(category.toString()).append(" "));
        descriptor.getDay().ifPresent(day -> sb.append(PREFIX_DAY).append(day.toString()).append(" "));
        descriptor.getMonth().ifPresent(month -> sb.append(PREFIX_MONTH).append(month.toString()).append(" "));
        descriptor.getYear().ifPresent(year -> sb.append(PREFIX_YEAR).append(year.toString()).append(" "));
        descriptor.getTransactionType().ifPresent(transactionType -> sb.append(PREFIX_TRANSACTION_TYPE)
                .append(transactionType.toString()).append(" "));
        return sb.toString();
    }
}
