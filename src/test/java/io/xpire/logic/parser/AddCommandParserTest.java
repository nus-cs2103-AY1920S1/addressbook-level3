package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static io.xpire.logic.CommandParserItemUtil.INVALID_EXPIRY_DATE;
import static io.xpire.logic.CommandParserItemUtil.INVALID_NAME;
import static io.xpire.logic.CommandParserItemUtil.INVALID_QUANTITY;
import static io.xpire.logic.CommandParserItemUtil.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.logic.CommandParserItemUtil.VALID_NAME_APPLE;

import static io.xpire.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_QUANTITY_BANANA;

import static io.xpire.testutil.TypicalItems.BANANA;
import static io.xpire.testutil.TypicalItems.KIWI;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.AddCommand;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.testutil.ItemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(BANANA).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_NAME_BANANA
                + " | " + VALID_EXPIRY_DATE_BANANA + " |" + VALID_QUANTITY_BANANA,
                new AddCommand(expectedItem));

        //no whitespace preamble
        CommandParserTestUtil.assertParseSuccess(parser, VALID_NAME_BANANA
                        + "|" + VALID_EXPIRY_DATE_BANANA + "|" + VALID_QUANTITY_BANANA,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no quantity specified
        Item expectedItem = new ItemBuilder(KIWI).withQuantity("1").withReminderThreshold("0").build();
        String userInput = VALID_NAME_KIWI + "|" + VALID_EXPIRY_DATE_KIWI + "|";
        CommandParserTestUtil.assertParseSuccess(parser, userInput,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_invalidInput_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing bars
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

        /*// trailing bars
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + "|||||"
                        + VALID_EXPIRY_DATE_APPLE + "||||||" + VALID_QUANTITY_APPLE, expectedMessage);

        // invalid separator
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + "&"
                + VALID_EXPIRY_DATE_APPLE + "&" + VALID_QUANTITY_APPLE, expectedMessage);*/

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_BANANA
            + "|", Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BANANA + "|" + INVALID_EXPIRY_DATE
            + "|" , ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BANANA + "|" + VALID_EXPIRY_DATE_BANANA
                + "|" + INVALID_QUANTITY, Quantity.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_BANANA
            + "|", Name.MESSAGE_CONSTRAINTS);
    }
}
