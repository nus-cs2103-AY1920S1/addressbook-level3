package seedu.billboard.testutil;

import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.billboard.logic.commands.AddCommand;
import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Expense;

/**
 * A utility class for Expense.
 */
public class ExpenseUtil {

    /**
     * Returns an add command string for adding the {@code expense}.
     */
    public static String getAddCommand(Expense expense) {
        return AddCommand.COMMAND_WORD + " " + getExpenseDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code expense}'s details.
     */
    public static String getExpenseDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(expense.getName().name).append(" ")
                .append(PREFIX_DESCRIPTION).append(expense.getDescription().description).append(" ")
                .append(PREFIX_DATE)
                .append(DateTimeFormatter.ofPattern(CreatedDateTime.ACCEPTABLE_PATTERNS.get(0))
                        .format(expense.getCreated().dateTime))
                .append(" ")
                .append(PREFIX_AMOUNT).append(expense.getAmount().amount).append(" ");

        expense.getTags().forEach(
            s -> sb.append(PREFIX_TAG).append(s.tagName).append(" ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditExpenseDescriptorDetails(EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.name).append(" "));
        descriptor.getDescription().ifPresent(desc -> sb.append(PREFIX_DESCRIPTION)
                .append(desc.description).append(" "));
        descriptor.getAmount().ifPresent(amt -> sb.append(PREFIX_AMOUNT).append(amt.amount).append(" "));

        if (descriptor.getTags().isPresent()) {
            List<String> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s).append(" "));
            }
        }
        return sb.toString();
    }
}
