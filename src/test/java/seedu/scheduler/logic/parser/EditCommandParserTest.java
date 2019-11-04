package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_YEAR_OF_STUDY;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_DEPARTMENT_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_FACULTY_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_NUS_WORK_EMAIL_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_PERSONAL_EMAIL_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_SLOT_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_YEAR_OF_STUDY;
import static seedu.scheduler.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.ROLE_DESC_AMY_INTVR;
import static seedu.scheduler.logic.commands.CommandTestUtil.ROLE_DESC_BOB_INTVE;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.logic.commands.EditCommand.MESSAGE_USAGE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

public class EditCommandParserTest {

    // TODO: Fix this piping hot mess
    //private static final String TAG_EMPTY = " " + PREFIX_TAG;

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
        assertParseFailure(parser, null + ROLE_DESC_BOB_INTVE, MESSAGE_INVALID_FORMAT);
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
        assertParseFailure(parser, null + ROLE_DESC_AMY_INTVR, MESSAGE_INVALID_FORMAT);
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
                + INVALID_FACULTY_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid faculty - blank spaces
        assertParseFailure(parser, VALID_NAME_BOB + ROLE_DESC_BOB_INTVE
                + (" " + PREFIX_FACULTY + "    "), Name.MESSAGE_CONSTRAINTS);

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

        // parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Interviewee} being edited,
        // parsing it together with a valid tag results in error

        // parsing {@code PREFIX_DEPARTMENT} alone will reset the tags of the {@code Interviewee} being edited,
        // parsing it together with a valid department results in error

        // parsing {@code PREFIX_SLOT} alone will reset the tags of the {@code Interviewee} being edited,
        // parsing it together with a valid slot results in error
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

        // multiple valid slot input with one invalid input

        // parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Interviewer} being edited,
        // parsing it together with a valid tag results in error
        String TAG_EMPTY = " " + PREFIX_TAG;
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + TAG_EMPTY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + TAG_DESC_HUSBAND + TAG_EMPTY + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // parsing an empty {@code PREFIX_SLOT} alone will result in error. Parsing it together with a valid slot
        // will result in error
        String SLOT_EMPTY = " " + PREFIX_SLOT;
        assertParseFailure(parser, VALID_NAME_AMY + ROLE_DESC_AMY_INTVR
                + SLOT_EMPTY, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        /*
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
        */
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        /*
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
        */
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        /*
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
        */
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        /*
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        */
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        /*
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
        */
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
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
    }

    @Test
    public void parse_resetTags_success() {
        /*
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
        */
    }
}
