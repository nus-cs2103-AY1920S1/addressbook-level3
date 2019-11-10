package seedu.scheduler.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.core.Messages.MESSAGE_CRITICAL_ERROR;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.EditCommand.MESSAGE_USAGE;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.scheduler.commons.util.CollectionUtil;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EditIntervieweeCommand;
import seedu.scheduler.logic.commands.EditIntervieweeCommand.EditIntervieweeDescriptor;
import seedu.scheduler.logic.commands.EditInterviewerCommand;
import seedu.scheduler.logic.commands.EditInterviewerCommand.EditInterviewerDescriptor;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.model.person.RoleType;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_TAG, PREFIX_FACULTY, PREFIX_YEAR_OF_STUDY, PREFIX_DEPARTMENT, PREFIX_SLOT,
                PREFIX_PERSONAL_EMAIL, PREFIX_NUS_WORK_EMAIL);

        Name name;

        // name preamble and role prefix must be present
        try {
            name = ParserUtil.parseName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_ROLE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

        // edit interviewee command
        if (role.getRole().equals(RoleType.INTERVIEWEE)) {
            EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
            fillIntervieweeDescriptor(descriptor, argMultimap);

            if (!descriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditIntervieweeCommand(name, descriptor);
        }
        // edit interviewer command
        if (role.getRole().equals(RoleType.INTERVIEWER)) {
            EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
            fillInterviewerDescriptor(descriptor, argMultimap);

            if (!descriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditInterviewerCommand(name, descriptor);
        }

        throw new AssertionError(MESSAGE_CRITICAL_ERROR); // should not reach this point
    }

    /**
     * Fills the given interviewee descriptor with necessary values from the argumentMultiMap.
     */
    private void fillIntervieweeDescriptor(EditIntervieweeDescriptor descriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_FACULTY).isPresent()) {
            descriptor.setFaculty(ParserUtil.parseFaculty(argMultimap.getValue(PREFIX_FACULTY).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR_OF_STUDY).isPresent()) {
            descriptor.setYearOfStudy(ParserUtil.parseYearOfStudy(argMultimap.getValue(PREFIX_YEAR_OF_STUDY).get()));
        }

        if (argMultimap.getValue(PREFIX_PERSONAL_EMAIL).isPresent()
                || argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).isPresent()) {
            Emails emails = new Emails();
            if (argMultimap.getValue(PREFIX_PERSONAL_EMAIL).isPresent()) {
                emails.addPersonalEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PERSONAL_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).isPresent()) {
                emails.addNusEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).get()));
            }
            descriptor.setEmails(emails);
        }

        // parse collections for edit
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(descriptor::setTags);
        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            parseDepartmentsForEdit(
                    argMultimap.getAllValues(PREFIX_DEPARTMENT)).ifPresent(descriptor::setDepartmentChoices);
        }
        if (argMultimap.getValue(PREFIX_SLOT).isPresent()) {
            parseSlotsForEdit(argMultimap.getAllValues(PREFIX_SLOT)).ifPresent(descriptor::setAvailableTimeslots);
        }
    }

    /**
     * Fills the given interviewer descriptor with necessary values from the argumentMultiMap.
     */
    private void fillInterviewerDescriptor(EditInterviewerDescriptor descriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).isPresent()) {
            descriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_NUS_WORK_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            descriptor.setDepartment(ParserUtil.parseDepartment(argMultimap.getValue(PREFIX_DEPARTMENT).get()));
        }

        // parse collections for edit
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(descriptor::setTags);
        if (argMultimap.getValue(PREFIX_SLOT).isPresent()) {
            parseSlotsForEdit(argMultimap.getAllValues(PREFIX_SLOT)).ifPresent(descriptor::setAvailabilities);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Converts a collection of strings into a Optional List of Departments. {@code departments} must contain at
     * least one string.
     */
    private Optional<List<Department>> parseDepartmentsForEdit(Collection<String> departments) throws ParseException {
        assert departments != null;

        if (departments.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        List<Department> parsedDepartments = ParserUtil.parseDepartments(departments);
        // there should not be duplicate departments
        if (CollectionUtil.collectionHasDuplicate(parsedDepartments)) {
            throw new ParseException(EditCommand.MESSAGE_DUPLICATE_DEPARTMENT);
        }

        return Optional.of(parsedDepartments);
    }

    /**
     * Converts a collection of strings into a Optional List of Slots. {@code slots} must contain at least one string.
     */
    private Optional<List<Slot>> parseSlotsForEdit(Collection<String> slots) throws ParseException {
        assert slots != null;

        if (slots.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        List<Slot> parsedSlots = ParserUtil.parseSlots(slots);
        // there should not be duplicate slots
        if (CollectionUtil.collectionHasDuplicate(parsedSlots)) {
            throw new ParseException(EditCommand.MESSAGE_DUPLICATE_SLOT);
        }

        return Optional.of(parsedSlots);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
