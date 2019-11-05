package mams.testutil;

import mams.logic.parser.CliSyntax;
import mams.model.student.Student;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + student.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_CREDITS + student.getCredits().value + " ");
        sb.append(CliSyntax.PREFIX_PREVMODS + student.getPrevMods().value + " ");
        sb.append(CliSyntax.PREFIX_STUDENT + student.getMatricId().value + " ");
        student.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

}
