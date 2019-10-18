package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.COST_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.COST_DESC_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_COST_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_SECOND_SPENDING;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_THIRD_SPENDING;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.logic.commands.EditCommand;
import seedu.moneygowhere.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.tag.Tag;
import seedu.moneygowhere.testutil.EditSpendingDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_COST_DESC, Cost.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid date followed by valid remark
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + REMARK_DESC_AMY, Date.MESSAGE_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DATE_DESC_BOB + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + VALID_REMARK_AMY + VALID_COST_AMY + VALID_DATE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SPENDING;
        String userInput = targetIndex.getOneBased() + DATE_DESC_BOB + TAG_DESC_HUSBAND
                + REMARK_DESC_AMY + COST_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDate(VALID_DATE_BOB).withRemark(VALID_REMARK_AMY).withCost(VALID_COST_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SPENDING;
        String userInput = targetIndex.getOneBased() + DATE_DESC_BOB + REMARK_DESC_AMY;

        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withDate(VALID_DATE_BOB)
                .withRemark(VALID_REMARK_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_SPENDING;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
        descriptor = new EditSpendingDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = targetIndex.getOneBased() + REMARK_DESC_AMY;
        descriptor = new EditSpendingDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + COST_DESC_AMY;
        descriptor = new EditSpendingDescriptorBuilder().withCost(VALID_COST_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditSpendingDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SPENDING;
        String userInput = targetIndex.getOneBased() + DATE_DESC_AMY + REMARK_DESC_AMY
                + TAG_DESC_FRIEND + DATE_DESC_AMY + REMARK_DESC_AMY + TAG_DESC_FRIEND
                + DATE_DESC_BOB + COST_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND;

        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withDate(VALID_DATE_BOB)
                .withRemark(VALID_REMARK_BOB).withCost(VALID_COST_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SPENDING;
        String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_BOB;
        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withDate(VALID_DATE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + REMARK_DESC_BOB + INVALID_DATE_DESC + COST_DESC_BOB
                + DATE_DESC_BOB;
        descriptor = new EditSpendingDescriptorBuilder().withDate(VALID_DATE_BOB).withRemark(VALID_REMARK_BOB)
                .withCost(VALID_COST_BOB).build();

        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_SPENDING;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
