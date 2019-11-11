package seedu.scheduler.testutil;

import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;
import static seedu.scheduler.model.person.EmailType.NUS;
import static seedu.scheduler.model.person.EmailType.PERSONAL;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.model.person.Interviewee;

/**
 * A utility class for Interviewee.
 */
public class IntervieweeUtil extends PersonUtil {

    /**
     * Returns an add command string for the {@code interviewee}.
     */
    public static String getAddCommand(Interviewee interviewee) {
        return AddCommand.COMMAND_WORD + " interviewee " + getIntervieweeDetails(interviewee);
    }

    /**
     * Returns the part of the command string for the given {@code interviewee}'s details.
     */
    public static String getIntervieweeDetails(Interviewee interviewee) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPersonDetails(interviewee));
        sb.append(PREFIX_FACULTY + interviewee.getFaculty().faculty + " ");
        sb.append(PREFIX_YEAR_OF_STUDY + interviewee.getYearOfStudy().toString() + " ");
        interviewee.getEmails().getEmailsOfType(PERSONAL).stream().forEach(
            e -> sb.append(PREFIX_PERSONAL_EMAIL + e.toString() + " ")
        );
        interviewee.getEmails().getEmailsOfType(NUS).stream().forEach(
            e -> sb.append(PREFIX_NUS_WORK_EMAIL + e.toString() + " ")
        );
        interviewee.getDepartmentChoices().stream().forEach(
            d -> sb.append(PREFIX_DEPARTMENT + d.department + " ")
        );
        interviewee.getAvailableTimeslots().stream().forEach(
            s -> sb.append(PREFIX_SLOT + s.toString() + " ")
        );
        return sb.toString();
    }
}
