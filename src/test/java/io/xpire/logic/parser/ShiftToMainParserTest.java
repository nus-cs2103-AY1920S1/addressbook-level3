package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static io.xpire.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_QUANTITY_INTEGER;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_DUCK;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.ShiftToMainCommand;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Quantity;

public class ShiftToMainParserTest {

    private ShiftToMainCommandParser parser = new ShiftToMainCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CommandParserTestUtil.assertEqualsParseSuccess(parser, "3"
                        + "|" + VALID_EXPIRY_DATE_DUCK + "|" + VALID_QUANTITY_DUCK,
                new ShiftToMainCommand(INDEX_THIRD_ITEM, new ExpiryDate(VALID_EXPIRY_DATE_DUCK),
                        new Quantity(VALID_QUANTITY_DUCK)));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no quantity specified
        CommandParserTestUtil.assertEqualsParseSuccess(parser, "3|" + VALID_EXPIRY_DATE_DUCK,
                new ShiftToMainCommand(INDEX_THIRD_ITEM, new ExpiryDate(VALID_EXPIRY_DATE_DUCK),
                        new Quantity("1")));
    }

    @Test
    public void parse_invalidInput_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShiftToMainCommand.MESSAGE_USAGE);
        // missing bars
        CommandParserTestUtil.assertParseFailure(parser, "4" + VALID_EXPIRY_DATE_FISH,
                expectedMessage);
    }


    @Test
    public void parse_invalidValue_failure() {

        // invalid index
        CommandParserTestUtil.assertParseFailure(parser, "-3|" + VALID_EXPIRY_DATE_BANANA,
                MESSAGE_INVALID_INDEX);

        // invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, "5|" + INVALID_EXPIRY_DATE,
                ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);

        // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, "2|" + VALID_EXPIRY_DATE_BANANA
                + "|" + INVALID_QUANTITY_INTEGER, Quantity.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, "2|" + INVALID_EXPIRY_DATE
                + "|" + INVALID_QUANTITY_INTEGER, ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);
    }
}
