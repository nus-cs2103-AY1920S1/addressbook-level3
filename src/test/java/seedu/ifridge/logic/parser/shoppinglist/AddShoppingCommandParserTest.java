package seedu.ifridge.logic.parser.shoppinglist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.AMOUNT_DESC_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.DESC_NUTS;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.INVALID_NAME_DESC;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.NAME_DESC_NUTS;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.NAME_DESC_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.VALID_AMOUNT_ORANGES;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalShoppingList.ORANGE;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.shoppinglist.AddShoppingCommand;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.testutil.ShoppingItemBuilder;

public class AddShoppingCommandParserTest {
    private AddShoppingCommandParser parser = new AddShoppingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ShoppingItem expectedShoppingItem = new ShoppingItemBuilder(ORANGE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ORANGES + AMOUNT_DESC_ORANGES,
                new AddShoppingCommand(expectedShoppingItem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_NUTS + NAME_DESC_ORANGES + AMOUNT_DESC_ORANGES,
                new AddShoppingCommand(expectedShoppingItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddShoppingCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ORANGES + DESC_NUTS, expectedMessage);

        //missing amount prefix
        assertParseFailure(parser, NAME_DESC_ORANGES + VALID_AMOUNT_ORANGES, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ORANGES + VALID_AMOUNT_ORANGES, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_ORANGES, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_AMOUNT_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ORANGES
                + AMOUNT_DESC_ORANGES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddShoppingCommand.MESSAGE_USAGE));
    }
}
