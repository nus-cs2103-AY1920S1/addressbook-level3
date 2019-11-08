package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HistoryCommandTest {

    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        assertCommandSuccess(new HistoryCommand(), model, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel, history);

        String command1 = "view 1";
        history.add(command1);
        assertCommandSuccess(new HistoryCommand(), model,
            String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel, history);

        String command2 = "dummy";
        String command3 = "clear";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
            String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(), model, expectedMessage, expectedModel, history);
    }
}
