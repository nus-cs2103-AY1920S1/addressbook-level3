package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_NAME;
import static io.xpire.testutil.TypicalItemsFields.INVALID_QUANTITY_INTEGER;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_BANANA;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.AddCommand;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CommandParserTestUtil.assertEqualsParseSuccess(parser, VALID_NAME_BANANA
                        + "|" + VALID_EXPIRY_DATE_BANANA + "|" + VALID_QUANTITY_BANANA,
                new AddCommand(new Name(VALID_NAME_BANANA), new ExpiryDate(VALID_EXPIRY_DATE_BANANA),
                        new Quantity(VALID_QUANTITY_BANANA)));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no quantity specified
        String userInput = VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE;
        CommandParserTestUtil.assertEqualsParseSuccess(parser, userInput,
                new AddCommand(new Name(VALID_NAME_APPLE), new ExpiryDate(VALID_EXPIRY_DATE_APPLE),
                        new Quantity("1")));
    }

    @Test
    public void parse_invalidInput_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        // missing bars
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_BANANA
            + "|", Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_APPLE + "|" + INVALID_EXPIRY_DATE,
                ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BANANA + "|" + VALID_EXPIRY_DATE_BANANA
                + "|" + INVALID_QUANTITY_INTEGER, Quantity.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME + "|" + INVALID_EXPIRY_DATE,
                Name.MESSAGE_CONSTRAINTS);
    }
}
