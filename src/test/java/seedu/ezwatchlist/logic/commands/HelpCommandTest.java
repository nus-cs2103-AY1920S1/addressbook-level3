package seedu.ezwatchlist.logic.commands;

<<<<<<< HEAD:src/test/java/seedu/address/logic/commands/HelpCommandTest.java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
=======
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/commands/HelpCommandTest.java
import static seedu.ezwatchlist.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD:src/test/java/seedu/address/logic/commands/HelpCommandTest.java
import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.HelpCommand;
=======
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/commands/HelpCommandTest.java
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
