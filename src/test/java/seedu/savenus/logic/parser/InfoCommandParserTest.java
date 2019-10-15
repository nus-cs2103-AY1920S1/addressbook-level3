package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;

public class InfoCommandParserTest {

    private static final String VALID_USER_INPUT = "info add";
    private static final String INVALID_USER_INPUT = "saidugasf";

    private static MenuParser menuParser = new MenuParser();

    private static Model model = new ModelManager();
    private static Model expectedModel = new ModelManager();

    @Test
    public void input_validUserInput_parseSuccess() throws ParseException {
        InfoCommand validEditInfo = (InfoCommand) menuParser.parseCommand(VALID_USER_INPUT);
        CommandResult expectedCommandresult =
                new CommandResult(InfoCommand.ADD_INFO, false, false, false);
        assertCommandSuccess(validEditInfo, model, expectedCommandresult, expectedModel);
    }

    @Test
    public void input_invalidUserInput_ThrowsParseException() {
        assertThrows(ParseException.class, () -> menuParser.parseCommand(INVALID_USER_INPUT));
    }
}
