package unrealunity.visit.logic.parser;

import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.FindCommand;
import unrealunity.visit.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser,
            "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate("Alice Bob"));
        CommandParserTestUtil.assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

}
