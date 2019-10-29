package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.events.parameters.DateTime.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.model.Model;
import seedu.address.testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

class AddAppCommandParserTest {
    private Model model = TestUtil.getTypicalModelManager();
    private AddAppCommandParser addAppCommandParser = new AddAppCommandParser(model);

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(addAppCommandParser, "yy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE_RECURSIVELY));
    }

    @Test
    void parse_invalidTiming_throwsParseException() {
        String expectMess = String.format("The start " + MESSAGE_CONSTRAINTS, AddAppCommand.MESSAGE_USAGE);
        assertParseFailure(addAppCommandParser, "i/01A rec/m num/2 str/01/11/19 -1",
                expectMess);
    }
}