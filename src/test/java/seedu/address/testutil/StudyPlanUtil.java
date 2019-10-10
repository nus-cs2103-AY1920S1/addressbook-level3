package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.EditTitleCommand.EditTitleStudyPlanDescriptor;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UserTag;

/**
 * A utility class for StudyPlan.
 */
public class StudyPlanUtil {

    /**
     * Returns an add command string for adding the {@code studyPlan}.
     */
    public static String getCreateStudyPlanCommand(StudyPlan studyPlan) {
        return CreateStudyPlanCommand.COMMAND_WORD + " " + getStudyPlanDetails(studyPlan);
    }

    /**
     * Returns the part of command string for the given {@code studyPlan}'s details.
     */
    public static String getStudyPlanDetails(StudyPlan studyPlan) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + studyPlan.getName().fullName + " ");
        sb.append(PREFIX_PHONE + studyPlan.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + studyPlan.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + studyPlan.getAddress().value + " ");
        studyPlan.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getTagName() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudyPlanDescriptor}'s details.
     */
    public static String getEditStudyPlanDescriptorDetails(EditTitleStudyPlanDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<UserTag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getTagName()).append(" "));
            }
        }
        return sb.toString();
    }
}
