package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_KIWI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GREEN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalItems.APPLE;
import static seedu.address.testutil.TypicalItems.KIWI;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.item.ExpiryDate;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ItemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(APPLE).withTags(VALID_TAG_FRUIT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_NAME_APPLE
                + "|" + VALID_EXPIRY_DATE_APPLE + "|" + VALID_TAG_FRUIT, new AddCommand(expectedItem));

        assertParseSuccess(parser, VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE + "|"
                + VALID_TAG_FRUIT, new AddCommand(expectedItem));

        // multiple tags - all accepted
        Item expectedItemMultipleTags = new ItemBuilder(KIWI).withTags(VALID_TAG_FRUIT, VALID_TAG_GREEN)
                                                              .build();
        assertParseSuccess(parser, VALID_NAME_KIWI + "|" + VALID_EXPIRY_DATE_KIWI
                + "|#" + VALID_TAG_FRUIT + "#" + VALID_TAG_GREEN, new AddCommand(expectedItemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Item expectedItem = new ItemBuilder(KIWI).withTags().build();
        String userInput = VALID_NAME_KIWI + "|" + VALID_EXPIRY_DATE_KIWI;
        assertParseSuccess(parser, userInput,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

        // missing expiry date prefix
        assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_APPLE
                + "|" + VALID_TAG_FRUIT, Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        assertParseFailure(parser, VALID_NAME_APPLE + "|" + INVALID_EXPIRY_DATE
                + "|" + VALID_TAG_FRUIT, ExpiryDate.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE
                + "|" + INVALID_TAG + VALID_TAG_FRUIT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME + "|" + VALID_EXPIRY_DATE_APPLE
                + "|" + INVALID_TAG, Name.MESSAGE_CONSTRAINTS);
    }
}
