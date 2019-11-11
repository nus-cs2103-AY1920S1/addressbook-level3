package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.scheduler.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.scheduler.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.scheduler.logic.commands.CommandTestUtil.SLOT_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.SLOT_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_ROLE_AMY_INTVR;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_ROLE_BOB_INTVE;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.YEAR_OF_STUDY_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.YEAR_OF_STUDY_DESC_BOB;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.scheduler.testutil.TypicalPersons.AMY_INTERVIEWER_MANUAL;
import static seedu.scheduler.testutil.TypicalPersons.BOB_INTERVIEWEE_MANUAL;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.AddIntervieweeCommand;
import seedu.scheduler.logic.commands.AddInterviewerCommand;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.testutil.IntervieweeBuilder;
import seedu.scheduler.testutil.InterviewerBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_intervieweeAllFieldsPresent_success() {
        Interviewee expectedInterviewee = new IntervieweeBuilder(BOB_INTERVIEWEE_MANUAL).withTags(VALID_TAG_FRIEND)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB
                        + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple names - last name accepted
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_AMY
                        + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple tags - all accepted
        Interviewee expectedIntervieweeMultipleTags = new IntervieweeBuilder(BOB_INTERVIEWEE_MANUAL)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedIntervieweeMultipleTags));

        // multiple personal emails - last personal email accepted
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_AMY + EMAIL_PERSONAL_DESC_BOB
                + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple work emails - last work email accepted
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB
                        + EMAIL_NUS_WORK_DESC_AMY + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple faculties - last faculty accepted
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_AMY + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple years of study - last year of study accepted
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_AMY + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedInterviewee));

        // multiple slots - all accepted
        Interviewee expectedIntervieweeMultipleSlots = new IntervieweeBuilder(BOB_INTERVIEWEE_MANUAL)
                .withTimeslots(VALID_SLOT_AMY, VALID_SLOT_BOB)
                .build();
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND
                        + TAG_DESC_HUSBAND
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB
                        + SLOT_DESC_AMY + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedIntervieweeMultipleSlots));

        // multiple departments - all accepted
        Interviewee expectedIntervieweeMultipleDepts = new IntervieweeBuilder(BOB_INTERVIEWEE_MANUAL)
                .withDepartmentChoices(VALID_DEPARTMENT_AMY, VALID_DEPARTMENT_BOB)
                .build();
        assertParseSuccess(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND
                        + TAG_DESC_HUSBAND
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_AMY + DEPARTMENT_DESC_BOB
                        + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedIntervieweeMultipleDepts));
    }

    @Test
    public void parse_intervieweeDuplicateSlots_failure() {
        // exact same slots - fails
        String intervieweeDuplicateSlots = " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB;
        assertParseFailure(parser, intervieweeDuplicateSlots, AddCommand.MESSAGE_DUPLICATE_SLOT);
    }

    @Test
    public void parse_intervieweeDuplicateDepartments_failure() {
        // exact same string - fails
        String intervieweeDuplicateDepartments = " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + DEPARTMENT_DESC_BOB
                + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB;
        assertParseFailure(parser, intervieweeDuplicateDepartments, AddCommand.MESSAGE_DUPLICATE_DEPARTMENT);

        // same string when we ignore case - fails (d/marketing vs d/Marketing)
        intervieweeDuplicateDepartments = " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB.toLowerCase() + DEPARTMENT_DESC_BOB
                + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB;
        assertParseFailure(parser, intervieweeDuplicateDepartments, AddCommand.MESSAGE_DUPLICATE_DEPARTMENT);
    }

    @Test
    public void parse_interviewerDuplicateSlots_failure() {
        // exact same slots - fails
        String interviewerDuplicateSlots = " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY
                + TAG_DESC_FRIEND + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY + SLOT_DESC_AMY;
        assertParseFailure(parser, interviewerDuplicateSlots, AddCommand.MESSAGE_DUPLICATE_SLOT);
    }

    @Test
    public void parse_interviewerAllFieldsPresent_success() {
        Interviewer expectedInterviewer = new InterviewerBuilder(AMY_INTERVIEWER_MANUAL).withTags(VALID_TAG_FRIEND)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY
                        + PHONE_DESC_AMY
                        + TAG_DESC_FRIEND + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedInterviewer));

        // multiple names - last name accepted
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_BOB + NAME_DESC_AMY
                        + PHONE_DESC_AMY + TAG_DESC_FRIEND + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY
                        + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedInterviewer));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_BOB
                        + PHONE_DESC_AMY + TAG_DESC_FRIEND + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY
                        + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedInterviewer));

        // multiple tags - all accepted
        Interviewer expectedInterviewerMultipleTags = new InterviewerBuilder(AMY_INTERVIEWER_MANUAL)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY
                        + PHONE_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + EMAIL_NUS_WORK_DESC_AMY
                        + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedInterviewerMultipleTags));

        // multiple work emails - last work email accepted
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + EMAIL_NUS_WORK_DESC_BOB + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedInterviewer));

        // multiple departments - last department accepted
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_BOB + DEPARTMENT_DESC_AMY
                        + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedInterviewer));

        // multiple slots - all accepted
        Interviewer expectedInterviewerMultipleSlots = new InterviewerBuilder(AMY_INTERVIEWER_MANUAL)
                .withAvailabilities(VALID_SLOT_AMY, VALID_SLOT_BOB)
                .build();
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY
                        + PHONE_DESC_AMY + TAG_DESC_FRIEND + EMAIL_NUS_WORK_DESC_AMY
                        + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY + SLOT_DESC_BOB,
                new AddInterviewerCommand(expectedInterviewerMultipleSlots));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Interviewer expectedPerson = new InterviewerBuilder(AMY_INTERVIEWER_MANUAL).withTags().build();
        assertParseSuccess(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY,
                new AddInterviewerCommand(expectedPerson));
    }

    @Test
    public void parse_intervieweeCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + PHONE_DESC_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing phone
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing personal email
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                        + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing nus work email
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                        + EMAIL_PERSONAL_DESC_BOB,
                expectedMessage);

        // missing faculty
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                        + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing year of study
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + FACULTY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                        + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing timeslot prefix
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB
                        + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing department
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + SLOT_DESC_BOB
                        + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + " " + VALID_NAME_BOB + " " + VALID_PHONE_BOB
                + " " + VALID_FACULTY_BOB + " " + VALID_YEAR_OF_STUDY_BOB + " " + VALID_DEPARTMENT_BOB
                + " " + VALID_SLOT_BOB + " " + VALID_PERSONAL_EMAIL_BOB + " " + VALID_NUS_WORK_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_interviewerCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + PHONE_DESC_AMY
                        + TAG_DESC_FRIEND + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY,
                expectedMessage);

        // missing phone
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY
                        + TAG_DESC_FRIEND + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY,
                expectedMessage);

        // missing department
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY
                        + TAG_DESC_FRIEND + SLOT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY,
                expectedMessage);

        // missing slot
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + DEPARTMENT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY,
                expectedMessage);

        // missing work email
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + " " + VALID_NAME_AMY + " " + VALID_PHONE_AMY
                        + " " + VALID_TAG_FRIEND + " " + VALID_DEPARTMENT_AMY + " " + VALID_SLOT_AMY
                        + " " + VALID_NUS_WORK_EMAIL_AMY,
                expectedMessage);
    }

    @Test
    public void parse_intervieweeInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_TAG_DESC + VALID_TAG_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid faculty
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + INVALID_FACULTY_DESC + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Faculty.MESSAGE_CONSTRAINTS);

        // invalid year of study
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + INVALID_YEAR_OF_STUDY
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Messages.MESSAGE_INVALID_YEAR_OF_STUDY);

        // invalid department
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + INVALID_DEPARTMENT_DESC + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Department.MESSAGE_CONSTRAINTS);

        // invalid slot
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + INVALID_SLOT_DESC + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Slot.MESSAGE_CONSTRAINTS);

        // invalid personal email
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + INVALID_PERSONAL_EMAIL_DESC + EMAIL_NUS_WORK_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid work email
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + INVALID_NUS_WORK_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + VALID_ROLE_BOB_INTVE + INVALID_NAME_DESC + INVALID_PHONE_DESC
                        + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                        + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + VALID_ROLE_BOB_INTVE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                        + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_interviewerInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + INVALID_NAME_DESC + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + DEPARTMENT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY + SLOT_DESC_AMY,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + INVALID_PHONE_DESC + TAG_DESC_FRIEND
                        + DEPARTMENT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY + SLOT_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_TAG_DESC
                        + DEPARTMENT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY + SLOT_DESC_AMY,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid department
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + INVALID_DEPARTMENT_DESC + EMAIL_NUS_WORK_DESC_AMY + SLOT_DESC_AMY,
                Department.MESSAGE_CONSTRAINTS);

        // invalid work email
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + DEPARTMENT_DESC_AMY + INVALID_NUS_WORK_EMAIL_DESC + SLOT_DESC_AMY,
                Email.MESSAGE_CONSTRAINTS);

        // invalid slot
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + DEPARTMENT_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY + INVALID_SLOT_DESC,
                Slot.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                        + DEPARTMENT_DESC_AMY + INVALID_NUS_WORK_EMAIL_DESC + INVALID_SLOT_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY
                        + TAG_DESC_FRIEND + DEPARTMENT_DESC_AMY + VALID_NUS_WORK_EMAIL_BOB + SLOT_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
