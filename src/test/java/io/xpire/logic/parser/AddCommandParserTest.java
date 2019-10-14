package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_NAME;
//import static io.xpire.logic.commands.CommandTestUtil.INVALID_TAG;
import static io.xpire.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
//import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
//import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_KIWI;
import static io.xpire.testutil.TypicalItems.APPLE;
//import static io.xpire.testutil.TypicalItems.KIWI;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.AddCommand;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.testutil.ItemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(APPLE).build();

        // whitespace only preamble
        CommandParserTestUtil.assertEqualsParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_NAME_APPLE
                + "|" + VALID_EXPIRY_DATE_APPLE, new AddCommand(expectedItem));

        CommandParserTestUtil.assertEqualsParseSuccess(parser, VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE,
                new AddCommand(expectedItem));

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

        // missing expiry date prefix
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_APPLE
                + "|", Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + "|" + INVALID_EXPIRY_DATE
                + "|" , ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);


        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_APPLE
                + "|", Name.MESSAGE_CONSTRAINTS);
    }
}
