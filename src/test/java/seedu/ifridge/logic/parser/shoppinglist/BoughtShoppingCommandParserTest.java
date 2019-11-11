package seedu.ifridge.logic.parser.shoppinglist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_ORANGES;
import static seedu.ifridge.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.BoughtShoppingCommand.MESSAGE_NOT_PROPER;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.AMOUNT_DESC_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.VALID_AMOUNT_ORANGES;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.ifridge.testutil.TypicalShoppingList.ORANGE;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.shoppinglist.BoughtShoppingCommand;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.testutil.ShoppingItemBuilder;

public class BoughtShoppingCommandParserTest {
    private BoughtShoppingCommandParser parser = new BoughtShoppingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ShoppingItem expectedShoppingItem = new ShoppingItemBuilder(ORANGE).build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INDEX_FIRST_FOOD.getOneBased()
                + EXPIRY_DATE_DESC_ORANGES + AMOUNT_DESC_ORANGES, new BoughtShoppingCommand(INDEX_FIRST_FOOD,
                new Amount(VALID_AMOUNT_ORANGES), new ExpiryDate(VALID_EXPIRY_DATE_ORANGES)));

        // multiple expiry dates - last expiry date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INDEX_FIRST_FOOD.getOneBased() + EXPIRY_DATE_DESC_NUTS
                + EXPIRY_DATE_DESC_ORANGES + AMOUNT_DESC_ORANGES, new BoughtShoppingCommand(INDEX_FIRST_FOOD,
                new Amount(VALID_AMOUNT_ORANGES), new ExpiryDate(VALID_EXPIRY_DATE_ORANGES)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = MESSAGE_NOT_PROPER;
        // missing expiry date prefix
        assertParseFailure(parser, INDEX_FIRST_FOOD.getOneBased() + AMOUNT_DESC_ORANGES + VALID_EXPIRY_DATE_ORANGES,
                expectedMessage);

        //missing amount prefix
        assertParseFailure(parser, INDEX_FIRST_FOOD.getOneBased() + EXPIRY_DATE_DESC_ORANGES + VALID_AMOUNT_ORANGES,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, INDEX_SECOND_FOOD.getOneBased() + VALID_AMOUNT_ORANGES + VALID_EXPIRY_DATE_ORANGES,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid expiry date
        assertParseFailure(parser, INDEX_FIRST_FOOD.getOneBased() + INVALID_EXPIRY_DATE_DESC + AMOUNT_DESC_ORANGES,
                ExpiryDate.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INDEX_FIRST_FOOD.getOneBased() + INVALID_EXPIRY_DATE_DESC + INVALID_AMOUNT_DESC,
                ExpiryDate.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EXPIRY_DATE_DESC_ORANGES + AMOUNT_DESC_ORANGES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtShoppingCommand.MESSAGE_USAGE));
    }
}
