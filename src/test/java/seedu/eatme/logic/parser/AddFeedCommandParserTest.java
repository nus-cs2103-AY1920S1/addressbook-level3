package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.commands.CommandTestUtil.ADDRESS_DESC_EATBOOK;
import static seedu.eatme.logic.commands.CommandTestUtil.NAME_DESC_EATBOOK;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_EATBOOK;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_EATBOOK;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.commands.AddFeedCommand;
import seedu.eatme.model.feed.Feed;
import seedu.eatme.testutil.FeedBuilder;

public class AddFeedCommandParserTest {
    private AddFeedCommandParser parser = new AddFeedCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Feed expectedFeed = new FeedBuilder().withName(VALID_NAME_EATBOOK).withAddress(VALID_ADDRESS_EATBOOK).build();
        assertParseSuccess(parser, NAME_DESC_EATBOOK + ADDRESS_DESC_EATBOOK, new AddFeedCommand(expectedFeed));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFeedCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_EATBOOK + ADDRESS_DESC_EATBOOK, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_EATBOOK + VALID_ADDRESS_EATBOOK, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_EATBOOK + VALID_ADDRESS_EATBOOK, expectedMessage);
    }
}
