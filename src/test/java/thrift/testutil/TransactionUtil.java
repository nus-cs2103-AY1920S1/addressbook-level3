package thrift.testutil;

import java.util.Set;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.parser.CliSyntax;
import thrift.model.tag.Tag;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code Expense}.
     */
    public static String getAddExpenseCommand(Expense expense) {
        return AddExpenseCommand.COMMAND_WORD + " " + getTransactionDetails(expense);
    }

    /**
     * Returns an add command string for adding the {@code Income}.
     */
    public static String getAddIncomeCommand(Income income) {
        return AddIncomeCommand.COMMAND_WORD + " " + getTransactionDetails(income);
    }

    /**
     * Returns the part of command string for the given {@code Transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + transaction.getDescription().toString() + " ");
        sb.append(CliSyntax.PREFIX_COST + transaction.getValue().toString() + " ");
        transaction.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UpdateTransactionDescriptor}'s details.
     */
    public static String getUpdateTransactionDescriptorDetails(UpdateCommand.UpdateTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb.append(CliSyntax.PREFIX_NAME)
                .append(description.toString()).append(" "));
        descriptor.getValue().ifPresent(value -> sb.append(CliSyntax.PREFIX_COST).append(value.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
