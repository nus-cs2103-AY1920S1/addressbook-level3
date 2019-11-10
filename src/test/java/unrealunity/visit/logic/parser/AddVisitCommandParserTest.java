package unrealunity.visit.logic.parser;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.logic.commands.AddVisitCommand;

public class AddVisitCommandParserTest {
    private AddVisitCommandParser parser = new AddVisitCommandParser();
    private String nonEmptyDate = "12/12/2012";
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE);

        // no parameters
        CommandParserTestUtil.assertParseFailure(parser, AddVisitCommand.COMMAND_WORD, expectedMessage);

        // no index
        CommandParserTestUtil.assertParseFailure(parser,
            AddVisitCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }
}
