package seedu.algobase.logic.commands;

import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;

class HelpCommandTest {

    private static final String EXPECTED_COMMAND_LIST = "Available commands are: [add, addplan, addtag, "
        + "clear, delete, deleteplan, deletetag, deletetask, donetask, edit, editplan, edittag, exit, "
        + "find, findplan, help, list, listplan, listtag, sort, switch, undonetask]\n"
        + "More information can be found in the popup window.";
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAlgoBase(), new UserPrefs());

    @Test
    void constructor_nullCommandClassWithoutListingAllCommands_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HelpCommand(null, false));
    }

    @Test
    void constructor_nullCommandClassWithListingAllCommands_success() {
        HelpCommand command = new HelpCommand(null, true);
        assertCommandSuccess(command, model,
            new CommandResult(EXPECTED_COMMAND_LIST, true, false), expectedModel);
    }

    @Test
    void execute_correctCommandWord_showsMessageUsage() {
        HelpCommand command = new HelpCommand(AddCommand.class, false);
        assertCommandSuccess(command, model, AddCommand.MESSAGE_USAGE, expectedModel);
    }
}
