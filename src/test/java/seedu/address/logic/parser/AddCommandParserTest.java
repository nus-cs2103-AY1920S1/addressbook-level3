package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_KIWI;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_KIWI;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRUIT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
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
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_APPLE
                + "|" + EXPIRY_DATE_DESC_APPLE + "|" + TAG_DESC_FRUIT, new AddCommand(expectedItem));

        assertParseSuccess(parser, NAME_DESC_APPLE + "|" + EXPIRY_DATE_DESC_APPLE + "|"
                + TAG_DESC_FRUIT, new AddCommand(expectedItem));
        System.out.println(NAME_DESC_APPLE + "|" + EXPIRY_DATE_DESC_APPLE + "|"
                + TAG_DESC_FRUIT);

        // multiple tags - all accepted
        Item expectedItemMultipleTags = new ItemBuilder(KIWI).withTags(VALID_TAG_FRUIT, VALID_TAG_GREEN)
                                                              .build();
        assertParseSuccess(parser, NAME_DESC_KIWI + "|" + EXPIRY_DATE_DESC_KIWI
                + "|" + TAG_DESC_FRUIT + "|" + TAG_DESC_GREEN, new AddCommand(expectedItemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Item expectedItem = new ItemBuilder(KIWI).withTags().build();
        String userInput = NAME_DESC_KIWI + "|" + EXPIRY_DATE_DESC_KIWI;
        System.out.println(userInput);
        assertParseSuccess(parser, userInput,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_APPLE + EXPIRY_DATE_DESC_APPLE,
                expectedMessage);

        // missing expiry date prefix
        assertParseFailure(parser, NAME_DESC_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_APPLE + VALID_EXPIRY_DATE_APPLE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + "|" + EXPIRY_DATE_DESC_APPLE
                + "|" + TAG_DESC_FRUIT, Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        assertParseFailure(parser, NAME_DESC_APPLE + "|" + INVALID_EXPIRY_DATE_DESC
                + "|" + TAG_DESC_FRUIT, ExpiryDate.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_APPLE + "|" + EXPIRY_DATE_DESC_APPLE
                + "|" + INVALID_TAG_DESC + VALID_TAG_FRUIT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + "|" + EXPIRY_DATE_DESC_APPLE
                + "|" + INVALID_TAG_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}
