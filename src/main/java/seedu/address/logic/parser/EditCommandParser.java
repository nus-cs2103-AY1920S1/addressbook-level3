package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditIntervieweeCommand;
import seedu.address.logic.commands.EditIntervieweeCommand.EditIntervieweeDescriptor;
import seedu.address.logic.commands.EditInterviewerCommand;
import seedu.address.logic.commands.EditInterviewerCommand.EditInterviewerDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_ROLE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

        if (role.value.equals("interviewee")) {
            EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
            fillIntervieweeDescriptor(descriptor, argMultimap);

            if (!descriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditIntervieweeCommand(name, descriptor);
        }
        if (role.value.equals("interviewer")) {
            EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
            fillInterviewerDescriptor(descriptor, argMultimap);

            if (!descriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditInterviewerCommand(name, descriptor);
        }

        throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR); // should not reach this point
    }

    /**
     * Fills the given interviewee descriptor with necessary values from the argumentMultiMap.
     */
    private void fillIntervieweeDescriptor(EditIntervieweeDescriptor d, ArgumentMultimap a) throws ParseException {
        if (a.getValue(PREFIX_NAME).isPresent()) {
            d.setName(ParserUtil.parseName(a.getValue(PREFIX_NAME).get()));
        }
        if (a.getValue(PREFIX_PHONE).isPresent()) {
            d.setPhone(ParserUtil.parsePhone(a.getValue(PREFIX_PHONE).get()));
        }
        if (a.getValue(PREFIX_FACULTY).isPresent()) {
            d.setFaculty(ParserUtil.parseFaculty(a.getValue(PREFIX_FACULTY).get()));
        }
        if (a.getValue(PREFIX_YEAR_OF_STUDY).isPresent()) {
            d.setYearOfStudy(ParserUtil.parseYearOfStudy(a.getValue(PREFIX_YEAR_OF_STUDY).get()));
        }
        Emails emails = new Emails();
        if (a.getValue(PREFIX_PERSONAL_EMAIL).isPresent()) {
            emails.addPersonalEmail(ParserUtil.parseEmail(a.getValue(PREFIX_PERSONAL_EMAIL).get()));
        }
        if (a.getValue(PREFIX_NUS_WORK_EMAIL).isPresent()) {
            emails.addNusEmail(ParserUtil.parseEmail(a.getValue(PREFIX_NUS_WORK_EMAIL).get()));
        }
        d.setEmails(emails);

        // parse collections for edit
        parseTagsForEdit(a.getAllValues(PREFIX_TAG)).ifPresent(d::setTags);
        parseDepartmentsForEdit(a.getAllValues(PREFIX_DEPARTMENT)).ifPresent(d::setDepartmentChoices);
        parseSlotsForEdit(a.getAllValues(PREFIX_SLOT)).ifPresent(d::setAvailableTimeslots);
    }

    /**
     * Fills the given interviewer descriptor with necessary values from the argumentMultiMap.
     */
    private void fillInterviewerDescriptor(EditInterviewerDescriptor d, ArgumentMultimap a) throws ParseException {
        if (a.getValue(PREFIX_NAME).isPresent()) {
            d.setName(ParserUtil.parseName(a.getValue(PREFIX_NAME).get()));
        }
        if (a.getValue(PREFIX_PHONE).isPresent()) {
            d.setPhone(ParserUtil.parsePhone(a.getValue(PREFIX_PHONE).get()));
        }
        if (a.getValue(PREFIX_NUS_WORK_EMAIL).isPresent()) {
            d.setEmail(ParserUtil.parseEmail(a.getValue(PREFIX_NUS_WORK_EMAIL).get()));
        }
        if (a.getValue(PREFIX_DEPARTMENT).isPresent()) {
            d.setDepartment(ParserUtil.parseDepartment(a.getValue(PREFIX_DEPARTMENT).get()));
        }

        // parse collections for edit
        parseTagsForEdit(a.getAllValues(PREFIX_TAG)).ifPresent(d::setTags);
        parseSlotsForEdit(a.getAllValues(PREFIX_SLOT)).ifPresent(d::setAvailabilities);
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
     * Converts a collection of strings into a Optional List of Departments.
     */
    private Optional<List<Department>> parseDepartmentsForEdit(Collection<String> departments) throws ParseException {
        assert departments != null;

        if (departments.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = departments.size() == 1 && departments.contains("")
                ? Collections.emptyList() : departments;
        return Optional.of(ParserUtil.parseDepartments(departments));
    }

    /**
     * Converts a collection of strings into a Optional List of Slots.
     */
    private Optional<List<Slot>> parseSlotsForEdit(Collection<String> slots) throws ParseException {
        assert slots != null;

        if (slots.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = slots.size() == 1 && slots.contains("")
                ? Collections.emptyList() : slots;
        return Optional.of(ParserUtil.parseSlots(slots));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
