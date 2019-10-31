package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;
import static seedu.address.model.person.EmailType.NUS;
import static seedu.address.model.person.EmailType.PERSONAL;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditIntervieweeCommand.EditIntervieweeDescriptor;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailType;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

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
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of the command string the given {@code interviewee}'s details.
     */
    public static String getIntervieweeDetails(Interviewee interviewee) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + interviewee.getName().fullName + " ");
        sb.append(PREFIX_PHONE + interviewee.getPhone().value + " ");
        interviewee.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        sb.append(PREFIX_FACULTY + interviewee.getFaculty().faculty + " ");
        sb.append(PREFIX_YEAR_OF_STUDY + interviewee.getYearOfStudy().toString() + " ");
        interviewee.getEmails().getEmailsOfType(PERSONAL).stream().forEach(
            s -> sb.append(PREFIX_PERSONAL_EMAIL + s.value + " ")
        );
        interviewee.getEmails().getEmailsOfType(EmailType.NUS).stream().forEach(
            s -> sb.append(PREFIX_NUS_WORK_EMAIL + s.value + " ")
        );
        interviewee.getDepartmentChoices().stream().forEach(
            s -> sb.append(PREFIX_DEPARTMENT + s.department + " ")
        );
        interviewee.getAvailableTimeslots().stream().forEach(
            s -> sb.append(PREFIX_SLOT + s.toString() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditIntervieweeDescriptor}'s details.
     */
    public static String getEditIntervieweeDescriptorDetails(EditIntervieweeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        descriptor.getFaculty().ifPresent(faculty -> sb.append(PREFIX_FACULTY).append(faculty.faculty).append(" "));
        descriptor.getYearOfStudy().ifPresent(year -> sb.append(PREFIX_YEAR_OF_STUDY)
                .append(year.toString()).append(" "));
        if (descriptor.getEmails().isPresent()) {
            Emails emails = descriptor.getEmails().get();
            List<Email> personalEmails = emails.getEmailsOfType(PERSONAL);
            List<Email> nusWorkEmails = emails.getEmailsOfType(NUS);
            if (personalEmails.isEmpty()) {
                sb.append(PREFIX_PERSONAL_EMAIL);
            } else {
                personalEmails.forEach(s -> sb.append(PREFIX_PERSONAL_EMAIL).append(s.value).append(" "));
            }
            if (nusWorkEmails.isEmpty()) {
                sb.append(PREFIX_NUS_WORK_EMAIL);
            } else {
                nusWorkEmails.forEach(s -> sb.append(PREFIX_NUS_WORK_EMAIL).append(s.value).append(" "));
            }
        }
        if (descriptor.getDepartmentChoices().isPresent()) {
            List<Department> departments = descriptor.getDepartmentChoices().get();
            if (departments.isEmpty()) {
                sb.append(PREFIX_DEPARTMENT);
            } else {
                departments.forEach(s -> sb.append(PREFIX_DEPARTMENT).append(s.department).append(" "));
            }
        }
        if (descriptor.getAvailableTimeslots().isPresent()) {
            List<Slot> slots = descriptor.getAvailableTimeslots().get();
            if (slots.isEmpty()) {
                sb.append(PREFIX_SLOT);
            } else {
                slots.forEach(s -> sb.append(PREFIX_SLOT).append(s.toString()).append(" "));
            }
        }
        return sb.toString();
    }

    /*
    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    /*
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
    */
}
