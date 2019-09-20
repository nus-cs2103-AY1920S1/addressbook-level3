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
        sb.append(PREFIX_PHONE + problem.getAuthor().value + " ");
        sb.append(PREFIX_EMAIL + problem.getWebLink().value + " ");
        sb.append(PREFIX_ADDRESS + problem.getDescription().value + " ");
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
        descriptor.getAuthor().ifPresent(author -> sb.append(PREFIX_PHONE).append(author.value).append(" "));
        descriptor.getWebLink().ifPresent(weblink -> sb.append(PREFIX_EMAIL).append(weblink.value).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_ADDRESS).append(description.value).append(" "));
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
