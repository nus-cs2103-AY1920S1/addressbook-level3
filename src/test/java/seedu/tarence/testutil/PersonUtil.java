package seedu.tarence.testutil;

import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddStudentCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns an addStudent command string for adding the {@code person}.
     */
    public static String getAddStudentCommand(Student student) {
        return AddStudentCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + student.getName().fullName + " ");
        sb.append(PREFIX_EMAIL + student.getEmail().value + " ");
        sb.append(student.getNusnetId().get().nusnetId + " ");
        sb.append(student.getMatricNum().get().value + " ");
        sb.append(PREFIX_MODULE + student.getModCode().modCode + " ");
        sb.append(PREFIX_TUTORIAL_NAME + student.getTutName().tutName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        return sb.toString();
    }
}
