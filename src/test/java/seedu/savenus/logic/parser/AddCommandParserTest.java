package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.savenus.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_RICE;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.savenus.testutil.TypicalFood.CHICKEN_RICE;
import static seedu.savenus.testutil.TypicalFood.NASI_LEMAK;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.testutil.FoodBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Food expectedFood = new FoodBuilder(NASI_LEMAK).withTags(VALID_TAG_CHICKEN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_NASI_LEMAK
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_CHICKEN
                + LOCATION_DESC_NASI_LEMAK + OPENING_HOURS_DESC_NASI_LEMAK
                + RESTRICTIONS_DESC_NASI_LEMAK, new AddCommand(expectedFood));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_CHICKEN_RICE + NAME_DESC_NASI_LEMAK
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_CHICKEN
                + LOCATION_DESC_NASI_LEMAK + OPENING_HOURS_DESC_NASI_LEMAK
                + RESTRICTIONS_DESC_NASI_LEMAK, new AddCommand(expectedFood));

        // multiple prices - last price accepted
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_CHICKEN_RICE
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_CHICKEN
                + LOCATION_DESC_NASI_LEMAK + OPENING_HOURS_DESC_NASI_LEMAK
                + RESTRICTIONS_DESC_NASI_LEMAK, new AddCommand(expectedFood));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK
                + DESCRIPTION_DESC_CHICKEN_RICE + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_CHICKEN
                + LOCATION_DESC_NASI_LEMAK + OPENING_HOURS_DESC_NASI_LEMAK
                + RESTRICTIONS_DESC_NASI_LEMAK, new AddCommand(expectedFood));

        // multiple locations - last location accepted
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK
                + DESCRIPTION_DESC_NASI_LEMAK + CATEGORY_DESC_NASI_LEMAK
                + TAG_DESC_CHICKEN
                + LOCATION_DESC_CHICKEN_RICE + LOCATION_DESC_NASI_LEMAK + OPENING_HOURS_DESC_NASI_LEMAK
                + RESTRICTIONS_DESC_NASI_LEMAK, new AddCommand(expectedFood));

        // multiple tags - all accepted
        Food expectedFoodMultipleTags = new FoodBuilder(NASI_LEMAK).withTags(VALID_TAG_CHICKEN, VALID_TAG_RICE)
                .build();
        assertParseSuccess(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_RICE + TAG_DESC_CHICKEN
                + LOCATION_DESC_NASI_LEMAK + OPENING_HOURS_DESC_NASI_LEMAK
                + RESTRICTIONS_DESC_NASI_LEMAK, new AddCommand(expectedFoodMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Food expectedFood = new FoodBuilder(CHICKEN_RICE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_CHICKEN_RICE + PRICE_DESC_CHICKEN_RICE + DESCRIPTION_DESC_CHICKEN_RICE
                        + CATEGORY_DESC_CHICKEN_RICE + LOCATION_DESC_CHICKEN_RICE
                        + OPENING_HOURS_DESC_CHICKEN_RICE + RESTRICTIONS_DESC_CHICKEN_RICE,
                new AddCommand(expectedFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_NASI_LEMAK + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                        + CATEGORY_DESC_NASI_LEMAK,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + VALID_PRICE_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                        + CATEGORY_DESC_NASI_LEMAK,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_NASI_LEMAK + VALID_PRICE_NASI_LEMAK + VALID_DESCRIPTION_NASI_LEMAK
                        + CATEGORY_DESC_NASI_LEMAK,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_RICE
                + LOCATION_DESC_CHICKEN_RICE + TAG_DESC_CHICKEN, Name.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + INVALID_PRICE_DESC
                + DESCRIPTION_DESC_NASI_LEMAK + CATEGORY_DESC_NASI_LEMAK
                + TAG_DESC_RICE + LOCATION_DESC_CHICKEN_RICE
                + TAG_DESC_CHICKEN, Price.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK + PRICE_DESC_NASI_LEMAK
                + INVALID_DESCRIPTION_DESC + CATEGORY_DESC_NASI_LEMAK
                + TAG_DESC_RICE + LOCATION_DESC_CHICKEN_RICE
                + TAG_DESC_CHICKEN, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + INVALID_TAG_DESC
                + LOCATION_DESC_CHICKEN_RICE + VALID_TAG_CHICKEN, Tag.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, NAME_DESC_NASI_LEMAK
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_CHICKEN
                + TAG_DESC_RICE + INVALID_LOCATION_DESC
                + OPENING_HOURS_DESC_NASI_LEMAK + RESTRICTIONS_DESC_NASI_LEMAK, Location.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_NASI_LEMAK
                        + DESCRIPTION_DESC_NASI_LEMAK
                        + CATEGORY_DESC_NASI_LEMAK,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_NASI_LEMAK
                        + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK
                        + CATEGORY_DESC_NASI_LEMAK
                        + TAG_DESC_RICE + TAG_DESC_CHICKEN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
