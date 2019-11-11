package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.commands.CommandTestUtil.AMOUNT_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.AMOUNT_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.CATEGORY_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.CATEGORY_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.DAY_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.DAY_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_MONTH_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_TRANSACTION_TYPE_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.MONTH_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.MONTH_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.YEAR_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.YEAR_DESC_BUS;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE);

    private EditTransactionCommandParser parser = new EditTransactionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION_ALLOWANCE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTransactionCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DESCRIPTION_DESC_ALLOWANCE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TRANSACTION_TYPE_DESC_ALLOWANCE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_DAY_DESC, Day.MESSAGE_CONSTRAINTS); // invalid day
        assertParseFailure(parser, "1" + INVALID_MONTH_DESC, Month.MESSAGE_CONSTRAINTS); // invalid month
        assertParseFailure(parser, "1" + INVALID_YEAR_DESC, Year.MESSAGE_CONSTRAINTS); // invalid year
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC,
                Category.MESSAGE_CONSTRAINTS); // invalid category
        assertParseFailure(parser, "1" + INVALID_TRANSACTION_TYPE_DESC,
                TransactionType.MESSAGE_CONSTRAINTS); // invalid transaction type

        // invalid amount followed by valid day
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC + DAY_DESC_ALLOWANCE,
                Amount.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount.
        assertParseFailure(parser, "1" + AMOUNT_DESC_BUS + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + INVALID_DAY_DESC + VALID_MONTH_ALLOWANCE
                        + INVALID_TRANSACTION_TYPE_DESC + VALID_AMOUNT_ALLOWANCE, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_BUS + YEAR_DESC_ALLOWANCE
                + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + DESCRIPTION_DESC_ALLOWANCE + CATEGORY_DESC_BUS
                + TRANSACTION_TYPE_DESC_ALLOWANCE;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_ALLOWANCE).withAmount(VALID_AMOUNT_BUS)
                .withDay(VALID_DAY_ALLOWANCE).withMonth(VALID_MONTH_ALLOWANCE).withYear(VALID_YEAR_ALLOWANCE)
                .withCategory(VALID_CATEGORY_BUS).withTransactionType(VALID_TRANSACTION_TYPE_ALLOWANCE).build();
        EditTransactionCommand expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_BUS
                + DAY_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BUS)
                .withDay(VALID_DAY_ALLOWANCE).withCategory(VALID_CATEGORY_ALLOWANCE).build();
        EditTransactionCommand expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_ALLOWANCE;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_ALLOWANCE).build();
        EditTransactionCommand expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_ALLOWANCE;
        descriptor = new EditTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_ALLOWANCE).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // day
        userInput = targetIndex.getOneBased() + DAY_DESC_ALLOWANCE;
        descriptor = new EditTransactionDescriptorBuilder().withDay(VALID_DAY_ALLOWANCE).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // month
        userInput = targetIndex.getOneBased() + MONTH_DESC_ALLOWANCE;
        descriptor = new EditTransactionDescriptorBuilder().withMonth(VALID_MONTH_ALLOWANCE).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // year
        userInput = targetIndex.getOneBased() + YEAR_DESC_BUS;
        descriptor = new EditTransactionDescriptorBuilder().withYear(VALID_YEAR_BUS).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // category
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_BUS;
        descriptor = new EditTransactionDescriptorBuilder().withCategory(VALID_CATEGORY_BUS).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // transaction type
        userInput = targetIndex.getOneBased() + TRANSACTION_TYPE_DESC_BUS;
        descriptor = new EditTransactionDescriptorBuilder().withTransactionType(VALID_TRANSACTION_TYPE_BUS).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE
                + MONTH_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE + CATEGORY_DESC_BUS + TRANSACTION_TYPE_DESC_BUS
                + YEAR_DESC_BUS + AMOUNT_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE + YEAR_DESC_BUS
                + AMOUNT_DESC_BUS + MONTH_DESC_BUS + DAY_DESC_BUS + YEAR_DESC_ALLOWANCE + DESCRIPTION_DESC_BUS
                + CATEGORY_DESC_ALLOWANCE + TRANSACTION_TYPE_DESC_ALLOWANCE;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BUS).withAmount(VALID_AMOUNT_BUS)
                .withDay(VALID_DAY_BUS).withMonth(VALID_MONTH_BUS).withYear(VALID_YEAR_ALLOWANCE)
                .withCategory(VALID_CATEGORY_ALLOWANCE).withTransactionType(VALID_TRANSACTION_TYPE_ALLOWANCE)
                .build();
        EditTransactionCommand expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_AMOUNT_DESC + AMOUNT_DESC_BUS;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(VALID_AMOUNT_BUS).build();
        EditTransactionCommand expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DAY_DESC_BUS + INVALID_AMOUNT_DESC + MONTH_DESC_BUS
                + AMOUNT_DESC_BUS;
        descriptor = new EditTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_BUS).withDay(VALID_DAY_BUS)
                .withMonth(VALID_MONTH_BUS).build();
        expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }
}
