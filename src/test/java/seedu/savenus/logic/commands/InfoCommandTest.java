package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.InfoCommandParser;
import seedu.savenus.logic.parser.MenuParser;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

/**
 * Contains tests for {@code InfoCommand}
 */
public class InfoCommandTest {

    private static Model model = new ModelManager();
    private static Model expectedModel = new ModelManager();
    private static MenuParser menuParser = new MenuParser();
    private static InfoCommandParser infoParser = new InfoCommandParser();

    private static final InfoCommand infoAdd = new InfoCommand(AddCommand.COMMAND_WORD);
    private static final InfoCommand infoBudget = new InfoCommand(BudgetCommand.COMMAND_WORD);
    private static final InfoCommand infoBuy = new InfoCommand(BuyCommand.COMMAND_WORD);

    private static final String VALID_COMMAND = "add";
    private static final String INVALID_COMMAND = "23ibdf";
    private static final String MULTIPLE_COMMAND = "info add edit";

    private static final String VALID_USER_INPUT = "info edit";
    private static final String INVALID_USER_INPUT = "info asdjasgdas";
    private static final String RANDOM_INVALID_USER_INPUT = "asioufgsaf";
    private static final String INVALID_MULTIPLE_COMMAND_USER_INPUT = "info edit add recommend";

    @Test
    public void equals() {
        // Same object, should return true
        assertTrue(infoAdd.equals(infoAdd));
        assertEquals(infoAdd, infoAdd);

        // Different object, should return false
        assertTrue(!infoAdd.equals(infoBudget));
        assertFalse(infoBuy.equals(infoBudget));
    }

    @Test
    public void input_validUserInput_parseSuccess() throws ParseException {
        InfoCommand validEditInfo = (InfoCommand) menuParser.parseCommand(VALID_USER_INPUT);
        CommandResult expectedCommandresult =
                new CommandResult(InfoCommand.EDIT_INFO, false, false, false);
        assertCommandSuccess(validEditInfo, model, expectedCommandresult, expectedModel);
    }

    @Test
    public void input_invalidUserInput_throwParseException() {
        assertThrows(ParseException.class, () -> menuParser.parseCommand(RANDOM_INVALID_USER_INPUT));
    }

    @Test
    public void input_invalidUserInput_invalidCommandFailure() throws ParseException {
        InfoCommand invalidInfo = (InfoCommand) menuParser.parseCommand(INVALID_USER_INPUT);
        assertCommandFailure(invalidInfo, model, InfoCommand.INVALID_COMMAND_ENTERED_MESSAGE);
    }

    @Test
    public void input_invalidUserInput_multipleCommandFailure() {
        assertThrows(ParseException.class, () -> menuParser.parseCommand(INVALID_MULTIPLE_COMMAND_USER_INPUT));
    }

    @Test
    public void input_validCommandSuccess() {
        InfoCommand modelInfoAdd = new InfoCommand(VALID_COMMAND);
        assertEquals(modelInfoAdd.getInput(), VALID_COMMAND);
        CommandResult expectedCommandResult =
                new CommandResult(InfoCommand.ADD_INFO, false, false, false);
        assertCommandSuccess(modelInfoAdd, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void input_invalidCommandFailure() {
        InfoCommand invalidInfo = new InfoCommand(INVALID_COMMAND);
        assertCommandFailure(invalidInfo, model, InfoCommand.INVALID_COMMAND_ENTERED_MESSAGE);
    }

    @Test
    public void input_multipleCommandFailure() {
        // Testing that the InfoCommand fails
        InfoCommand multipleInfo = new InfoCommand(MULTIPLE_COMMAND);
        assertCommandFailure(multipleInfo, model, InfoCommand.INVALID_COMMAND_ENTERED_MESSAGE);
    }

    @Test
    public void input_multipleCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> infoParser.parse(MULTIPLE_COMMAND));
    }

    @Test
    public void input_emptyCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> infoParser.parse(""));
    }

    @Test
    public void constructor_invalidCommand_throwsCommandException() {
        assertThrows(CommandException.class, () -> new InfoCommand(INVALID_COMMAND).execute(model));
    }
}
