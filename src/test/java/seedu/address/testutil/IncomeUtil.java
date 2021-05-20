package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.commands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.address.model.income.Income;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Income.
 */
public class IncomeUtil {
    /**
     * Returns an add command string for adding the {@code income}.
     */
    public static String getAddCommand(Income income) {
        return AddIncomeCommand.COMMAND_WORD + " " + getIncomeDetails(income);
    }

    /**
     * Returns the part of command string for the given {@code income}'s details.
     */
    public static String getIncomeDetails(Income income) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + income.getDescription().text + " ");
        sb.append(PREFIX_CASH + income.getAmount().value + " ");
        sb.append(PREFIX_DATE + income.getDate().text + " ");
        sb.append(PREFIX_NAME + income.getName().fullName + " ");
        sb.append(PREFIX_PHONE + income.getPhone().value + " ");
        income.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditIncomeDescriptor}'s details.
     */
    public static String getEditIncomeDescriptorDetails(EditIncomeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION).append(description.text)
                .append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_CASH).append(amount.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.text).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
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
