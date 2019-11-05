package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.AddAppCommand;
import seedu.address.logic.parser.appointments.AddAppCommandParser;
import seedu.address.model.Model;
import seedu.address.testutil.TestUtil;

class AddAppCommandParserTest {
    private Model model = TestUtil.getTypicalModelManager();
    private AddAppCommandParser addAppCommandParser = new AddAppCommandParser(model);

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(addAppCommandParser, "yy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE));
    }
}
