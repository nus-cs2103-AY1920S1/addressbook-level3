package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;

import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_MAX_INT;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalIndexes.INDEX_THIRD;

import static mams.testutil.TypicalMams.getTypicalMams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

import org.junit.jupiter.api.Test;

public class ClashCommandTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());

    @Test
    public void equal() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        params.setModuleCodes("cs1010", "cs1020");
        params.setStudentIndex(INDEX_FIRST);

        ClashCommand command = new ClashCommand(params);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        ClashCommand commandCopy = new ClashCommand(params);
        assertTrue(command.equals(commandCopy));

        // different types -> returns false
        assertFalse(command.equals(1));
        assertFalse(command.equals("abc"));

        // null -> returns false
        assertFalse(command.equals(null));

        // different appeal index -> returns false
        params.setAppealIndex(INDEX_SECOND);
        ClashCommand secondCommand = new ClashCommand(params);
        assertFalse(command.equals(secondCommand));

        // different module indices -> returns false
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndices(INDEX_FIRST, INDEX_THIRD);
        secondCommand = new ClashCommand(params);
        assertFalse(command.equals(secondCommand));

        // different module codes -> returns false
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        params.setModuleCodes("cs1010", "cs2030");
        secondCommand = new ClashCommand(params);
        assertFalse(command.equals(secondCommand));

        // different student index -> returns false
        params.setModuleCodes("cs1010", "cs1020");
        params.setStudentIndex(INDEX_SECOND);
        secondCommand = new ClashCommand(params);
        assertFalse(command.equals(secondCommand));
    }

    @Test
    public void execute_validAppealIndex_success() {

        // add module appeal

        // drop module appeal

        // appeal at index is not an add/drop module appeal
    }

    @Test
    public void execute_invalidAppealIndex_throwsInvalidIndexException() {

        // index > size of current appeal list

    }

    @Test
    public void execute_validModuleIndices_success() {}

    @Test
    public void execute_atLeastOneInvalidModuleIndices_throwsInvalidIndexException() {

        // index > size of current appeal list
    }

    @Test
    public void execute_validModuleCodes_success() {}

    @Test
    public void execute_atLeastOneInvalidModuleCodes_throwsModuleNotFoundException() {}

    @Test
    public void execute_validStudentIndex_success() {}

    @Test
    public void execute_invalidStudentIndex_throwsInvalidIndexException() {

        // index > size of current appeal list
    }

}
