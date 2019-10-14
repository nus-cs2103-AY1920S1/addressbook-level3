package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.Set;

import seedu.address.logic.commands.AddProfileCommand;
import seedu.address.logic.commands.EditProfileCommand.EditPersonDescriptor;
import seedu.address.profile.medical.MedicalHistory;
import seedu.address.profile.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddProfileCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " " + PREFIX_DOB + person.getDateOfBirth().dateOfBirth + " "
                + PREFIX_GENDER + person.getGender().gender + PREFIX_BLOODTYPE + person.getBloodType().bloodGroup + " "
                + PREFIX_WEIGHT + person.getWeight().weight + PREFIX_HEIGHT + person.getHeight().height);
        person.getMedicalHistories().stream().forEach(
            s -> sb.append(PREFIX_MEDICALHISTORY + s.medicalHistoryName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        if (descriptor.getMedicalHistories().isPresent()) {
            Set<MedicalHistory> medicalHistories = descriptor.getMedicalHistories().get();
            if (medicalHistories.isEmpty()) {
                sb.append(PREFIX_MEDICALHISTORY);
            } else {
                medicalHistories.forEach(s -> sb.append(PREFIX_MEDICALHISTORY)
                        .append(s.medicalHistoryName).append(" "));
            }
        }
        return sb.toString();
    }
}
