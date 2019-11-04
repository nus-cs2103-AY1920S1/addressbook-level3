package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.getHelpMessage;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.help.SecondaryCommand;
import seedu.address.model.help.Type;

public class HelpCommandParserTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private HelpCommandParser helpCommandParser = new HelpCommandParser();

    @Test
    public void parse_utterNonsense_success() throws ParseException {
        String testString = "gerfcwdx";
        CommandResult expectedCommandResult = new CommandResult(getHelpMessage(), true, false, false, false);
        assertCommandSuccess(helpCommandParser.parse(testString), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_invalidCommand_failure() {
        String testString = " cmd/vwcaw type/api";
        assertParseFailure(helpCommandParser, testString, SecondaryCommand.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidType_failure() {
        String testString = " cmd/help type/apcqdwi";
        assertParseFailure(helpCommandParser, testString, Type.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allPrefixesCorrect_success() throws ParseException {
        String testString = " cmd/exit type/brief";
        String expectedResult = "exit: Exits the FinSec application";
        assertCommandSuccess(helpCommandParser.parse(testString), model, expectedResult, expectedModel);
    }
}
