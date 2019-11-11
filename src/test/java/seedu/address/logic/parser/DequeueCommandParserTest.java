//@@author wongsm7
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.logic.commands.queue.UndoDequeueCommand;
import seedu.address.logic.parser.queue.DequeueCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.testutil.TestUtil;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DequeueCommandParserTest {

    private Model model = TestUtil.getTypicalModelManager();
    private DequeueCommandParser parser = new DequeueCommandParser(model);

    @Test
    public void parse_validArgs_returnsDequeueCommand() {
        ReferenceId id = model.getQueueList().get(0);
        assertParseSuccess(parser, "1", new ReversibleActionPairCommand(new DequeueCommand(id),
                new UndoDequeueCommand(id, 0)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DequeueCommand.MESSAGE_USAGE));
    }
}
