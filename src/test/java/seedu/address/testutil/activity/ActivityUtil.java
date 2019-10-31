package seedu.planner.testutil.activity;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.commands.EditActivityCommand;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.tag.Tag;

/**
 * A utility class for Activity.
 */
public class ActivityUtil {

    /**
     * Returns an add command string for adding the {@code activities}.
     */
    public static String getAddActivityCommand(Activity activity) {
        return AddActivityCommand.COMMAND_WORD + " "
                + AddActivityCommand.SECOND_COMMAND_WORD + " "
                + getActivityDetails(activity);
    }

    /**
     * Returns the part of command string for the given {@code activity}'s details.
     */
    public static String getActivityDetails(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + activity.getName().toString() + " ");
        sb.append(PREFIX_ADDRESS + activity.getAddress().toString() + " ");
        activity.getContact().ifPresent(contact -> sb.append(PREFIX_PHONE + contact.getPhone().toString() + " ")
        );
        activity.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditActivityDescriptor}'s details.
     */
    public static String getEditActivityDescriptorDetails(EditActivityCommand.EditActivityDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
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
