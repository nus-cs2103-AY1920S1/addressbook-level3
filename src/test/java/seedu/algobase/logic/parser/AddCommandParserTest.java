package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.commands.CommandTestUtil.AUTHOR_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.AUTHOR_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_WEBLINK_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.algobase.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.algobase.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.WEBLINK_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.WEBLINK_DESC_BOB;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.algobase.testutil.TypicalProblems.AMY;
import static seedu.algobase.testutil.TypicalProblems.BOB;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.commands.AddCommand;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.testutil.ProblemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Problem expectedProblem = new ProblemBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple authors - last author accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AUTHOR_DESC_AMY + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple weblinks - last weblink accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_AMY + WEBLINK_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple descriptiones - last description accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + DESCRIPTION_DESC_AMY
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedProblem));

        // multiple tags - all accepted
        Problem expectedProblemMultipleTags = new ProblemBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedProblemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Problem expectedProblem = new ProblemBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + AUTHOR_DESC_AMY + WEBLINK_DESC_AMY + DESCRIPTION_DESC_AMY,
                new AddCommand(expectedProblem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing author prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_AUTHOR_BOB + WEBLINK_DESC_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing weblink prefix
        assertParseFailure(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + VALID_WEBLINK_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + VALID_DESCRIPTION_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_AUTHOR_BOB + VALID_WEBLINK_BOB + VALID_DESCRIPTION_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid author
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_AUTHOR_DESC + WEBLINK_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Author.MESSAGE_CONSTRAINTS);

        // invalid weblink
        assertParseFailure(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + INVALID_WEBLINK_DESC + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, WebLink.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + INVALID_DESCRIPTION_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + DESCRIPTION_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB + INVALID_DESCRIPTION_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + AUTHOR_DESC_BOB + WEBLINK_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
