package cs.f10.t1.nursetraverse.logic.parser.visit;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.CommandParserTestUtil.assertParseFailure;
import static cs.f10.t1.nursetraverse.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static cs.f10.t1.nursetraverse.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the BeginCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BeginCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class BeginVisitCommandParserTest {

    private BeginVisitCommandParser parser = new BeginVisitCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new BeginVisitCommand(INDEX_FIRST_PATIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BeginVisitCommand.MESSAGE_USAGE));
    }
}
