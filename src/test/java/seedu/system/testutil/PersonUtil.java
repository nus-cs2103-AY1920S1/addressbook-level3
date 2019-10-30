package seedu.system.testutil;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.system.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.system.logic.commands.outofsession.AddPersonCommand;
import seedu.system.logic.commands.outofsession.EditPersonCommand.EditPersonDescriptor;
import seedu.system.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddPersonCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().toString() + " ");
        sb.append(PREFIX_DOB + person.getDateOfBirth().toString() + " ");
        sb.append(PREFIX_GENDER + person.getGender().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name).append(" "));
        descriptor.getDateOfBirth().ifPresent(dateOfBirth -> sb.append(PREFIX_DOB).append(dateOfBirth).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender).append(" "));
        return sb.toString();
    }
}
