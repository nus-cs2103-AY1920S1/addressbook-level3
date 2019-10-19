package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Expense;

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
        sb.append(PREFIX_DESCRIPTION + expense.getDescription().fullDescription + " ");
        sb.append(PREFIX_PRICE + expense.getPrice().value + " ");
        expense.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.categoryName + " ")
        );
        sb.append(PREFIX_TIMESTAMP + expense.getTimestamp().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditExpenseDescriptorDetails(EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb
                .append(PREFIX_DESCRIPTION).append(description.fullDescription).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> categories = descriptor.getCategories().get();
            if (categories.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                categories.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.categoryName).append(" "));
            }
        }
        descriptor.getTimestamp()
                .ifPresent(timestamp -> sb.append(PREFIX_TIMESTAMP).append(timestamp.toString()).append(" "));
        return sb.toString();
    }
}
