package seedu.ezwatchlist.logic.commands;

<<<<<<< HEAD:src/test/java/seedu/address/logic/commands/ExitCommandTest.java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
=======
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/commands/ExitCommandTest.java
import static seedu.ezwatchlist.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD:src/test/java/seedu/address/logic/commands/ExitCommandTest.java
import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.ExitCommand;
=======
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/commands/ExitCommandTest.java
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
