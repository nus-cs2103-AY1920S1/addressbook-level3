package seedu.billboard.testutil;

import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.billboard.logic.commands.AddCommand;
import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * A utility class for Expense.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code expense}.
     */
    public static String getAddCommand(Expense expense) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code expense}'s details.
     */
    public static String getPersonDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(expense.getName().name).append(" ")
            .append(PREFIX_DESCRIPTION).append(expense.getDescription().description).append(" ")
            .append(PREFIX_AMOUNT).append(expense.getAmount().amount).append(" ");

        expense.getTags().forEach(
            s -> sb.append(PREFIX_TAG).append(s.tagName).append(" ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.name).append(" "));
        descriptor.getDescription().ifPresent(desc -> sb.append(PREFIX_DESCRIPTION)
                .append(desc.description).append(" "));
        descriptor.getAmount().ifPresent(amt -> sb.append(PREFIX_AMOUNT).append(amt.amount).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
