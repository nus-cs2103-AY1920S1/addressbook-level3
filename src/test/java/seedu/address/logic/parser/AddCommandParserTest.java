package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_NUS_WORK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_NUS_WORK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_PERSONAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_PERSONAL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FACULTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FACULTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SLOT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SLOT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_OF_STUDY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_OF_STUDY_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY_INTERVIEWEE;
import static seedu.address.testutil.TypicalPersons.BOB_INTERVIEWEE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.IntervieweeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    // TODO: Add more tests according to how AddCommandParser has been changed.
    @Test
    public void parse_allFieldsPresent_success() {
        Interviewee expectedPerson = new IntervieweeBuilder(BOB_INTERVIEWEE).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ROLE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, ROLE_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, ROLE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedPerson));

        // multiple tags - all accepted
        Interviewee expectedPersonMultipleTags = new IntervieweeBuilder(BOB_INTERVIEWEE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, ROLE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                new AddIntervieweeCommand(expectedPersonMultipleTags));
        // TODO: more test cases (ken)
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Interviewee expectedPerson = new IntervieweeBuilder(AMY_INTERVIEWEE).withTags().build();
        assertParseSuccess(parser, ROLE_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + FACULTY_DESC_AMY + YEAR_OF_STUDY_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY
                + EMAIL_PERSONAL_DESC_AMY + EMAIL_NUS_WORK_DESC_AMY,
                new AddIntervieweeCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, ROLE_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, ROLE_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, ROLE_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_FACULTY_BOB + VALID_YEAR_OF_STUDY_BOB + VALID_DEPARTMENT_BOB + VALID_SLOT_BOB
                + VALID_PERSONAL_EMAIL_BOB + VALID_NUS_WORK_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, ROLE_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, ROLE_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, ROLE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, ROLE_DESC_BOB + INVALID_NAME_DESC + INVALID_PHONE_DESC
                + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB
                + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ROLE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FACULTY_DESC_BOB + YEAR_OF_STUDY_DESC_BOB
                + DEPARTMENT_DESC_BOB + SLOT_DESC_BOB + EMAIL_PERSONAL_DESC_BOB + EMAIL_NUS_WORK_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
