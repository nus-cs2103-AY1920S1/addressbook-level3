package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVALID_MEMBER_NAME_DESC;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.pluswork.logic.commands.CommandTestUtil.MEMBER_ID_JOHN_DOE;
import static seedu.pluswork.logic.commands.CommandTestUtil.MEMBER_NAME_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.MEMBER_NAME_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.MEMBER_NAME_JOHN_DOE;
import static seedu.pluswork.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.pluswork.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TAG_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TAG_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.TAG_DESC_URGENCY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pluswork.testutil.TypicalTasksMembers.JOHN_DOE;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.AddMemberCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.testutil.MemberBuilder;

public class AddMemberCommandParserTest {
    private AddMemberCommandParser parser = new AddMemberCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws CommandException {
        Member expectedMember = new MemberBuilder(JOHN_DOE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_MEMBER_NAME + "John Doe" + PREFIX_MEMBER_ID
                + "JD" + TAG_DESC_PUBLICITY, new AddMemberCommand(expectedMember));

        // multiple names - last name accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_FINANCE + MEMBER_NAME_JOHN_DOE + MEMBER_ID_JOHN_DOE
                 + TAG_DESC_PUBLICITY, new AddMemberCommand(expectedMember));

        // multiple tags - all accepted
        Member expectedMemberMultipleTags = new MemberBuilder(JOHN_DOE).withId(new MemberId(MEMBER_ID_JOHN_DOE))
                .withTags(VALID_TAG_PUBLICITY, VALID_TAG_URGENCY)
                .build();
        assertParseSuccess(parser, MEMBER_NAME_JOHN_DOE + MEMBER_ID_JOHN_DOE
                + TAG_DESC_URGENCY + TAG_DESC_PUBLICITY, new AddMemberCommand(expectedMemberMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws CommandException {
        // zero tags
        Member expectedMember = new MemberBuilder(JOHN_DOE).withTags().build();
        assertParseSuccess(parser, MEMBER_NAME_JOHN_DOE + MEMBER_ID_JOHN_DOE, new AddMemberCommand(expectedMember));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws CommandException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, MEMBER_NAME_JOHN_DOE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, MEMBER_NAME_JOHN_DOE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() throws CommandException {
        // invalid name
        assertParseFailure(parser, INVALID_MEMBER_NAME_DESC + MEMBER_ID_JOHN_DOE + TAG_DESC_FINANCE
                        + TAG_DESC_PUBLICITY, MemberName.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, MEMBER_NAME_DESC_PUBLICITY + MEMBER_ID_JOHN_DOE + INVALID_TAG_DESC
                        + VALID_TAG_PUBLICITY, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEMBER_NAME_DESC + MEMBER_ID_JOHN_DOE + INVALID_TAG_DESC,
                MemberName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEMBER_NAME_DESC_PUBLICITY + MEMBER_ID_JOHN_DOE
                        + TAG_DESC_FINANCE + TAG_DESC_PUBLICITY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
    }
}
