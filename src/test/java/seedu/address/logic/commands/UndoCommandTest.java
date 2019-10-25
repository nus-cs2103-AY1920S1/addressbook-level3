package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.HistoryRecord;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.DummyMutatorCommand;

public class UndoCommandTest {

    @Test
    public void execute_noArgsNoHistory_throwsCommandException() {
        // Test on fresh no history model
        Model model = new ModelManager();

        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_MORE_HISTORY);

        // Test on no history model with commands undone before
        Command commandToUndo = new DummyMutatorCommand("1");
        Model expectedModel = new ModelManager();
        String expectedMessage = String.format(UndoCommand.MESSAGE_UNDO_SUCCESS,
                CollectionUtil.collectionToStringShowingIndexes(List.of(
                        new HistoryRecord((MutatorCommand) commandToUndo, new AddressBook()))));

        assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);

        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_MORE_HISTORY);
    }
}
