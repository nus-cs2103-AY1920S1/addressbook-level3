package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.templatecommand.TemplateUnarchiveCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the TemplateUnarchiveCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the TemplateUnarchiveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class TemplateUnarchiveCommandParserTest {

    private TemplateUnarchiveCommandParser parser = new TemplateUnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1", new TemplateUnarchiveCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TemplateUnarchiveCommand.MESSAGE_USAGE));
    }
}
