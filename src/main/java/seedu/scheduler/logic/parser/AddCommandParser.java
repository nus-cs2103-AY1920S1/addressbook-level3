package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_CRITICAL_ERROR;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.scheduler.commons.util.CollectionUtil;
import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.AddIntervieweeCommand;
import seedu.scheduler.logic.commands.AddInterviewerCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.model.person.RoleType;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG,
                        PREFIX_FACULTY, PREFIX_YEAR_OF_STUDY, PREFIX_DEPARTMENT, PREFIX_SLOT, PREFIX_PERSONAL_EMAIL,
                        PREFIX_NUS_WORK_EMAIL);

        // common prefixes - present across interviewers and interviewees
        if (!arePrefixesPresent(argMultimap, PREFIX_ROLE, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // hierarchical parsing
        if (role.getRole().equals(RoleType.INTERVIEWEE)) {
            return parseAddIntervieweeCommand(argMultimap, name, phone, tagList);
        } else if (role.getRole().equals(RoleType.INTERVIEWER)) {
            return parseAddInterviewerCommand(argMultimap, name, phone, tagList);
        }

        // control flow should not reach here.
        throw new AssertionError(MESSAGE_CRITICAL_ERROR);
    }

    /**
     * Parses the argument multimap into an AddIntervieweeCommand.
     */
    private AddIntervieweeCommand parseAddIntervieweeCommand(ArgumentMultimap argMultimap, Name name, Phone phone,
                                                             Set<Tag> tagSet) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_FACULTY, PREFIX_YEAR_OF_STUDY, PREFIX_FACULTY,
                PREFIX_DEPARTMENT, PREFIX_SLOT, PREFIX_PERSONAL_EMAIL, PREFIX_NUS_WORK_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        // Get all necessary fields from argMultimap
        Faculty faculty = ParserUtil.parseFaculty(argMultimap.getValue(PREFIX_FACULTY).get());
        Integer yearOfStudy = ParserUtil.parseYearOfStudy(argMultimap.getValue(PREFIX_YEAR_OF_STUDY).get());
        List<Slot> availableTimeslots = ParserUtil.parseSlots(argMultimap.getAllValues(PREFIX_SLOT));
        Email personalEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PERSONAL_EMAIL).get());
        Email nusWorkEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).get());
        Emails emails = new Emails().addPersonalEmail(personalEmail).addNusEmail(nusWorkEmail);
        List<Department> departmentChoices =
                ParserUtil.parseDepartments(argMultimap.getAllValues(PREFIX_DEPARTMENT));

        // An interviewee should not be allocated the same timeslots.
        if (CollectionUtil.collectionHasDuplicate(availableTimeslots)) {
            throw new ParseException(AddCommand.MESSAGE_DUPLICATE_SLOT);
        }

        // An interviewee should not belong to the same department.
        if (CollectionUtil.collectionHasDuplicate(departmentChoices)) {
            throw new ParseException(AddCommand.MESSAGE_DUPLICATE_DEPARTMENT);
        }

        // Build the interviewee
        Interviewee interviewee = new Interviewee.IntervieweeBuilder(name, phone, tagSet)
                .faculty(faculty)
                .yearOfStudy(yearOfStudy)
                .departmentChoices(departmentChoices)
                .availableTimeslots(availableTimeslots)
                .emails(emails)
                .build();

        return new AddIntervieweeCommand(interviewee);
    }

    /**
     * Parses the argument multimap into an AddInterviewerCommand.
     */
    private AddInterviewerCommand parseAddInterviewerCommand(ArgumentMultimap argMultimap, Name name, Phone phone,
                                                             Set<Tag> tagSet) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_DEPARTMENT, PREFIX_NUS_WORK_EMAIL, PREFIX_SLOT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        // Get all necessary values from argMultimap.
        Department department = ParserUtil.parseDepartment(argMultimap.getValue(PREFIX_DEPARTMENT).get());
        Email nusWorkEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).get());
        List<Slot> availableTimeslots = ParserUtil.parseSlots(argMultimap.getAllValues(PREFIX_SLOT));

        // An interviewer should not have the same availabilities.
        if (CollectionUtil.collectionHasDuplicate(availableTimeslots)) {
            throw new ParseException(AddCommand.MESSAGE_DUPLICATE_SLOT);
        }

        // Build the interviewer
        Interviewer interviewer = new Interviewer.InterviewerBuilder(name, phone, tagSet)
                .department(department)
                .email(nusWorkEmail)
                .availabilities(availableTimeslots)
                .build();
        return new AddInterviewerCommand(interviewer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
