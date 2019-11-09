package seedu.address.testutil;

import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;

import java.util.Set;

import seedu.address.logic.finance.commands.EditCommand;
import seedu.address.logic.finance.commands.SpendCommand;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * A utility class for log entries.
 */
public class LogEntryUtil {

    /**
     * Returns an Spend command string for adding the {@code SpendLogEntry}.
     */
    public static String getSpendCommand(LogEntry spendLogEntry) {
        return SpendCommand.COMMAND_WORD + " " + getSpendLogEntryDetails(spendLogEntry);
    }

    /**
     * Returns the part of command string for the given {@code SpendLogEntry}'s details.
     */
    public static String getSpendLogEntryDetails(LogEntry logEntry) {
        SpendLogEntry spendLogEntry = (SpendLogEntry) logEntry;
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_AMOUNT + spendLogEntry.getAmount().toString());
        sb.append(PREFIX_DAY + spendLogEntry.getTransactionDate().value + " ");
        sb.append(PREFIX_DESCRIPTION + spendLogEntry.getDescription().value + " ");
        sb.append(PREFIX_PLACE + spendLogEntry.getPlace().value + " ");
        sb.append(PREFIX_TRANSACTION_METHOD + spendLogEntry.getTransactionMethod().value + " ");
        spendLogEntry.getCategories().stream().forEach(
            c -> sb.append(PREFIX_CATEGORY + c.catName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditLogEntryDescriptorDetails(EditCommand.EditLogEntryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getAmount().ifPresent(amt -> sb.append(PREFIX_AMOUNT).append(amt.amount).append(" "));
        descriptor.getTransactionDate().ifPresent(tDate -> sb.append(PREFIX_DAY).append(tDate.value).append(" "));
        descriptor.getDesc().ifPresent(d -> sb.append(PREFIX_DESCRIPTION).append(d.value).append(" "));
        descriptor.getPlace().ifPresent(p -> sb.append(PREFIX_PLACE).append(p.value).append(" "));
        descriptor.getTransactionMethod().ifPresent(tMet ->
                sb.append(PREFIX_TRANSACTION_METHOD).append(tMet.value).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> tags = descriptor.getCategories().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                tags.forEach(cat -> sb.append(PREFIX_CATEGORY).append(cat.catName).append(" "));
            }
        }
        return sb.toString();
    }
}
