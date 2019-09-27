package thrift.testutil;

import java.util.Set;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.EditCommand;
import thrift.logic.parser.CliSyntax;
import thrift.model.tag.Tag;
import thrift.model.transaction.Expense;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code Expense}.
     */
    public static String getAddExpenseCommand(Expense expense) {
        return AddExpenseCommand.COMMAND_WORD + " " + getExpenseDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s details.
     */
    public static String getExpenseDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + expense.getDescription().toString() + " ");
        sb.append(CliSyntax.PREFIX_COST + expense.getValue().toString() + " ");
        expense.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTransactionDescriptor}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditCommand.EditTransactionDescriptor descriptor) {
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
