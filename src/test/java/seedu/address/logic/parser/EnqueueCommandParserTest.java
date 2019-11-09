//@@author wongsm7
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.parameters.PersonReferenceId.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.logic.commands.queue.EnqueueCommand;
import seedu.address.logic.parser.queue.EnqueueCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.testutil.TestUtil;

public class EnqueueCommandParserTest {
    private EnqueueCommandParser parser = new EnqueueCommandParser();
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void parse_validId_success() {
        ReferenceId id = model.getFilteredPatientList().get(0).getReferenceId();
        assertParseSuccess(parser, id.toString(), new ReversibleActionPairCommand(new EnqueueCommand(id),
                new DequeueCommand(id)));
    }

    @Test
    public void parse_invalidId_failure() {
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, EnqueueCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "22&2", expectedMessage);
    }
}
