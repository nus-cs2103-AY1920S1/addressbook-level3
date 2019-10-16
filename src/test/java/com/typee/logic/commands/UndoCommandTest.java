package com.typee.logic.commands;

//import static com.typee.testutil.TypicalPersons.getTypicalAddressBook;
//import static com.typee.testutil.TypicalPersons.getTypicalPersons;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;


public class UndoCommandTest {

    /*
    private Model model = new ModelManager(getTypicalAddressBook().getAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook().getAddressBook(), new UserPrefs());

    public void setUp() {
        model.deletePerson(getTypicalPersons().get(0));
        model.saveEngagementList();

        expectedModel.deletePerson(getTypicalPersons().get(0));
        expectedModel.saveEngagementList();
    }

    @Test
    public void execute_single_undoableState() {
        setUp();

        try {
            expectedModel.undoEngagementList();
        } catch (NullUndoableActionException e) {
            throw new AssertionError("Wrong null undoable command");
        }

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

     */
}
