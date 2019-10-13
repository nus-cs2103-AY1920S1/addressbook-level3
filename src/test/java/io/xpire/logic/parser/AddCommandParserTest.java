package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static io.xpire.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_NAME;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_QUANTITY;
import static io.xpire.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_QUANTITY_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static io.xpire.testutil.TypicalItems.APPLE;

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
        Item expectedItem = new ItemBuilder(APPLE).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_NAME_APPLE
                + "|" + VALID_EXPIRY_DATE_APPLE + "|" + VALID_QUANTITY_APPLE,
                new AddCommand(expectedItem));

        CommandParserTestUtil.assertParseSuccess(parser, VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE
                + "|" + VALID_QUANTITY_APPLE, new AddCommand(expectedItem));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no quantity specified
        Item expectedItem = new ItemBuilder(APPLE).withQuantity("1").build();
        String userInput = VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE + "|";
        CommandParserTestUtil.assertParseSuccess(parser, userInput,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing bars
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_APPLE
                + "|" + VALID_QUANTITY_APPLE + "|" + VALID_TAG_FRUIT, Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + "|" + INVALID_EXPIRY_DATE
                + "|" + VALID_QUANTITY_APPLE, ExpiryDate.MESSAGE_CONSTRAINTS);

        // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE
                + "|" + INVALID_QUANTITY, Quantity.MESSAGE_CONSTRAINTS);
    }
}
