package seedu.tarence.testutil;

import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.tarence.model.student.Student;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns an addStudent command string for adding the {@code student}.
     */
    public static String getAddStudentCommand(Student student) {
        return AddStudentCommand.COMMAND_WORD + " " + getStudentDetails(student);
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
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        return sb.toString();

    }
}

