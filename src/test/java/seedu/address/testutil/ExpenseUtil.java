package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.expense.Expense;

/**
 * A utility class for Expense.
 */
public class ExpenseUtil {

    /**
     * Returns an add command string for adding the {@code expense}.
     */
    public static String getAddExpenseCommand(Expense expense) {
        return AddExpenseCommand.COMMAND_WORD + " " + getExpenseDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code expense}'s details.
     */
    public static String getExpenseDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + expense.getName().fullName + " ");
        sb.append(PREFIX_AMOUNT + expense.getAmount().value + " ");
        sb.append(PREFIX_CURRENCY + expense.getCurrency().name + " ");
        sb.append(PREFIX_DATE + expense.getDate().value + " ");
        sb.append(PREFIX_TAG + expense.getTag().tagName);
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditExpenseDescriptorDetails(EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.value).append(" "));
        descriptor.getCurrency().ifPresent(amount -> sb.append(PREFIX_CURRENCY).append(amount.name).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        descriptor.getTag().ifPresent(tag -> sb.append(PREFIX_TAG).append(tag.tagName));

        return sb.toString();
    }
}
