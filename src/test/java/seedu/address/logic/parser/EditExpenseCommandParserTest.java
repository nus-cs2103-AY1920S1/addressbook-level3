package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.CURRENCY_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.CURRENCY_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CURRENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ALCOHOL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENCY_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENCY_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALCOHOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRINKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExpenseCommand;
import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.commons.Amount;
import seedu.address.model.commons.Currency;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.Tag;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE);

    private EditExpenseCommandParser parser = new EditExpenseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_VODKA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditExpenseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_VODKA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_VODKA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_CURRENCY_DESC, Currency.MESSAGE_CONSTRAINTS); // invalid currency
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid amount followed by valid date
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC + DATE_DESC_VODKA, Amount.MESSAGE_CONSTRAINTS);

        // valid amount followed by invalid amount. The test case for invalid amount followed by valid amount
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_RUM + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Expense} being edited,
        // parsing it together with a valid tag results in error
        // NOTE: Above is no longer the case as an expense has only 1 tag and the last tag specified prevails.
        //assertParseFailure(parser, "1" + TAG_DESC_DRINKS + TAG_DESC_ALCOHOL + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        //assertParseFailure(parser, "1" + TAG_DESC_DRINKS + TAG_EMPTY + TAG_DESC_ALCOHOL, Tag.MESSAGE_CONSTRAINTS);
        //assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_DRINKS + TAG_DESC_ALCOHOL, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_AMOUNT_VODKA,
            Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_RUM + CURRENCY_DESC_VODKA + TAG_DESC_ALCOHOL
            + DATE_DESC_VODKA + NAME_DESC_VODKA;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_VODKA)
            .withCurrency(VALID_CURRENCY_VODKA).withAmount(VALID_AMOUNT_RUM).withDate(VALID_DATE_VODKA)
            .withTag(VALID_TAG_ALCOHOL).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_RUM + DATE_DESC_VODKA;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
            .withAmount(VALID_AMOUNT_RUM).withDate(VALID_DATE_VODKA).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_VODKA;
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(VALID_NAME_VODKA).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_VODKA;
        descriptor = new EditExpenseDescriptorBuilder().withAmount(VALID_AMOUNT_VODKA).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // currency
        userInput = targetIndex.getOneBased() + CURRENCY_DESC_VODKA;
        descriptor = new EditExpenseDescriptorBuilder().withCurrency(VALID_CURRENCY_VODKA).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_VODKA;
        descriptor = new EditExpenseDescriptorBuilder().withDate(VALID_DATE_VODKA).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_DRINKS;
        descriptor = new EditExpenseDescriptorBuilder().withTag(VALID_TAG_DRINKS).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_RUM + DATE_DESC_VODKA
            + TAG_DESC_ALCOHOL + AMOUNT_DESC_VODKA + DATE_DESC_VODKA + TAG_DESC_DRINKS
            + AMOUNT_DESC_RUM + DATE_DESC_RUM + CURRENCY_DESC_RUM;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withCurrency(VALID_CURRENCY_RUM)
            .withAmount(VALID_AMOUNT_RUM).withDate(VALID_DATE_RUM)
            .withTag(VALID_TAG_DRINKS).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + INVALID_AMOUNT_DESC + AMOUNT_DESC_RUM;
        EditExpenseDescriptor descriptor =
            new EditExpenseDescriptorBuilder().withAmount(VALID_AMOUNT_RUM).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput =
            targetIndex.getOneBased() + DATE_DESC_RUM + INVALID_AMOUNT_DESC + CURRENCY_DESC_VODKA + AMOUNT_DESC_RUM;
        descriptor = new EditExpenseDescriptorBuilder().withCurrency(VALID_CURRENCY_VODKA)
            .withAmount(VALID_AMOUNT_RUM).withDate(VALID_DATE_RUM).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withTag("").build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
