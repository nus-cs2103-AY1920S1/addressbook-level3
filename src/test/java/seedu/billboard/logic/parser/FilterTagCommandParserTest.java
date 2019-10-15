package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.TAG_DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_TAXES;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.FilterTagCommand;
import seedu.billboard.model.tag.ContainsTagPredicate;
import seedu.billboard.model.tag.Tag;

public class FilterTagCommandParserTest {
    private FilterTagCommandParser parser = new FilterTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagName_failure() {
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_TAG_DESC, expectedMessage);
    }

    @Test
    public void parse_validArgs_returnFilterTagCommand() {
        // Single tag
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_DINNER));
        FilterTagCommand expectedCommand = new FilterTagCommand(new ContainsTagPredicate(tags));
        assertParseSuccess(parser, TAG_DESC_DINNER, expectedCommand);

        // Multiple tags
        tags.add(new Tag(VALID_TAG_TAXES));
        expectedCommand = new FilterTagCommand(new ContainsTagPredicate(tags));
        assertParseSuccess(parser, TAG_DESC_DINNER + TAG_DESC_TAXES, expectedCommand);

        //Duplicate tags
        Set<Tag> duplicate = new HashSet<>();
        duplicate.add(new Tag(VALID_TAG_DINNER));
        duplicate.add(new Tag(VALID_TAG_DINNER));
        expectedCommand = new FilterTagCommand(new ContainsTagPredicate(duplicate));
        assertParseSuccess(parser, TAG_DESC_DINNER + TAG_DESC_DINNER, expectedCommand);
    }
}
