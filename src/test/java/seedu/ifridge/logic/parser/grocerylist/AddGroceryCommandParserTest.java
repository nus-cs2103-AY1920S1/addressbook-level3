package seedu.ifridge.logic.parser.grocerylist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.AMOUNT_DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.EXPIRY_DATE_DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.INVALID_NAME_DESC;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.INVALID_TAG_DESC;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.NAME_DESC_BANANA;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.NAME_DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.TAG_DESC_BANANA;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.TAG_DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.TAG_DESC_ORANGES;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_EXPIRY_DATE_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_TAG_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_TAG_ORANGES;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.grocerylist.AddGroceryCommand;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.tag.Tag;
import seedu.ifridge.testutil.GroceryItemBuilder;

public class AddGroceryCommandParserTest {
    private AddGroceryCommandParser parser = new AddGroceryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        GroceryItem expectedFood = new GroceryItemBuilder().withName(VALID_NAME_NUTS).withAmount(VALID_AMOUNT_NUTS)
                .withExpiryDate(VALID_EXPIRY_DATE_NUTS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_NUTS
                + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS, new AddGroceryCommand(expectedFood));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BANANA + NAME_DESC_NUTS
                + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS, new AddGroceryCommand(expectedFood));

        // multiple tags - all accepted
        GroceryItem expectedFoodMultipleTags = new GroceryItemBuilder().withName(VALID_NAME_NUTS)
                .withAmount(VALID_AMOUNT_NUTS).withExpiryDate(VALID_EXPIRY_DATE_NUTS)
                .withTags(VALID_TAG_NUTS, VALID_TAG_ORANGES).build();

        assertParseSuccess(parser, NAME_DESC_NUTS + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS
                + TAG_DESC_NUTS + TAG_DESC_ORANGES, new AddGroceryCommand(expectedFoodMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        GroceryItem expectedFood = new GroceryItemBuilder().withName(VALID_NAME_NUTS).withAmount(VALID_AMOUNT_NUTS)
                .withExpiryDate(VALID_EXPIRY_DATE_NUTS).build();
        assertParseSuccess(parser, NAME_DESC_NUTS + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS,
                new AddGroceryCommand(expectedFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroceryCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_NUTS + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_NUTS + VALID_AMOUNT_NUTS + VALID_EXPIRY_DATE_NUTS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS
                + TAG_DESC_NUTS + TAG_DESC_BANANA, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_NUTS + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS
                + INVALID_TAG_DESC + VALID_TAG_NUTS, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_NUTS + EXPIRY_DATE_DESC_NUTS
                + INVALID_TAG_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_NUTS + AMOUNT_DESC_NUTS
                + EXPIRY_DATE_DESC_NUTS + TAG_DESC_NUTS + TAG_DESC_BANANA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroceryCommand.MESSAGE_USAGE));
    }
}
