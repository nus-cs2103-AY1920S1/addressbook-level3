package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;

import seedu.address.logic.commands.addcommand.AddMentorCommand;
import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.model.entity.Mentor;

/**
 * Utility class for a mentor.
 */
public class MentorUtil {

    /**
     * Returns an add command string for adding the {@code mentor}.
     */
    public static String getAddCommand(Mentor mentor) {
        return AddMentorCommand.COMMAND_WORD + " " + getMentorDetails(mentor);
    }

    /**
     * Returns the part of command string for the given {@code mentor}'s details.
     */
    public static String getMentorDetails(Mentor mentor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + mentor.getName().fullName + " ");
        sb.append(PREFIX_PHONE + mentor.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + mentor.getEmail().value + " ");
        sb.append(PREFIX_ORGANISATION + mentor.getOrganization().fullName + " ");
        sb.append(PREFIX_SUBJECT_NAME + mentor.getSubject().toString() + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMentorDescriptor}'s details.
     */
    public static String getEditMentorDescriptorDetails(EditMentorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getOrganization().ifPresent(org -> sb.append(PREFIX_ORGANISATION).append(org.fullName).append(" "));
        descriptor.getSubject().ifPresent(sub -> sb.append(PREFIX_SUBJECT_NAME).append(sub.toString()).append(" "));

        return sb.toString();
    }

}
