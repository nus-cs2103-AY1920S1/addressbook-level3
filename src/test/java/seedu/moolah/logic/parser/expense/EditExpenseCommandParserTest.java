package seedu.moolah.logic.parser.expense;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_CATEGORY_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_DESCRIPTION_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_PRICE_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_PRICE_DESC_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_TIMESTAMP_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_CATEGORY_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_DESCRIPTION_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_PRICE_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_TIMESTAMP_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_TIMESTAMP_CHICKEN;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.general.Price;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_CATEGORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE);

    private EditExpenseCommandParser parser = new EditExpenseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EXPENSE_DESCRIPTION_CHICKEN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditExpenseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EXPENSE_DESCRIPTION_DESC_CHICKEN, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EXPENSE_DESCRIPTION_DESC_CHICKEN, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(
                parser,
                "1" + INVALID_EXPENSE_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser,
                "1" + INVALID_EXPENSE_PRICE_DESC, Price.MESSAGE_CONSTRAINTS); // invalid price
        assertParseFailure(parser,
                "1" + INVALID_EXPENSE_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category
        assertParseFailure(parser,
                "1" + INVALID_EXPENSE_TIMESTAMP_DESC, Timestamp.MESSAGE_CONSTRAINTS_GENERAL); // invalid timestamp

        // invalid price followed by valid description
        assertParseFailure(
                parser,
                "1" + INVALID_EXPENSE_PRICE_DESC + VALID_EXPENSE_DESCRIPTION_CHICKEN, Price.MESSAGE_CONSTRAINTS);

        // valid price followed by invalid price. The test case for invalid price followed by valid price
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser,
                "1" + EXPENSE_PRICE_DESC_TAXI + INVALID_EXPENSE_PRICE_DESC, Price.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(
                parser,
                "1" + INVALID_EXPENSE_DESCRIPTION_DESC + INVALID_EXPENSE_PRICE_DESC
                        + INVALID_EXPENSE_DESCRIPTION_DESC + VALID_EXPENSE_PRICE_CHICKEN,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + EXPENSE_PRICE_DESC_TAXI
                + EXPENSE_DESCRIPTION_DESC_CHICKEN + EXPENSE_CATEGORY_DESC_CHICKEN + EXPENSE_TIMESTAMP_DESC_CHICKEN;

        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withDescription(VALID_EXPENSE_DESCRIPTION_CHICKEN)
                .withPrice(VALID_EXPENSE_PRICE_TAXI)
                .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN)
                .withTimestamp(VALID_EXPENSE_TIMESTAMP_CHICKEN).build();

        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + EXPENSE_PRICE_DESC_TAXI;

        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withPrice(VALID_EXPENSE_PRICE_TAXI).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + EXPENSE_DESCRIPTION_DESC_CHICKEN;
        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withDescription(VALID_EXPENSE_DESCRIPTION_CHICKEN).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + EXPENSE_PRICE_DESC_CHICKEN;
        descriptor = new EditExpenseDescriptorBuilder().withPrice(VALID_EXPENSE_PRICE_CHICKEN).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + EXPENSE_CATEGORY_DESC_CHICKEN;
        descriptor = new EditExpenseDescriptorBuilder().withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // timestamp
        userInput = targetIndex.getOneBased() + EXPENSE_TIMESTAMP_DESC_CHICKEN;
        descriptor = new EditExpenseDescriptorBuilder().withTimestamp(VALID_EXPENSE_TIMESTAMP_CHICKEN).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_EXPENSE_PRICE_DESC + EXPENSE_PRICE_DESC_TAXI;
        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withPrice(VALID_EXPENSE_PRICE_TAXI).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_EXPENSE_PRICE_DESC + EXPENSE_PRICE_DESC_TAXI;
        descriptor = new EditExpenseDescriptorBuilder().withPrice(VALID_EXPENSE_PRICE_TAXI).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
