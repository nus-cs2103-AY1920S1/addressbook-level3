package com.typee.logic.commands;

//import static com.typee.testutil.TypicalPersons.getTypicalAddressBook;
//import static com.typee.testutil.TypicalPersons.getTypicalPersons;

public class RedoCommandTest {

    /*
    private Model model = new ModelManager(getTypicalAddressBook().getAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook().getAddressBook(), new UserPrefs());

    public void setUp() throws NullUndoableActionException, NullRedoableActionException {
        model.deleteEngagement(getTypicalPersons().get(0));
        model.saveEngagementList();
        model.undoEngagementList();

        expectedModel.deleteEngagement(getTypicalPersons().get(0));
        expectedModel.saveEngagementList();
        expectedModel.undoEngagementList();
        expectedModel.redoEngagementList();
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

     */
}
