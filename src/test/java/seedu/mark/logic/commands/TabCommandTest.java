package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.TabCommand.MESSAGE_SWITCH_ACKNOWLEDGEMENT;
import static seedu.mark.logic.commands.TabCommand.Tab;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.TabCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

class TabCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Storage storage = new StorageStub();

    @Test
    public void execute_tab_success() {
        CommandResult expectedCommandResult = new TabCommandResult(
                String.format(MESSAGE_SWITCH_ACKNOWLEDGEMENT, Tab.DASHBOARD.toString()), Tab.DASHBOARD);
        assertCommandSuccess(new TabCommand(Tab.DASHBOARD), model, storage, expectedCommandResult, expectedModel);

        expectedCommandResult = new TabCommandResult(
                String.format(MESSAGE_SWITCH_ACKNOWLEDGEMENT, Tab.OFFLINE.toString()), Tab.OFFLINE);
        assertCommandSuccess(new TabCommand(Tab.OFFLINE), model, storage, expectedCommandResult, expectedModel);

        expectedCommandResult = new TabCommandResult(
                String.format(MESSAGE_SWITCH_ACKNOWLEDGEMENT, Tab.ONLINE.toString()), Tab.ONLINE);
        assertCommandSuccess(new TabCommand(Tab.ONLINE), model, storage, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals_success() {
        assertEquals(new TabCommand(Tab.DASHBOARD), new TabCommand(Tab.DASHBOARD));
        assertEquals(new TabCommand(Tab.OFFLINE), new TabCommand(Tab.OFFLINE));
        assertEquals(new TabCommand(Tab.ONLINE), new TabCommand(Tab.ONLINE));

        assertNotEquals(new TabCommand(Tab.DASHBOARD), new TabCommand(Tab.OFFLINE));

        assertThrows(NullPointerException.class, () -> new TabCommand(null));
    }

}
