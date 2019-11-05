package dukecooks.testutil.profile;

import java.util.Set;

import dukecooks.logic.commands.profile.AddProfileCommand;
import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.Person;

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
        sb.append(CliSyntax.PREFIX_NAME + person.getName().fullName + " " + CliSyntax.PREFIX_DOB
                + person.getDateOfBirth().dateOfBirth + " "
                + CliSyntax.PREFIX_GENDER + person.getGender().gender + CliSyntax.PREFIX_BLOODTYPE
                + person.getBloodType().bloodGroup + " "
                + CliSyntax.PREFIX_WEIGHT + person.getWeight().weight + CliSyntax.PREFIX_HEIGHT
                + person.getHeight().height);
        person.getMedicalHistories().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_MEDICALHISTORY + s.medicalHistoryName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditProfileCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME)
                .append(name.fullName).append(" "));
        if (descriptor.getMedicalHistoriesToAdd().isPresent()) {
            Set<MedicalHistory> medicalHistories = descriptor.getMedicalHistoriesToAdd().get();
            if (medicalHistories.isEmpty()) {
                sb.append(CliSyntax.PREFIX_MEDICALHISTORY);
            } else {
                medicalHistories.forEach(s -> sb.append(CliSyntax.PREFIX_MEDICALHISTORY)
                        .append(s.medicalHistoryName).append(" "));
            }
        }
        descriptor.getDateOfBirth().ifPresent(d-> sb.append(CliSyntax.PREFIX_DOB)
                .append(d.dateOfBirth.toString()).append(" "));
        descriptor.getGender().ifPresent(g -> sb.append(CliSyntax.PREFIX_GENDER)
                .append(g.gender).append(" "));
        descriptor.getBloodType().ifPresent(b -> sb.append(CliSyntax.PREFIX_BLOODTYPE)
                .append(b.bloodGroup).append(" "));
        descriptor.getHeight().ifPresent(h -> sb.append(CliSyntax.PREFIX_HEIGHT)
                .append(h.toString()).append(" "));
        descriptor.getWeight().ifPresent(w -> sb.append(CliSyntax.PREFIX_WEIGHT)
                .append(w.toString()).append(" "));

        return sb.toString();
    }
}
