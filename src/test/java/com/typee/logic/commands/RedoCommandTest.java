package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalPersons.getTypicalAddressBook;
import static com.typee.testutil.TypicalPersons.getTypicalPersons;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;


public class RedoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook().getAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook().getAddressBook(), new UserPrefs());

    public void setUp() throws NullUndoableActionException, NullRedoableActionException {
        model.deletePerson(getTypicalPersons().get(0));
        model.saveAppointmentList();
        model.undoAppointmentList();

        expectedModel.deletePerson(getTypicalPersons().get(0));
        expectedModel.saveAppointmentList();
        expectedModel.undoAppointmentList();
        expectedModel.redoAppointmentList();
    }

    @Test
    public void execute_single_redoableState() {
        try {
            setUp();
        } catch (NullUndoableActionException | NullRedoableActionException e) {
            throw new AssertionError("Error in initialising");
        }

        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
