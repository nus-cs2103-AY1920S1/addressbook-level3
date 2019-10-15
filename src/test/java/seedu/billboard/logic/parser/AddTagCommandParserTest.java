package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_TAXES;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.AddTagCommand;
import seedu.billboard.model.tag.Tag;

public class AddTagCommandParserTest {
    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // One tag
        Index targetIndex = INDEX_FIRST_EXPENSE;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DINNER;
        AddTagCommand expectedCommand = new AddTagCommand(INDEX_FIRST_EXPENSE,
                new ArrayList<>(Arrays.asList(VALID_TAG_DINNER)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // Multiple tags
        userInput = targetIndex.getOneBased() + TAG_DESC_DINNER + TAG_DESC_TAXES;
        expectedCommand = new AddTagCommand(INDEX_FIRST_EXPENSE,
                new ArrayList<>(Arrays.asList(VALID_TAG_DINNER, VALID_TAG_TAXES)));
        assertParseSuccess(parser, userInput, expectedCommand);

        //Duplicate tag
        userInput = targetIndex.getOneBased() + TAG_DESC_TAXES + TAG_DESC_TAXES;
        expectedCommand = new AddTagCommand(INDEX_FIRST_EXPENSE,
                new ArrayList<>(Arrays.asList(VALID_TAG_TAXES, VALID_TAG_TAXES)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);

        //no index
        assertParseFailure(parser, TAG_DESC_DINNER, expectedMessage);
    }
    @Test
    public void parse_invalidTagName_failure() {
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        String userInput = INDEX_FIRST_EXPENSE.getOneBased()
                + INVALID_TAG_DESC;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
