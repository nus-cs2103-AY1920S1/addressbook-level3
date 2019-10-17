package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalSpendings.AMY;
import static seedu.address.testutil.TypicalSpendings.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.spending.Cost;
import seedu.address.model.spending.Date;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.SpendingBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Spending expectedSpending = new SpendingBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB
                + COST_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedSpending));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB
                + COST_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedSpending));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_AMY + DATE_DESC_BOB + REMARK_DESC_BOB
                + COST_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedSpending));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_AMY + REMARK_DESC_BOB
                + COST_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedSpending));

        // multiple cost - last cost accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB + COST_DESC_AMY
                + COST_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedSpending));

        // multiple tags - all accepted
        Spending expectedSpendingMultipleTags = new SpendingBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB + COST_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedSpendingMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Spending expectedSpending = new SpendingBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + DATE_DESC_AMY + REMARK_DESC_AMY + COST_DESC_AMY,
                new AddCommand(expectedSpending));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + DATE_DESC_BOB + REMARK_DESC_BOB + COST_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_DATE_BOB + REMARK_DESC_BOB + COST_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + VALID_REMARK_BOB + COST_DESC_BOB,
                expectedMessage);

        // missing cost prefix
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB + VALID_COST_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_DATE_BOB + VALID_REMARK_BOB + VALID_COST_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_BOB + REMARK_DESC_BOB + COST_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_DATE_DESC + REMARK_DESC_BOB + COST_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid cost
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB + INVALID_COST_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Cost.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB + COST_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_BOB + REMARK_DESC_BOB + INVALID_COST_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + DATE_DESC_BOB + REMARK_DESC_BOB
                        + COST_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
