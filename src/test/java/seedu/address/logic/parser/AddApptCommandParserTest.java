package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.AddApptCommand;
import seedu.address.logic.parser.appointments.AddApptCommandParser;
import seedu.address.model.Model;
import seedu.address.testutil.TestUtil;

class AddApptCommandParserTest {
    private Model model = TestUtil.getTypicalModelManager();
    private AddApptCommandParser addAppCommandParser = new AddApptCommandParser(model);

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(addAppCommandParser, "yy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
    }
}
