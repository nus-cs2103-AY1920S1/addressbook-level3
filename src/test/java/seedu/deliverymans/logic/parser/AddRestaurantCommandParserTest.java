package seedu.deliverymans.logic.parser;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.deliverymans.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.deliverymans.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.deliverymans.logic.commands.CommandTestUtil.LOCATION_DESC_TWO;
import static seedu.deliverymans.logic.commands.CommandTestUtil.NAME_DESC_TWO;
import static seedu.deliverymans.logic.commands.CommandTestUtil.TAG_DESC_ONE;
import static seedu.deliverymans.logic.commands.CommandTestUtil.TAG_DESC_TWO;
import static seedu.deliverymans.logic.commands.CommandTestUtil.VALID_LOCATION_TWO;
import static seedu.deliverymans.logic.commands.CommandTestUtil.VALID_NAME_TWO;
import static seedu.deliverymans.logic.commands.CommandTestUtil.VALID_TAG_ONE;
import static seedu.deliverymans.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.deliverymans.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.deliverymans.model.util.SampleDataUtil.getTagSet;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.logic.commands.restaurant.AddRestaurantCommand;
import seedu.deliverymans.logic.parser.restaurant.AddRestaurantCommandParser;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.restaurant.Restaurant;


public class AddRestaurantCommandParserTest {
    private AddRestaurantCommandParser parser = new AddRestaurantCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // normal - success
        Restaurant expectedRestaurant = new Restaurant(new Name("Name Two"),
                LocationMap.getLocation("Changi").get(),
                getTagSet("Tag1"));

        assertParseSuccess(parser, NAME_DESC_TWO + LOCATION_DESC_TWO
                + TAG_DESC_ONE, new AddRestaurantCommand(expectedRestaurant));

        // multiple tags - success
        Restaurant expectedRestaurantMultipleTags = new Restaurant(new Name("Name Two"),
                LocationMap.getLocation("Changi").get(),
                getTagSet("Tag2", "Tag1"));

        assertParseSuccess(parser, NAME_DESC_TWO + LOCATION_DESC_TWO
                + TAG_DESC_TWO + TAG_DESC_ONE, new AddRestaurantCommand(expectedRestaurantMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags - success
        Restaurant expectedRestaurant = new Restaurant(new Name("Name Two"),
                LocationMap.getLocation("Changi").get(), getTagSet());

        assertParseSuccess(parser, NAME_DESC_TWO + LOCATION_DESC_TWO,
                new AddRestaurantCommand(expectedRestaurant));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRestaurantCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_TWO + LOCATION_DESC_TWO, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, NAME_DESC_TWO + VALID_LOCATION_TWO, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_TWO + VALID_LOCATION_TWO, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + LOCATION_DESC_TWO
                + TAG_DESC_TWO + TAG_DESC_ONE, Name.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, NAME_DESC_TWO + INVALID_LOCATION_DESC
                + TAG_DESC_TWO + TAG_DESC_ONE, LocationMap.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_TWO + LOCATION_DESC_TWO
                + INVALID_TAG_DESC + VALID_TAG_ONE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_LOCATION_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}
