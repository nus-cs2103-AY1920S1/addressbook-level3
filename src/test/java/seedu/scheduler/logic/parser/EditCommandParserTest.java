package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_YEAR_OF_STUDY;
import static seedu.scheduler.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.DEPARTMENT_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.EMAIL_NUS_WORK_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.EMAIL_NUS_WORK_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.EMAIL_PERSONAL_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.EMAIL_PERSONAL_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.FACULTY_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.FACULTY_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_DEPARTMENT_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_FACULTY_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_NUS_WORK_EMAIL_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_PERSONAL_EMAIL_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_SLOT_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_YEAR_OF_STUDY;
import static seedu.scheduler.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.ROLE_DESC_AMY_INTVR;
import static seedu.scheduler.logic.commands.CommandTestUtil.ROLE_DESC_BOB_INTVE;
import static seedu.scheduler.logic.commands.CommandTestUtil.SLOT_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.SLOT_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FACULTY_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.YEAR_OF_STUDY_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.YEAR_OF_STUDY_DESC_BOB;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EditIntervieweeCommand;
import seedu.scheduler.logic.commands.EditIntervieweeCommand.EditIntervieweeDescriptor;
import seedu.scheduler.logic.commands.EditInterviewerCommand;
import seedu.scheduler.logic.commands.EditInterviewerCommand.EditInterviewerDescriptor;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String DEPARTMENT_EMPTY = " " + PREFIX_DEPARTMENT;
    private static final String SLOT_EMPTY = " " + PREFIX_SLOT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_intervieweeMissingParts_failure() {
        // no name specified
        assertParseFailure(parser, ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);

        // no role specified
        assertParseFailure(parser, VALID_NAME_BOB, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, EditCommand.MESSAGE_NOT_EDITED);

        // no name and no role specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_interviewerMissingParts_failure() {
        // no name specified
        assertParseFailure(parser, ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);

        // no role specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, EditCommand.MESSAGE_NOT_EDITED);

        // no name and no role specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    /**
     * A valid preamble only contains alphanumeric characters, and should not be blank.
     */
    @Test
    public void parse_intervieweeInvalidPreamble_failure() {
        // preamble with special characters
        assertParseFailure(parser, "-5" + VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "!" + VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "@" + VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "<>" + VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "~" + VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);

        // preamble with unicode characters
        assertParseFailure(parser, "\u2202" + VALID_NAME_BOB + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being passed as preamble
        assertParseFailure(parser, "" + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " " + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "    " + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_interviewerInvalidPreamble_failure() {
        // preamble with special characters
        assertParseFailure(parser, "-5" + VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "!" + VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "@" + VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "<>" + VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "~" + VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);

        // preamble with unicode characters
        assertParseFailure(parser, "\u2202" + VALID_NAME_AMY + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);

        // invalid arguments being passed as preamble
        assertParseFailure(parser, "" + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " " + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "    " + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_intervieweeInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // invalid personal email
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_PERSONAL_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        // invalid work email
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_NUS_WORK_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        // invalid faculty - empty faculty
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_FACULTY_DESC, Faculty.MESSAGE_CONSTRAINTS);

        // invalid faculty - blank spaces
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + (" " + PREFIX_FACULTY + "    "), Faculty.MESSAGE_CONSTRAINTS);

        // invalid year of study - alphabets
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_YEAR_OF_STUDY, MESSAGE_INVALID_YEAR_OF_STUDY);

        // invalid year of study - greater than 2^31 - 1
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + (" " + PREFIX_YEAR_OF_STUDY + "999999999999999999"), MESSAGE_INVALID_YEAR_OF_STUDY);

        // invalid year of study - negative integer
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + (" " + PREFIX_YEAR_OF_STUDY + "-1"), MESSAGE_INVALID_YEAR_OF_STUDY);

        // invalid department - empty department
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_DEPARTMENT_DESC, Department.MESSAGE_CONSTRAINTS);

        // invalid department - blank spaces
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + (" " + PREFIX_DEPARTMENT + "    "), Department.MESSAGE_CONSTRAINTS);

        // invalid slot - incorrect format
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_SLOT_DESC, Slot.MESSAGE_CONSTRAINTS);

        // invalid slot - start and ends at the same time
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + (" " + PREFIX_SLOT + "03/12/2019 00:00-00:00"), Slot.MESSAGE_CONSTRAINTS);

        // valid input (phone) followed by invalid input (phone)
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple valid slot input with one invalid input
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_SLOT_DESC + VALID_SLOT_BOB + VALID_SLOT_AMY, Slot.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Interviewer} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + TAG_EMPTY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + TAG_DESC_HUSBAND + TAG_EMPTY + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_DEPARTMENT} alone will result in department parse error,
        // parsing it together with a valid department results in department parse error
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + DEPARTMENT_EMPTY, Department.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + DEPARTMENT_DESC_BOB + DEPARTMENT_EMPTY, Department.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + DEPARTMENT_EMPTY + DEPARTMENT_DESC_BOB, Department.MESSAGE_CONSTRAINTS);

        // parsing an empty {@code PREFIX_SLOT} alone will result in slot parse error.
        // Parsing it together with a valid slot will result in slot parse error.
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + SLOT_EMPTY, Slot.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + SLOT_DESC_BOB + SLOT_EMPTY, Slot.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + SLOT_DESC_BOB + SLOT_EMPTY + SLOT_DESC_AMY, Slot.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only first invalid value captured
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + INVALID_PHONE_DESC + EMAIL_NUS_WORK_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_interviewerInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // invalid slot - incorrect format
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_SLOT_DESC, Slot.MESSAGE_CONSTRAINTS);

        // invalid slot - start and ends at the same time
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + (" " + PREFIX_SLOT + "03/12/2019 00:00-00:00"), Slot.MESSAGE_CONSTRAINTS);

        // invalid department - empty department
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_DEPARTMENT_DESC, Department.MESSAGE_CONSTRAINTS);

        // invalid department - blank spaces
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + (" " + PREFIX_DEPARTMENT + "    "), Department.MESSAGE_CONSTRAINTS);

        // invalid work email
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_NUS_WORK_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        // valid input (phone) followed by invalid input (phone)
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + PHONE_DESC_AMY + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Interviewer} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + TAG_EMPTY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + TAG_DESC_HUSBAND + TAG_EMPTY + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // parsing an empty {@code PREFIX_SLOT} alone will result in slot parse error.
        // Parsing it together with a valid slot will result in slot parse error.
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + SLOT_EMPTY, Slot.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + SLOT_DESC_AMY + SLOT_EMPTY, Slot.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + SLOT_DESC_AMY + SLOT_EMPTY + SLOT_DESC_BOB, Slot.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only first value is captured
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + INVALID_PHONE_DESC + INVALID_NUS_WORK_EMAIL_DESC, Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_editIntervieweeDuplicateSlots_failure() {
        // exact same slots - fails
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_NUS_WORK_DESC_AMY + EMAIL_PERSONAL_DESC_AMY + FACULTY_DESC_AMY + YEAR_OF_STUDY_DESC_AMY
                + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY + SLOT_DESC_AMY;
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_DUPLICATE_SLOT);
    }

    @Test
    public void parse_editIntervieweeDuplicateDepartments_failure() {
        // exact same departments - fails
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + EMAIL_NUS_WORK_DESC_AMY + EMAIL_PERSONAL_DESC_AMY + FACULTY_DESC_AMY + YEAR_OF_STUDY_DESC_AMY
                + DEPARTMENT_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY;
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_DUPLICATE_DEPARTMENT);

        // same department, but in different case (logistics vs Logistics)
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + EMAIL_NUS_WORK_DESC_AMY + EMAIL_PERSONAL_DESC_AMY + FACULTY_DESC_AMY + YEAR_OF_STUDY_DESC_AMY
                + DEPARTMENT_DESC_AMY.toLowerCase() + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY;
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_DUPLICATE_DEPARTMENT);
    }

    @Test
    public void parse_interviewerDuplicateSlots_failure() {
        // exact same slots - fails
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + NAME_DESC_BOB + PHONE_DESC_BOB + DEPARTMENT_DESC_BOB
                + EMAIL_NUS_WORK_DESC_BOB + SLOT_DESC_BOB + SLOT_DESC_BOB;
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_DUPLICATE_SLOT);
    }

    @Test
    public void parse_intervieweeAllFieldsSpecified_success() {

        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + EMAIL_NUS_WORK_DESC_AMY + EMAIL_PERSONAL_DESC_AMY + FACULTY_DESC_AMY + YEAR_OF_STUDY_DESC_AMY
                + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY;

        Name name = new Name(VALID_NAME_AMY);
        Phone phone = new Phone(VALID_PHONE_AMY);
        Set<Tag> tagSet = new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND)));
        Email workEmail = new Email(VALID_NUS_WORK_EMAIL_AMY);
        Email personalEmail = new Email(VALID_PERSONAL_EMAIL_AMY);
        Emails emails = new Emails().addNusEmail(workEmail).addPersonalEmail(personalEmail);
        Faculty faculty = new Faculty(VALID_FACULTY_AMY);
        Integer yearOfStudy = Integer.valueOf(VALID_YEAR_OF_STUDY_AMY);
        List<Department> departmentList = List.of(new Department(VALID_DEPARTMENT_AMY));
        List<Slot> slotList = List.of(Slot.fromString(VALID_SLOT_AMY));

        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        descriptor.setName(name);
        descriptor.setPhone(phone);
        descriptor.setTags(tagSet);
        descriptor.setEmails(emails);
        descriptor.setFaculty(faculty);
        descriptor.setYearOfStudy(yearOfStudy);
        descriptor.setDepartmentChoices(departmentList);
        descriptor.setAvailableTimeslots(slotList);

        EditCommand expectedCommand = new EditIntervieweeCommand(new Name(VALID_NAME_BOB), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_interviewerAllFieldsSpecified_success() {
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_FRIEND
                + EMAIL_NUS_WORK_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB;

        Name name = new Name(VALID_NAME_BOB);
        Phone phone = new Phone(VALID_PHONE_BOB);
        Set<Tag> tagSet = new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND)));
        Email workEmail = new Email(VALID_NUS_WORK_EMAIL_BOB);
        Department department = new Department(VALID_DEPARTMENT_BOB);
        List<Slot> slotList = List.of(Slot.fromString(VALID_SLOT_BOB));

        EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
        descriptor.setName(name);
        descriptor.setPhone(phone);
        descriptor.setTags(tagSet);
        descriptor.setEmail(workEmail);
        descriptor.setDepartment(department);
        descriptor.setAvailabilities(slotList);

        EditCommand expectedCommand = new EditInterviewerCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_intervieweeOneFieldSpecified_success() {
        // name
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + NAME_DESC_AMY;
        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        Name targetName = new Name(VALID_NAME_BOB);
        Name resultName = new Name(VALID_NAME_AMY);
        descriptor.setName(resultName);

        EditCommand expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + PHONE_DESC_AMY;
        descriptor = new EditIntervieweeDescriptor();
        Phone phone = new Phone(VALID_PHONE_AMY);
        descriptor.setPhone(phone);

        expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + TAG_DESC_HUSBAND;
        descriptor = new EditIntervieweeDescriptor();
        Set<Tag> tagSet = new HashSet<>(List.of(new Tag(VALID_TAG_HUSBAND)));
        descriptor.setTags(tagSet);

        expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // emails
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + EMAIL_NUS_WORK_DESC_AMY + EMAIL_PERSONAL_DESC_AMY;
        descriptor = new EditIntervieweeDescriptor();
        Email workEmail = new Email(VALID_NUS_WORK_EMAIL_AMY);
        Email personalEmail = new Email(VALID_PERSONAL_EMAIL_AMY);
        Emails emails = new Emails().addPersonalEmail(personalEmail).addNusEmail(workEmail);
        descriptor.setEmails(emails);

        expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // departments
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + DEPARTMENT_DESC_AMY;
        descriptor = new EditIntervieweeDescriptor();
        List<Department> departmentList = List.of(new Department(VALID_DEPARTMENT_AMY));
        descriptor.setDepartmentChoices(departmentList);

        expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // slots
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + SLOT_DESC_AMY;
        descriptor = new EditIntervieweeDescriptor();
        List<Slot> slotList = List.of(Slot.fromString(VALID_SLOT_AMY));
        descriptor.setAvailableTimeslots(slotList);

        expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_interviewerOneFieldSpecified_success() {
        // name
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + NAME_DESC_BOB;
        EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
        Name targetName = new Name(VALID_NAME_AMY);
        Name resultName = new Name(VALID_NAME_BOB);
        descriptor.setName(resultName);

        EditCommand expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + PHONE_DESC_BOB;
        descriptor = new EditInterviewerDescriptor();
        Phone phone = new Phone(VALID_PHONE_BOB);
        descriptor.setPhone(phone);

        expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + TAG_DESC_FRIEND;
        descriptor = new EditInterviewerDescriptor();
        Set<Tag> tagSet = new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND)));
        descriptor.setTags(tagSet);

        expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // department
        userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + DEPARTMENT_DESC_BOB;
        descriptor = new EditInterviewerDescriptor();
        Department department = new Department(VALID_DEPARTMENT_BOB);
        descriptor.setDepartment(department);

        expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // work email
        userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + EMAIL_NUS_WORK_DESC_BOB;
        descriptor = new EditInterviewerDescriptor();
        Email email = new Email(VALID_NUS_WORK_EMAIL_BOB);
        descriptor.setEmail(email);

        expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // slots
        userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + SLOT_DESC_BOB;
        descriptor = new EditInterviewerDescriptor();
        List<Slot> slotList = List.of(Slot.fromString(VALID_SLOT_BOB));
        descriptor.setAvailabilities(slotList);

        expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_intervieweeMultipleRepeatedFields_acceptsLast() {
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + FACULTY_DESC_AMY + TAG_DESC_FRIEND + FACULTY_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        Name targetName = new Name(VALID_NAME_BOB);

        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        descriptor.setFaculty(new Faculty(VALID_FACULTY_AMY));
        descriptor.setTags(new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND))));
        EditIntervieweeCommand expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_interviewerMultipleRepeatedFields_acceptsLast() {
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + EMAIL_NUS_WORK_DESC_AMY + TAG_DESC_FRIEND
                + EMAIL_NUS_WORK_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        Name targetName = new Name(VALID_NAME_AMY);

        EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
        descriptor.setEmail(new Email(VALID_NUS_WORK_EMAIL_AMY));
        descriptor.setTags(new HashSet<>(List.of(new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_FRIEND))));
        EditInterviewerCommand expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_intervieweeInvalidValueFollowedByValidValue_success() {
        /*
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        */
        // no other valid values specified
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + INVALID_PHONE_DESC + PHONE_DESC_AMY;
        Name targetname = new Name(VALID_NAME_BOB);

        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        descriptor.setPhone(new Phone(VALID_PHONE_AMY));
        EditIntervieweeCommand expectedCommand = new EditIntervieweeCommand(targetname, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + FACULTY_DESC_AMY + INVALID_PHONE_DESC + PHONE_DESC_AMY;
        descriptor = new EditIntervieweeDescriptor();
        descriptor.setPhone(new Phone(VALID_PHONE_AMY));
        descriptor.setFaculty(new Faculty(VALID_FACULTY_AMY));
        expectedCommand = new EditIntervieweeCommand(targetname, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_interviewerInvalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        Name targetname = new Name(VALID_NAME_AMY);

        EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
        descriptor.setPhone(new Phone(VALID_PHONE_BOB));
        EditInterviewerCommand expectedCommand = new EditInterviewerCommand(targetname, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + DEPARTMENT_DESC_BOB + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditInterviewerDescriptor();
        descriptor.setPhone(new Phone(VALID_PHONE_BOB));
        descriptor.setDepartment(new Department(VALID_DEPARTMENT_BOB));
        expectedCommand = new EditInterviewerCommand(targetname, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_intervieweeResetTags_success() {
        // passing an empty tag prefix should reset all tags of an interviewee
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + TAG_EMPTY;

        Name targetName = new Name(VALID_NAME_BOB);
        EditIntervieweeDescriptor descriptor = new EditIntervieweeDescriptor();
        descriptor.setTags(new HashSet<>());

        EditIntervieweeCommand expectedCommand = new EditIntervieweeCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_interviewerResetTags_success() {
        // passing an empty tag prefix should reset all tags of an interviewer
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + TAG_EMPTY;

        Name targetName = new Name(VALID_NAME_AMY);
        EditInterviewerDescriptor descriptor = new EditInterviewerDescriptor();
        descriptor.setTags(new HashSet<>());

        EditInterviewerCommand expectedCommand = new EditInterviewerCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_intervieweeResetDepartments_failure() {
        // when editing an interviewee, empty departments cannot be passed (I.e Departments can never be empty).
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + DEPARTMENT_EMPTY;

        assertParseFailure(parser, userInput, Department.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_intervieweeResetSlots_failure() {
        // when editing an interviewee, slots must also never be empty.
        String userInput = VALID_NAME_BOB + ROLE_DESC_BOB_INTVE + SLOT_EMPTY;

        assertParseFailure(parser, userInput, Slot.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_interviewerResetSlots_failure() {
        // when editing an interviewer, slots can also never be empty.
        String userInput = VALID_NAME_AMY + ROLE_DESC_AMY_INTVR + SLOT_EMPTY;

        assertParseFailure(parser, userInput, Slot.MESSAGE_CONSTRAINTS);
    }
}
