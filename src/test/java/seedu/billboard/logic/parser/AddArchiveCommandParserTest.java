package seedu.billboard.logic.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.billboard.logic.commands.AddArchiveCommand;

import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddArchiveCommandParserTest {

    private AddArchiveCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new AddArchiveCommandParser();
    }

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1 arc/luxury", new AddArchiveCommand("luxury", INDEX_FIRST_EXPENSE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a arc/luxury", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddArchiveCommand.MESSAGE_USAGE));
    }
}
