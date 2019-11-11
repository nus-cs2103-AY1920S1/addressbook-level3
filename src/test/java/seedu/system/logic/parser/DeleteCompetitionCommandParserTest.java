package seedu.system.logic.parser;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.system.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.outofsession.DeleteCompetitionCommand;
import seedu.system.logic.parser.outofsession.DeleteCompetitionCommandParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCompetitionCommand code. For example, inputs "2" and "2 xyz" take the
 * same path through the DeleteCompetitionCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCompetitionCommandParserTest {

    private DeleteCompetitionCommandParser parser = new DeleteCompetitionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCompetitionCommand() {
        assertParseSuccess(parser, "2", new DeleteCompetitionCommand(INDEX_SECOND));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "QQQ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteCompetitionCommand.MESSAGE_USAGE));
    }
}
