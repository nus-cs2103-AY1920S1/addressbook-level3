package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.logic.commands.AddInterviewerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleType;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG,
                        PREFIX_FACULTY, PREFIX_YEAR_OF_STUDY, PREFIX_DEPARTMENT, PREFIX_SLOT, PREFIX_PERSONAL_EMAIL,
                        PREFIX_NUS_WORK_EMAIL);

        // common prefixes - present across interviewers and interviewees
        if (!arePrefixesPresent(argMultimap, PREFIX_ROLE, PREFIX_NAME, PREFIX_PHONE, PREFIX_DEPARTMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<Department> departmentChoices = ParserUtil.parseDepartments(argMultimap.getAllValues(PREFIX_DEPARTMENT));
        List<Slot> availableTimeslots = ParserUtil.parseSlots(argMultimap.getAllValues(PREFIX_SLOT));

        // may not be present, depending on role type being added.
        Optional<String> facultyString = argMultimap.getValue(PREFIX_FACULTY);
        Optional<String> yearOfStudyString = argMultimap.getValue(PREFIX_YEAR_OF_STUDY);
        Optional<String> personalEmailString = argMultimap.getValue(PREFIX_PERSONAL_EMAIL);
        Optional<String> nusWorkEmailString = argMultimap.getValue(PREFIX_NUS_WORK_EMAIL);

        if (role.getRole() == RoleType.INTERVIEWEE) {
            Faculty faculty = ParserUtil.parseFaculty(facultyString.get());
            Integer yearOfStudy = ParserUtil.parseYearOfStudy(yearOfStudyString.get());
            Email personalEmail = ParserUtil.parseEmail(personalEmailString.get());
            Email nusWorkEmail = ParserUtil.parseEmail(nusWorkEmailString.get());
            Emails emails = new Emails().addPersonalEmail(personalEmail).addNusEmail(nusWorkEmail);
            Interviewee interviewee = new Interviewee.IntervieweeBuilder(name, phone, tagList)
                    .faculty(faculty)
                    .yearOfStudy(yearOfStudy)
                    .departmentChoices(departmentChoices)
                    .availableTimeslots(availableTimeslots)
                    .emails(emails)
                    .build();
            return new AddIntervieweeCommand(interviewee);
        } else if (role.getRole() == RoleType.INTERVIEWER) {
            Department department = departmentChoices.get(0); // interviewer has one department
            Email nusWorkEmail = ParserUtil.parseEmail(nusWorkEmailString.get());
            Interviewer interviewer = new Interviewer.InterviewerBuilder(name, phone, tagList)
                    .department(department)
                    .email(nusWorkEmail)
                    .availabilities(availableTimeslots)
                    .build();
            return new AddInterviewerCommand(interviewer);
        } else {
            // control flow should not reach here.
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
