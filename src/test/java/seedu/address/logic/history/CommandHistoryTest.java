package seedu.address.logic.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.history.CommandHistory.MAX_SIZE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.Body;
import seedu.address.testutil.BodyBuilder;

//@@author ambervoong
class CommandHistoryTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory history = new CommandHistory();

    @Test
    void addGetExecutedCommand() {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);

        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), descriptor);
        // It's okay that the command was not executed before, because no attempt was made to undo it.
        history.addExecutedCommand(updateCommand);
        assertEquals(history.getExecutedCommand(), updateCommand);
    }

    @Test
    void addExecutedCommand_maxSizeReached_successfullyRemovedOlderCommand() {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);

        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), descriptor);
        for (int i = 0; i <= MAX_SIZE + 5; ++i) {
            // Number of commands added goes over max history size
            history.addExecutedCommand(updateCommand);
        }
        // History size does not go over MAX_SIZE.
        assertEquals(MAX_SIZE, history.getSize());
    }
}
