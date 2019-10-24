package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.history.CommandHistory;



class HistoryCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboard(), new UserPrefs());
        CommandHistory.clearHistory();
        CommandHistory.addCmdHistory("history");
    }

    @Test
    void execute() {
        //Empty history -> empty history message
        HistoryCommand historyCommand = new HistoryCommand();
        assertCommandSuccess(historyCommand, model, HistoryCommand.MESSAGE_EMPTY_HISTORY, model);
        //Not empty history -> history records
        CommandHistory.addCmdHistory("history");
        assertCommandSuccess(historyCommand, model, String.format(CommandHistory.HAVE_HISTORY, "\n\thistory"), model);
    }
}
