package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_OVERFLOW_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_ZERO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ALICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTransactions.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.OutCommand;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Description;
import seedu.address.model.util.Date;
import seedu.address.testutil.TransactionBuilder;

public class OutCommandParserTest {
    private OutCommandParser parser = new OutCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // original amount made negative to account for Amount.makeNegative() in OutTransaction constructor
        BankAccountOperation expectedTransaction = new TransactionBuilder(ALICE)
                .withAmount("-100").build();
        BankAccountOperation expectedTransactionCategories = new TransactionBuilder(ALICE)
                .withAmount("-100").withCategories("food", "friends").build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AMOUNT_DESC_ALICE + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, new OutCommand(expectedTransaction));

        // multiple categories
        assertParseSuccess(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE
                        + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE + " c/friends",
                new OutCommand(expectedTransactionCategories));

        assertParseSuccess(parser, " n/milk c/food $/100 d/10112019", new OutCommand(expectedTransaction));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        BankAccountOperation expectedTransaction2 = new TransactionBuilder(ALICE)
                .withAmount("-100").withCategories("GENERAL").build();
        assertParseSuccess(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE, new OutCommand(expectedTransaction2));
    }

    @Test void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, OutCommand.MESSAGE_USAGE);

        // missing amount prefix
        assertParseFailure(parser, " " + VALID_AMOUNT_ALICE + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + VALID_DATE_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE
                + VALID_DESCRIPTION_ALICE + CATEGORY_DESC_ALICE, expectedMessage);

        // all prefix missing
        assertParseFailure(parser, " " + VALID_AMOUNT_ALICE + VALID_DATE_ALICE
                + VALID_DESCRIPTION_ALICE + VALID_CATEGORY_ALICE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid amount (zero)
        assertParseFailure(parser, " " + INVALID_AMOUNT_ZERO_DESC + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, Messages.MESSAGE_AMOUNT_ZERO);

        // invalid amount (range)
        assertParseFailure(parser, " " + INVALID_AMOUNT_RANGE_DESC + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, Messages.MESSAGE_AMOUNT_OVERFLOW);

        // invalid amount (double)
        assertParseFailure(parser, " " + INVALID_AMOUNT_TYPE_DESC + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, Amount.DOUBLE_CONSTRAINTS);

        // invalid amount (overflow)
        assertParseFailure(parser, " " + INVALID_AMOUNT_OVERFLOW_DESC + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, Messages.MESSAGE_AMOUNT_OVERFLOW);

        // invalid date (format)
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + INVALID_DATE_DESC
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE, Date.MESSAGE_FORMAT_CONSTRAINTS);

        // invalid date (type)
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + INVALID_DATETYPE_DESC
                + DESCRIPTION_DESC_ALICE + CATEGORY_DESC_ALICE,
                String.format(Date.MESSAGE_DATE_INVALID, INVALID_DATETYPE));

        // invalid description
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE
                + INVALID_DESCRIPTION_DESC + CATEGORY_DESC_ALICE, Description.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE
                + DESCRIPTION_DESC_ALICE + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);

    }
}
