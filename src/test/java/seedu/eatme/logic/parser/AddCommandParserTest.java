package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.eatme.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.eatme.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.eatme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.eatme.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.eatme.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_CATEGORY_WITH_PREFIX;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_WITH_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_WITH_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_WITH_PREFIX_CHEAP;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eatme.testutil.TypicalEateries.KENTUCKY;
import static seedu.eatme.testutil.TypicalEateries.MCDONALD;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.commands.AddCommand;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;
import seedu.eatme.testutil.EateryBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Eatery expectedEatery = new EateryBuilder(MCDONALD).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_NAME_WITH_PREFIX_MAC
                + VALID_ADDRESS_WITH_PREFIX_MAC + VALID_CATEGORY_WITH_PREFIX
                + VALID_TAG_WITH_PREFIX_CHEAP, new AddCommand(expectedEatery));

        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_NAME_WITH_PREFIX_KFC + VALID_NAME_WITH_PREFIX_MAC
                + VALID_ADDRESS_WITH_PREFIX_MAC + VALID_CATEGORY_WITH_PREFIX
                + VALID_TAG_WITH_PREFIX_CHEAP, new AddCommand(expectedEatery));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, VALID_NAME_WITH_PREFIX_MAC + VALID_ADDRESS_WITH_PREFIX_KFC
                + VALID_ADDRESS_WITH_PREFIX_MAC + VALID_CATEGORY_WITH_PREFIX
                + VALID_TAG_WITH_PREFIX_CHEAP, new AddCommand(expectedEatery));

        // multiple tags - all accepted
        Eatery expectedEateryMultipleTags = new EateryBuilder(MCDONALD).withTags(VALID_TAG_NO_PREFIX_CHEAP,
                VALID_TAG_NO_PREFIX_NICE).build();

        assertParseSuccess(parser, VALID_NAME_WITH_PREFIX_MAC + VALID_ADDRESS_WITH_PREFIX_MAC
                + VALID_CATEGORY_WITH_PREFIX
                + VALID_TAG_WITH_PREFIX_CHEAP + VALID_TAG_WITH_PREFIX_NICE, new AddCommand(expectedEateryMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Eatery expectedEatery = new EateryBuilder(KENTUCKY).withTags().build();
        assertParseSuccess(parser, VALID_NAME_WITH_PREFIX_KFC + VALID_ADDRESS_WITH_PREFIX_KFC
                + VALID_CATEGORY_WITH_PREFIX,
                new AddCommand(expectedEatery));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_MAIN);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_NO_PREFIX_KFC + VALID_ADDRESS_WITH_PREFIX_KFC,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, VALID_NAME_WITH_PREFIX_KFC + VALID_ADDRESS_NO_PREFIX_KFC,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_NO_PREFIX_KFC + VALID_ADDRESS_NO_PREFIX_KFC,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_ADDRESS_WITH_PREFIX_KFC + VALID_CATEGORY_WITH_PREFIX
                + VALID_TAG_WITH_PREFIX_CHEAP + VALID_TAG_WITH_PREFIX_NICE, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, VALID_NAME_WITH_PREFIX_MAC + INVALID_ADDRESS_DESC + VALID_CATEGORY_WITH_PREFIX
                + VALID_TAG_WITH_PREFIX_NICE + VALID_TAG_WITH_PREFIX_CHEAP, Address.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, VALID_NAME_WITH_PREFIX_MAC + VALID_ADDRESS_WITH_PREFIX_MAC + INVALID_CATEGORY_DESC
                + VALID_TAG_WITH_PREFIX_CHEAP + VALID_TAG_WITH_PREFIX_NICE, Category.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_NAME_WITH_PREFIX_MAC + VALID_ADDRESS_WITH_PREFIX_MAC
                + VALID_CATEGORY_WITH_PREFIX
                + INVALID_TAG_DESC + VALID_TAG_WITH_PREFIX_NICE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_ADDRESS_DESC + VALID_CATEGORY_WITH_PREFIX,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_NAME_WITH_PREFIX_KFC
                + VALID_ADDRESS_WITH_PREFIX_KFC + VALID_CATEGORY_WITH_PREFIX + VALID_TAG_WITH_PREFIX_NICE
                + VALID_TAG_WITH_PREFIX_CHEAP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_MAIN));
    }
}
