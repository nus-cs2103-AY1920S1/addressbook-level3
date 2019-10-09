package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.CommandTestUtil.*;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.savenus.testutil.TypicalFood.*;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.testutil.FoodBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Food expectedFood = new FoodBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_CHICKEN_RICE + NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple prices - last price accepted
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_CHICKEN_RICE + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_CHICKEN_RICE + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple tags - all accepted
        Food expectedFoodMultipleTags = new FoodBuilder(NASI_LEMAK).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedFoodMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Food expectedFood = new FoodBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_CHICKEN_RICE + PRICE_DESC_CHICKEN_RICE + DESCRIPTION_DESC_CHICKEN_RICE,
                new AddCommand(expectedFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + VALID_PRICE_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_NASI_LEMAK + VALID_PRICE_NASI_LEMAK + VALID_DESCRIPTION_NASI_LEMAK,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + INVALID_PRICE_DESC + DESCRIPTION_DESC_NASI_LEMAK
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Price.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + INVALID_DESCRIPTION_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
