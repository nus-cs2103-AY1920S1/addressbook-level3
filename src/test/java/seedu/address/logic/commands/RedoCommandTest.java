package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

public class RedoCommandTest {
    private static Model model = new ModelManager();
    private static UndoCommand undoCommand = new UndoCommand();
    private static RedoCommand redoCommand = new RedoCommand();

    private static final Book VALID_BOOK = new BookBuilder()
            .withTitle("Hello World")
            .withAuthor("Yeo Tong")
            .withSerialNumber("B00000")
            .build();

    @Test
    public void execute_noRedoableCommand_throwsCommandException() {
        assertThrows(CommandException.class, () -> redoCommand.execute(model));
    }

    @Test
    public void execute_redoAfterUndoneCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);

        AddCommand addCommand = new AddCommand(VALID_BOOK);
        assertNotNull(addCommand);

        try {
            addCommand.execute(model);
            addCommand.execute(updatedModel);
            model.commitCommand(addCommand);
            updatedModel.commitCommand(addCommand);
            undoCommand.execute(updatedModel);
            redoCommand.execute(updatedModel);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(model, updatedModel);
    }

    @Test
    public void equal() {
        RedoCommand standardCommand = new RedoCommand();

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);
    }
}
