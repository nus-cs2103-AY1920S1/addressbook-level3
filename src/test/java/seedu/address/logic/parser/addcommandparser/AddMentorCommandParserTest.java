package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGANISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMentors.BOB;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.addcommand.AddMentorCommand;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.SubjectName;
import seedu.address.testutil.MentorBuilder;

class AddMentorCommandParserTest {

    private AddMentorCommandParser parser = new AddMentorCommandParser();

    @Disabled
    @Test
    void parse_allFieldsPresent_success() {
        Mentor expectedMentor = new MentorBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));

        // multiple subjects - last subject accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + SUBJECT_DESC_AMY
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));

        // multiple organizations - last organization accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ORGANIZATION_DESC_AMY
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));
    }

    @Test
    void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMentorCommand.MESSAGE_USAGE);

        // Missing Name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, expectedMessage);

        // Missing Phone Number prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, expectedMessage);

        // Missing Email Prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, expectedMessage);

        // Missing Subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_SUBJECT_BOB + ORGANIZATION_DESC_BOB, expectedMessage);

        // Missing  Organization prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + VALID_ORGANIZATION_BOB, expectedMessage);

        // All prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_SUBJECT_BOB + VALID_ORGANIZATION_BOB, expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {

        // Invalid Name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // Invalid Phone Number
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // Invalid Email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // Invalid Subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_SUBJECT_DESC + ORGANIZATION_DESC_BOB, SubjectName.MESSAGE_CONSTRAINTS);

        // Invalid Organization
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + INVALID_ORGANISATION_DESC, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMentorCommand.MESSAGE_USAGE));
    }

}
