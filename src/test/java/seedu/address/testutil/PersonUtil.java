package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESULT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;



/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PICTURE + person.getPicture().value + " ");
        sb.append(PREFIX_CLASSID + person.getClassId().value + " ");
        sb.append(PREFIX_ATTENDANCE + person.getAttendance().value + " ");
        sb.append(PREFIX_RESULT + person.getResult().value + " ");
        sb.append(PREFIX_PARTICIPATION + person.getParticipation().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPicture().ifPresent(picture -> sb.append(PREFIX_PICTURE).append(picture.value).append(" "));
        descriptor.getClassId().ifPresent(classid -> sb.append(PREFIX_CLASSID).append(classid.value).append(" "));
        descriptor.getAttendance().ifPresent(attendance -> sb.append(PREFIX_ATTENDANCE)
                .append(attendance.value).append(" "));
        descriptor.getResult().ifPresent(result -> sb.append(PREFIX_RESULT).append(result.value).append(" "));
        descriptor.getParticipation().ifPresent(participation -> sb.append(PREFIX_PARTICIPATION)
                .append(participation.value).append(" "));
        return sb.toString();
    }
}
