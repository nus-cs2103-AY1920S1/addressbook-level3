package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.event.EventExportCommand.MESSAGE_EXPORT_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Test for Event Export Command
 */
public class EventExportCommandTest {

    @Test
    public void execute_validCommand_returnsExportCommandResultType() {
        Model model = new ModelManager();
        assertCommandSuccess(new EventExportCommand(), model,
                new CommandResult(MESSAGE_EXPORT_SUCCESS, CommandResultType.EXPORT_CALENDAR), model);
    }

}
