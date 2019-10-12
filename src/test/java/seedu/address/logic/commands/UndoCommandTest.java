package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_EMPTY_UNDO_HISTORY;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.Body;
import seedu.address.testutil.BodyBuilder;

//@@author ambervoong
/**
 * Contains integration tests (interaction with the Model and UndoableCommands).
 */
class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_undoUpdateCommand_success() throws CommandException {
        Body body = new BodyBuilder().build();
        model.addEntity(body);
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);

        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), descriptor);
        updateCommand.execute(model);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage =  String.format(MESSAGE_UNDO_SUCCESS, body);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), new BodyBuilder().build());

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nothingToUndo_failure() throws CommandException {
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage =  MESSAGE_EMPTY_UNDO_HISTORY;
        assertCommandFailure(undoCommand, model, expectedMessage);
    }

}