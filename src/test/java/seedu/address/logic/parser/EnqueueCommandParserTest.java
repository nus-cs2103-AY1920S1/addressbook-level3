package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.model.person.parameters.PersonReferenceId.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EnqueueCommand;

public class EnqueueCommandParserTest {
    private EnqueueCommandParser parser = new EnqueueCommandParser();

    @Test
    public void parse_invalidId_failure() {
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, EnqueueCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "22&2", expectedMessage);
    }
}
