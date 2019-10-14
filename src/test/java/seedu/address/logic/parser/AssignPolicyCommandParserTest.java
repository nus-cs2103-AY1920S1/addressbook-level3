package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AssignPolicyCommand;

class AssignPolicyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPolicyCommand.MESSAGE_USAGE);

    private AssignPolicyCommandParser parser = new AssignPolicyCommandParser();

    @Test
    void parse_missingParts_failure() {
        // no
    }
}