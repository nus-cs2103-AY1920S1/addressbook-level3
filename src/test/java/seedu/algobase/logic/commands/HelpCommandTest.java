package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.AppUtil.getClassStringField;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.problem.AddCommand;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;

class HelpCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private static String getExpectedCommandListString() {
        List<String> commandWords = new ArrayList<>();
        for (Class command : Command.COMMAND_LIST) {
            commandWords.add(getClassStringField(command, "COMMAND_WORD"));
        }
        String commandPrompt = "Available commands are: " + commandWords.toString() + "\n"
                + "More information can be found in the popup window.";
        return commandPrompt;
    }

    @Test
    void constructor_nullCommandClassWithoutListingAllCommands_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HelpCommand(null, false));
    }

    @Test
    void constructor_nullCommandClassWithListingAllCommands_success() {
        HelpCommand command = new HelpCommand(null, true);
        assertCommandSuccess(command, model, commandHistory,
            new CommandResult(getExpectedCommandListString(), true, false), expectedModel);
    }

    @Test
    void execute_correctCommandWord_showsMessageUsage() {
        HelpCommand command = new HelpCommand(AddCommand.class, false);
        assertCommandSuccess(command, model, commandHistory, AddCommand.MESSAGE_USAGE, expectedModel);
    }
}
