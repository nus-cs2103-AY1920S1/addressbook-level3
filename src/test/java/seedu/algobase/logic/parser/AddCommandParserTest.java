package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.algobase.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.algobase.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.algobase.testutil.TypicalProblems.AMY;
import static seedu.algobase.testutil.TypicalProblems.BOB;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.commands.AddCommand;
import seedu.algobase.model.Problem.*;
import seedu.algobase.model.Problem.WebLink;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.testutil.ProblemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Problem expectedProblem = new ProblemBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple authors - last author accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple weblinks - last weblink accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple descriptiones - last description accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple tags - all accepted
        Problem expectedProblemMultipleTags = new ProblemBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedProblemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Problem expectedProblem = new ProblemBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedProblem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing author prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing weblink prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid author
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Author.MESSAGE_CONSTRAINTS);

        // invalid weblink
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, WebLink.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
