package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_LOCATION_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_LOCATION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_RICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertRemovePreferenceParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertRemovePreferenceParseSuccess;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.RemoveDislikeCommand;
import seedu.savenus.logic.commands.RemoveLikeCommand;
import seedu.savenus.logic.commands.RemovePreferenceCommand;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

public class RemovePrefCommandParserTest {
    private RemovePreferenceCommandParser parser = new RemovePreferenceCommandParser();

    private HashSet<Category> expectedCategory = new HashSet<>();
    private HashSet<Tag> expectedTags = new HashSet<>();
    private HashSet<Location> expectedLocation = new HashSet<>();

    @BeforeEach
    public void setUp() {
        expectedCategory.add(new Category(VALID_CATEGORY_CHICKEN_RICE));
        expectedCategory.add(new Category(VALID_CATEGORY_NASI_LEMAK));

        expectedTags.add(new Tag(VALID_TAG_CHICKEN));
        expectedTags.add(new Tag(VALID_TAG_RICE));

        expectedLocation.add(new Location(VALID_LOCATION_CHICKEN_RICE));
        expectedLocation.add(new Location(VALID_LOCATION_NASI_LEMAK));
    }

    @Test
    public void parse_nothingProvided_success() {
        assertRemovePreferenceParseSuccess(parser, PREAMBLE_WHITESPACE, new RemoveDislikeCommand(true), false);
        assertRemovePreferenceParseSuccess(parser, PREAMBLE_WHITESPACE, new RemoveLikeCommand(true), true);
    }

    @Test
    public void parse_invalidField_failure() {
        String command = NAME_DESC_CHICKEN_RICE + PRICE_DESC_CHICKEN_RICE;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePreferenceCommand.MESSAGE_USAGE);

        assertRemovePreferenceParseFailure(parser, command, expectedMessage, false);
        assertRemovePreferenceParseFailure(parser, command, expectedMessage, true);
    }

    @Test
    public void parse_invalidAndValidFields_failure() {
        String command = NAME_DESC_CHICKEN_RICE + PRICE_DESC_CHICKEN_RICE + CATEGORY_DESC_CHICKEN_RICE;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePreferenceCommand.MESSAGE_USAGE);

        assertRemovePreferenceParseFailure(parser, command, expectedMessage, false);
        assertRemovePreferenceParseFailure(parser, command, expectedMessage, true);
    }

    @Test
    public void parse_validCategory_success() {
        String command = CATEGORY_DESC_CHICKEN_RICE + CATEGORY_DESC_NASI_LEMAK;

        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveDislikeCommand(expectedCategory, new HashSet<>(), new HashSet<>(), false), false);
        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveLikeCommand(expectedCategory, new HashSet<>(), new HashSet<>(), false), true);
    }

    @Test
    public void parse_missingCategoryPrefix_failure() {
        String command = VALID_CATEGORY_CHICKEN_RICE + VALID_CATEGORY_NASI_LEMAK;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePreferenceCommand.MESSAGE_USAGE);

        assertRemovePreferenceParseFailure(parser, command, expectedMessage, false);
        assertRemovePreferenceParseFailure(parser, command, expectedMessage, true);
    }

    @Test
    public void parse_validTag_success() {
        String command = TAG_DESC_CHICKEN + TAG_DESC_RICE;

        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveDislikeCommand(new HashSet<>(), expectedTags, new HashSet<>(), false), false);
        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveLikeCommand(new HashSet<>(), expectedTags, new HashSet<>(), false), true);
    }

    @Test
    public void parse_missingTagPrefix_failure() {
        String command = VALID_TAG_CHICKEN + VALID_TAG_RICE;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePreferenceCommand.MESSAGE_USAGE);

        assertRemovePreferenceParseFailure(parser, command, expectedMessage, false);
        assertRemovePreferenceParseFailure(parser, command, expectedMessage, true);
    }

    @Test
    public void parse_validLocation_success() {
        String command = LOCATION_DESC_CHICKEN_RICE + LOCATION_DESC_NASI_LEMAK;

        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveDislikeCommand(new HashSet<>(), new HashSet<>(), expectedLocation, false), false);
        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveLikeCommand(new HashSet<>(), new HashSet<>(), expectedLocation, false), true);
    }

    @Test
    public void parse_missingLocationPrefix_failure() {
        String command = VALID_LOCATION_CHICKEN_RICE + VALID_LOCATION_NASI_LEMAK;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePreferenceCommand.MESSAGE_USAGE);

        assertRemovePreferenceParseFailure(parser, command, expectedMessage, false);
        assertRemovePreferenceParseFailure(parser, command, expectedMessage, true);
    }

    @Test
    public void parse_allFields_success() {
        String command = CATEGORY_DESC_CHICKEN_RICE + CATEGORY_DESC_NASI_LEMAK + TAG_DESC_CHICKEN + TAG_DESC_RICE
                + LOCATION_DESC_CHICKEN_RICE + LOCATION_DESC_NASI_LEMAK;

        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveDislikeCommand(expectedCategory, expectedTags, expectedLocation, false), false);
        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveLikeCommand(expectedCategory, expectedTags, expectedLocation, false), true);
    }

    @Test
    public void parse_multipleDuplicates_success() {
        String command = CATEGORY_DESC_CHICKEN_RICE + CATEGORY_DESC_NASI_LEMAK
                + CATEGORY_DESC_CHICKEN_RICE + CATEGORY_DESC_NASI_LEMAK
                + TAG_DESC_CHICKEN + TAG_DESC_RICE
                + TAG_DESC_CHICKEN + TAG_DESC_RICE
                + LOCATION_DESC_CHICKEN_RICE + LOCATION_DESC_NASI_LEMAK
                + LOCATION_DESC_CHICKEN_RICE + LOCATION_DESC_NASI_LEMAK;

        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveDislikeCommand(expectedCategory, expectedTags, expectedLocation, false), false);
        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveLikeCommand(expectedCategory, expectedTags, expectedLocation, false), true);
    }

    @Test
    public void parse_duplicatesInDifferentCase_success() {
        String command = CATEGORY_DESC_CHICKEN_RICE.toLowerCase() + CATEGORY_DESC_NASI_LEMAK.toLowerCase() + " "
                + PREFIX_CATEGORY + VALID_CATEGORY_CHICKEN_RICE.toUpperCase() + " "
                + PREFIX_CATEGORY + VALID_CATEGORY_NASI_LEMAK.toUpperCase()
                + TAG_DESC_CHICKEN.toLowerCase() + TAG_DESC_RICE.toLowerCase() + " "
                + PREFIX_TAG + VALID_TAG_CHICKEN.toUpperCase() + " "
                + PREFIX_TAG + VALID_TAG_RICE.toUpperCase()
                + LOCATION_DESC_CHICKEN_RICE.toLowerCase() + LOCATION_DESC_NASI_LEMAK.toLowerCase() + " "
                + PREFIX_LOCATION + VALID_LOCATION_CHICKEN_RICE.toUpperCase() + " "
                + PREFIX_LOCATION + VALID_LOCATION_NASI_LEMAK.toUpperCase();

        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveDislikeCommand(expectedCategory, expectedTags, expectedLocation, false), false);
        assertRemovePreferenceParseSuccess(parser, command,
                new RemoveLikeCommand(expectedCategory, expectedTags, expectedLocation, false), true);
    }

    @Test
    public void wrongMethodCalled_failure() {
        assertThrows(AssertionError.class, () -> parser.parse("Test"));
    }
}
