package seedu.algobase.testutil;

import static seedu.algobase.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.algobase.logic.commands.AddCommand;
import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.model.Problem.Problem;
import seedu.algobase.model.tag.Tag;

/**
 * A utility class for Problem.
 */
public class ProblemUtil {

    /**
     * Returns an add command string for adding the {@code Problem}.
     */
    public static String getAddCommand(Problem problem) {
        return AddCommand.COMMAND_WORD + " " + getProblemDetails(problem);
    }

    /**
     * Returns the part of command string for the given {@code Problem}'s details.
     */
    public static String getProblemDetails(Problem problem) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + problem.getName().fullName + " ");
        sb.append(PREFIX_PHONE + problem.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + problem.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + problem.getAddress().value + " ");
        problem.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditProblemDescriptor}'s details.
     */
    public static String getEditProblemDescriptorDetails(EditProblemDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
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
