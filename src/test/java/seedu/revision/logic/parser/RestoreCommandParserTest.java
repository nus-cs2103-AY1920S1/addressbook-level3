package seedu.revision.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.RestoreCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.ClearCommandParser;
import seedu.revision.logic.parser.main.ParserManager;
import seedu.revision.logic.parser.main.RestoreCommandParser;

public class RestoreCommandParserTest {
    private RestoreCommandParser parser = new RestoreCommandParser();
    private final ParserManager parserManager = new ParserManager();

    @Test
    public void parse_validArgs_returnsClearCommand() throws ParseException {
        assertTrue(parserManager.parseCommand(RestoreCommand.COMMAND_WORD) instanceof RestoreCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommandParser.MESSAGE_ADDITIONAL_COMMAND_BEHIND));
    }
}
